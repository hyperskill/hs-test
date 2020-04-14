package org.hyperskill.hstest.v6.mocks.web.request;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hyperskill.hstest.v6.mocks.web.response.HttpResponse;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestExecutor {

    private static class BufferPortion {
        final byte[] buffer;
        BufferPortion(byte[] buffer) {
            this.buffer = buffer;
        }
    }

    private static HttpResponse executeRequest(HttpRequestBase request) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            org.apache.http.HttpResponse httpResponse = client.execute(request);

            int statusCode = httpResponse.getStatusLine().getStatusCode();

            Map<String, String> headers = new HashMap<>();
            for (Header currHeader : httpResponse.getAllHeaders()) {
                headers.put(currHeader.getName(), currHeader.getValue());
            }

            if (httpResponse.getEntity() == null) {
                return new HttpResponse(statusCode, headers, new byte[0]);
            }

            DataInputStream input = new DataInputStream(
                httpResponse.getEntity().getContent());

            ArrayList<BufferPortion> buffer = new ArrayList<>();

            int readPortion = 1024;
            int contentLength = 0;
            while (true) {
                byte[] rawPortion = new byte[readPortion];
                int readBytes = input.read(rawPortion);
                if (readBytes == -1) {
                    break;
                }
                contentLength += readBytes;
                if (readBytes != readPortion) {
                    byte[] lastRawPortion = new byte[readBytes];
                    System.arraycopy(rawPortion, 0, lastRawPortion, 0, readBytes);
                    buffer.add(new BufferPortion(lastRawPortion));
                    break;
                }
                buffer.add(new BufferPortion(rawPortion));
            }

            byte[] rawContent = new byte[contentLength];
            for (int i = 0; i < buffer.size(); i++) {
                BufferPortion portion = buffer.get(i);
                System.arraycopy(
                    portion.buffer, 0,
                    rawContent, i * readPortion,
                    portion.buffer.length);
            }

            request.releaseConnection();

            return new HttpResponse(statusCode, headers, rawContent);
        } catch (IOException e) {
            return null;
        }
    }

    public static String packUrlParams(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        StringBuilder paramsBuilder = new StringBuilder();
        for (String key : params.keySet()) {
            paramsBuilder.append(key);
            paramsBuilder.append("=");
            paramsBuilder.append(params.get(key));
            paramsBuilder.append("&");
        }
        String strParams = paramsBuilder.toString();
        strParams = strParams.substring(0, strParams.length() - 1);
        return strParams;
    }

    private static HttpRequestBase constructGet(HttpRequest request) {
        String uriWithUrlParams = request.uri;
        if (request.getParams != null && !request.getParams.isEmpty()) {
            uriWithUrlParams += "?" + packUrlParams(request.getParams);
        }
        return new HttpGet(uriWithUrlParams);
    }

    private static HttpRequestBase constructPost(HttpRequest request) {
        HttpPost post = new HttpPost(request.uri);
        post.setEntity(new StringEntity(request.content,
            ContentType.getByMimeType(request.headers.get("Content-Type"))));
        return post;
    }

    private static HttpRequestBase constructPut(HttpRequest request) {
        HttpPut put = new HttpPut(request.uri);
        put.setEntity(new StringEntity(request.content,
            ContentType.getByMimeType(request.headers.get("Content-Type"))));
        return put;
    }

    private static HttpRequestBase constructDelete(HttpRequest request) {
        return new HttpDelete(request.uri);
    }

    public static HttpResponse send(HttpRequest request) {

        HttpRequestBase requestBase;

        switch (request.method) {
            case "POST": requestBase = constructPost(request); break;
            case "PUT": requestBase = constructPut(request); break;
            case "DELETE": requestBase = constructDelete(request); break;
            case "GET":
            default:
                requestBase = constructGet(request);
        }

        for (String key : request.headers.keySet()) {
            requestBase.addHeader(key, request.headers.get(key));
        }

        return executeRequest(requestBase);
    }
}
