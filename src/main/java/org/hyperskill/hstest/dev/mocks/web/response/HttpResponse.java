package org.hyperskill.hstest.dev.mocks.web.response;

public class HttpResponse {
    public final int statusCode;
    public final String content;

    public HttpResponse(int statusCode, String content) {
        this.statusCode = statusCode;
        this.content = content;
    }
}
