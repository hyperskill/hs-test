package org.hyperskill.hstest.mocks.web.request;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hyperskill.hstest.exception.outcomes.PresentationError;
import org.hyperskill.hstest.mocks.web.constants.Methods;
import org.hyperskill.hstest.mocks.web.response.HttpResponse;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.hyperskill.hstest.mocks.web.constants.Headers.CONTENT_TYPE;

public final class HttpRequestExecutor {

    private HttpRequestExecutor() { }

    private static final int READ_CHUNK = 1024;

    private static class BufferPortion {
        final byte[] buffer;
        BufferPortion(byte[] buffer) {
            this.buffer = buffer;
        }
    }

    private static HttpResponse executeRequest(HttpRequestBase request, HttpRequest source) {
        try {
            HttpClient client = HttpClientBuilder.create().build();

            org.apache.http.HttpResponse httpResponse;
            try {
                httpResponse = client.execute(request);
            } catch (IOException ex) {
                throw new PresentationError(ex.getMessage());
            }

            int statusCode = httpResponse.getStatusLine().getStatusCode();

            Map<String, String> headers = new HashMap<>();
            for (Header currHeader : httpResponse.getAllHeaders()) {
                headers.put(currHeader.getName(), currHeader.getValue());
            }

            if (httpResponse.getEntity() == null) {
                return new HttpResponse(source, statusCode, headers, new byte[0]);
            }

            DataInputStream input = new DataInputStream(
                httpResponse.getEntity().getContent());

            ArrayList<BufferPortion> buffer = new ArrayList<>();

            int contentLength = 0;
            while (true) {
                byte[] rawPortion = new byte[READ_CHUNK];

                int readBytes;
                try {
                    readBytes = input.read(rawPortion);
                } catch (IOException ex) {
                    break;
                }

                if (readBytes == -1) {
                    break;
                }

                contentLength += readBytes;

                if (readBytes != READ_CHUNK) {
                    byte[] lastRawPortion = new byte[readBytes];
                    System.arraycopy(rawPortion, 0, lastRawPortion, 0, readBytes);
                    buffer.add(new BufferPortion(lastRawPortion));
                } else {
                    buffer.add(new BufferPortion(rawPortion));
                }
            }

            byte[] rawContent = new byte[contentLength];
            int currentDest = 0;
            for (BufferPortion portion : buffer) {
                System.arraycopy(
                    portion.buffer, 0,
                    rawContent, currentDest,
                    portion.buffer.length);
                currentDest += portion.buffer.length;
            }

            request.releaseConnection();

            return new HttpResponse(source, statusCode, headers, rawContent);
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
        return new HttpGet(request.getUri());
    }

    private static HttpRequestBase constructPost(HttpRequest request) {
        HttpPost post = new HttpPost(request.getUri());
        post.setEntity(new StringEntity(request.content,
            ContentType.getByMimeType(request.headers.get(CONTENT_TYPE.toString()))));
        return post;
    }

    private static HttpRequestBase constructPut(HttpRequest request) {
        HttpPut put = new HttpPut(request.getUri());
        put.setEntity(new StringEntity(request.content,
            ContentType.getByMimeType(request.headers.get(CONTENT_TYPE.toString()))));
        return put;
    }

    private static HttpRequestBase constructDelete(HttpRequest request) {
        return new HttpDelete(request.getUri());
    }

    public static HttpResponse send(HttpRequest request) {

        HttpRequestBase requestBase;

        switch (Methods.of(request.method)) {
            case POST:
                requestBase = constructPost(request);
                break;
            case PUT:
                requestBase = constructPut(request);
                break;
            case DELETE:
                requestBase = constructDelete(request);
                break;
            case GET:
            default:
                requestBase = constructGet(request);
        }

        for (String key : request.headers.keySet()) {
            requestBase.addHeader(key, request.headers.get(key));
        }

        return executeRequest(requestBase, request);
    }
}
