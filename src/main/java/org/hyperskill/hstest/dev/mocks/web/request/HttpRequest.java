package org.hyperskill.hstest.dev.mocks.web.request;


public class HttpRequest {

    String method;
    String uri;
    String version;

    String getParams = "";

    String header;
    String content;

    int contentLength;

    HttpRequest() { }

    public int getContentLength() {
        return contentLength;
    }

    public String getHeader() {
        return header;
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

    public String getGetParams() {
        return getParams;
    }
}
