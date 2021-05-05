package org.hyperskill.hstest.mocks.web.request;

import org.apache.http.entity.ContentType;
import org.hyperskill.hstest.mocks.web.response.HttpResponse;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.hyperskill.hstest.common.Utils.sleep;
import static org.hyperskill.hstest.mocks.web.constants.Headers.AUTHORIZATION;
import static org.hyperskill.hstest.mocks.web.constants.Headers.CONTENT_TYPE;
import static org.hyperskill.hstest.mocks.web.constants.Methods.POST;
import static org.hyperskill.hstest.mocks.web.request.HttpRequestExecutor.packUrlParams;
import static org.hyperskill.hstest.mocks.web.request.HttpRequestParser.parseUri;

public class HttpRequest {

    String method = "";
    String uri = "";
    String version = "";

    String schema = "http";
    String host = "localhost";
    int port = 80;
    String endpoint = "/";
    String anchor = "";

    Map<String, String> params = new HashMap<>();
    Map<String, String> headers = new HashMap<>();

    String content = "";
    int contentLength;

    public HttpRequest() { }

    public HttpRequest(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getLocalUri() {
        String localUri = endpoint;
        if (params.size() > 0) {
            localUri += "?" + packUrlParams(params);
        }
        if (anchor.length() > 0) {
            localUri += "#" + anchor;
        }
        return localUri;
    }

    public String getSchema() {
        return schema;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getAnchor() {
        return anchor;
    }

    public HttpRequest setUri(String uri) {
        this.uri = uri;
        parseUri(this);
        return this;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getParams() {
        return params;
    }

    @Deprecated
    public Map<String, String> getGetParams() {
        return getParams();
    }

    public HttpRequest addParam(String param, String value) {
        params.put(param, value);
        return this;
    }

    public HttpRequest addParams(Map<String, String> newParams) {
        for (String key : newParams.keySet()) {
            addParam(key, newParams.get(key));
        }
        return this;
    }

    @Deprecated
    public HttpRequest setGetParam(String getParam, String value) {
        return addParam(getParam, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpRequest addHeader(String header, String value) {
        headers.put(header, value);
        return this;
    }

    public HttpRequest addHeaders(Map<String, String> newHeaders) {
        for (String key : newHeaders.keySet()) {
            addHeader(key, newHeaders.get(key));
        }
        return this;
    }

    public String getContent() {
        return content;
    }

    public int getContentLength() {
        return contentLength;
    }

    public HttpRequest setContent(String content) {
        this.content = content;
        contentLength = content.length();
        return this;
    }

    public HttpRequest setContentType(ContentType type) {
        return addHeader(CONTENT_TYPE, type.getMimeType());
    }

    public HttpRequest basicAuth(String login, String pass) {
        String beforeEncoding = login + ":" + pass;
        String afterEncoding = Base64.getEncoder().encodeToString(beforeEncoding.getBytes());
        String headerValue = "Basic " + afterEncoding;
        return addHeader(AUTHORIZATION, headerValue);
    }

    public HttpResponse send() {
        if (method.equals(POST) && !params.isEmpty()) {
            content = packUrlParams(params);
        }
        HttpResponse response = HttpRequestExecutor.send(this);
        sleep(1); // So that we cannot send 2 requests in the same microsecond
        return response;
    }
}
