package org.hyperskill.hstest.v7.mocks.web.response;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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

    public JsonElement getJson() {
        String content = getContent();
        return new JsonParser().parse(content);
    }
}
