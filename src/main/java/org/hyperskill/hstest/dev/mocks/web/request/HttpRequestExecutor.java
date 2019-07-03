package org.hyperskill.hstest.dev.mocks.web.request;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hyperskill.hstest.dev.mocks.web.response.HttpResponse;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestExecutor {

    private static HttpResponse executeRequest(HttpRequestBase request) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            org.apache.http.HttpResponse httpResponse = client.execute(request);

            int statusCode = httpResponse.getStatusLine().getStatusCode();

            Map<String, String> headers = new HashMap<>();
            for (Header currHeader : httpResponse.getAllHeaders()) {
                headers.put(currHeader.getName(), currHeader.getValue());
            }

            DataInputStream input = new DataInputStream(
                httpResponse.getEntity().getContent());

            byte[] rawContent;
            if (headers.containsKey("Content-Length")) {
                rawContent = new byte[Integer.parseInt(headers.get("Content-Length"))];
                input.read(rawContent);
            } else {
                rawContent = new byte[32];
                input.read(rawContent);
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
