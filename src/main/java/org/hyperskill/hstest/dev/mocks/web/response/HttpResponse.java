package org.hyperskill.hstest.dev.mocks.web.response;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpResponse {
    private final int statusCode;

    private final Map<String, String> headers;
    private final byte[] rawContent;

    public HttpResponse(int statusCode, Map<String, String> headers, byte[] rawContent) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.rawContent = rawContent;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getRawContent() {
        return rawContent;
    }

    public String getContent() {
        return new String(rawContent, StandardCharsets.UTF_8);
    }
}
