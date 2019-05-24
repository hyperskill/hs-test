package org.hyperskill.hstest.android.mocks.web;

public class WebPage {

    public static final String code200 = "200 OK";
    public static final String code404 = "404 Not Found";

    private String content = "";
    private String contentType = "";
    private String statusCode = code200;

    private static final String request =
        "GET __path__ HTTP/1.1\r\n" +
        "Host: __host__\r\n\r\n";

    private static final String response =
        "HTTP/1.1 __status_code__\r\n" +
        "Content-Length: __length__\r\n\r\n";

    static final String notFound = response
        .replaceAll("__status_code__", code404)
        .replaceAll("__length__", "0");

    private static final String responseStatusCode = "HTTP/1.1 __status_code__\r\n";
    private static final String responseContentType = "Content-Type: __content_type__\r\n";
    private static final String responseContentLength = "Content-Length: __length__\r\n";

    private String createHeader() {
        StringBuilder response = new StringBuilder();

        response.append(responseStatusCode.replaceAll("__status_code__", statusCode));

        if (contentType.length() != 0) {
            response.append(responseContentType.replaceAll("__content_type__", contentType));
        }

        String size = Integer.toString(content.length());
        response.append(responseContentLength.replaceAll("__length__", size));

        return response.append("\r\n").toString();
    }

    public String contentWithHeader() {
        return createHeader() + content;
    }

    public WebPage setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public WebPage setContent(String content) {
        this.content = content;
        return this;
    }

    public WebPage setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }

}
