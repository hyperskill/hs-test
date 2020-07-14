package org.hyperskill.hstest.mocks.web.response;

import com.google.gson.JsonElement;
import org.hyperskill.hstest.common.JsonUtils;
import org.hyperskill.hstest.mocks.web.request.HttpRequest;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpResponse {

    private final HttpRequest request;
    private final int statusCode;
    private final Map<String, String> headers;
    private final byte[] rawContent;

    public HttpResponse(HttpRequest request, int statusCode, Map<String, String> headers, byte[] rawContent) {
        this.request = request;
        this.statusCode = statusCode;
        this.headers = headers;
        this.rawContent = rawContent;
    }

    public HttpRequest getRequest() {
        return request;
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
        return JsonUtils.getJson(getContent());
    }
}
