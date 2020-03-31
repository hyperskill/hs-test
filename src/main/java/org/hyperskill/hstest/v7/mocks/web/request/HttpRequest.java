package org.hyperskill.hstest.v7.mocks.web.request;

import org.apache.http.entity.ContentType;
import org.hyperskill.hstest.v7.mocks.web.response.HttpResponse;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.hyperskill.hstest.v7.mocks.web.constants.Headers.AUTHORIZATION;
import static org.hyperskill.hstest.v7.mocks.web.constants.Headers.CONTENT_TYPE;


public class HttpRequest {

    String method = "";
    String uri = "";
    String version = "";

    Map<String, String> getParams = new HashMap<>();
    Map<String, String> headers = new HashMap<>();

    String content = "";
    int contentLength;

    public HttpRequest() { }

    public HttpRequest(String method) {
        this.method = method;
    }

    public int getContentLength() {
        return contentLength;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getContent() {
        return content;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getGetParams() {
        return getParams;
    }

    public HttpRequest setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public HttpRequest setGetParam(String getParam, String value) {
        getParams.put(getParam, value);
        return this;
    }

    public HttpRequest addHeader(String header, String value) {
        headers.put(header, value);
        return this;
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
        return HttpRequestExecutor.send(this);
    }
}
