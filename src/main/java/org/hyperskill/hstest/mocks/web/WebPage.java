package org.hyperskill.hstest.mocks.web;

import static org.hyperskill.hstest.mocks.web.constants.Headers.CONTENT_LENGTH;
import static org.hyperskill.hstest.mocks.web.constants.Headers.CONTENT_TYPE;
import static org.hyperskill.hstest.mocks.web.constants.Headers.HOST;
import static org.hyperskill.hstest.mocks.web.constants.Methods.GET;
import static org.hyperskill.hstest.mocks.web.constants.StatusCodes.CODE_200;
import static org.hyperskill.hstest.mocks.web.constants.StatusCodes.CODE_404;

public class WebPage {

    private String content = "";
    private String contentType = "";
    private String statusCode = CODE_200;

    private static final String NEW_LINE = "\r\n";

    private static final String STATUS_CODE_TEMP = "__status_code__";
    private static final String CONTENT_TYPE_TEMP = "__content_type__";
    private static final String CONTENT_LENGTH_TEMP = "__length__";
    private static final String PATH_TEMP = "__path__";
    private static final String HOST_TEMP = "__host__";

    private static final String RESPONSE_STATUS_CODE =
        "HTTP/1.1 " + STATUS_CODE_TEMP + NEW_LINE;

    private static final String RESPONSE_CONTENT_TYPE =
        CONTENT_TYPE + ": " + CONTENT_TYPE_TEMP + NEW_LINE;

    private static final String RESPONSE_CONTENT_LENGTH =
        CONTENT_LENGTH + ": " + CONTENT_LENGTH_TEMP + NEW_LINE;

    private static final String REQUEST_GET = GET + " " + PATH_TEMP + " HTTP/1.1" + NEW_LINE;
    private static final String REQUEST_HOST = HOST + ": " + HOST_TEMP + NEW_LINE;

    private static final String REQUEST =
        REQUEST_GET + REQUEST_HOST + NEW_LINE;

    private static final String RESPONSE =
        RESPONSE_STATUS_CODE + RESPONSE_CONTENT_LENGTH + NEW_LINE;

    static final String NOT_FOUND = RESPONSE
        .replaceAll(STATUS_CODE_TEMP, CODE_404)
        .replaceAll(CONTENT_LENGTH_TEMP, "0");

    private String createHeader() {
        StringBuilder response = new StringBuilder();

        response.append(RESPONSE_STATUS_CODE.replaceAll(STATUS_CODE_TEMP, statusCode));

        if (contentType.length() != 0) {
            response.append(RESPONSE_CONTENT_TYPE.replaceAll(CONTENT_TYPE_TEMP, contentType));
        }

        String size = Integer.toString(content.length());
        response.append(RESPONSE_CONTENT_LENGTH.replaceAll(CONTENT_LENGTH_TEMP, size));

        return response.append(NEW_LINE).toString();
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
