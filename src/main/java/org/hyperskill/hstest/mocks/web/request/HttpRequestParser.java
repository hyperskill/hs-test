package org.hyperskill.hstest.mocks.web.request;

import org.hyperskill.hstest.common.Utils;

import java.io.DataInputStream;

import static org.hyperskill.hstest.mocks.web.constants.Headers.CONTENT_LENGTH;

public final class HttpRequestParser {

    private final DataInputStream input;
    private final HttpRequest request;

    private HttpRequestParser(DataInputStream input) {
        this.input = input;
        request = new HttpRequest();
    }

    private String getStartLine() throws Exception {
        StringBuilder buffer = new StringBuilder();
        while (buffer.length() < 4
                || !(buffer.charAt(buffer.length() - 2) == '\r'
                && buffer.charAt(buffer.length() - 1) == '\n')) {
            buffer.appendCodePoint(input.read());
        }
        return Utils.cleanText(buffer.toString()).trim();
    }

    private String getRawHeaders() throws Exception {
        StringBuilder buffer = new StringBuilder();
        while (buffer.length() < 4
                || !(buffer.charAt(buffer.length() - 4) == '\r'
                    && buffer.charAt(buffer.length() - 3) == '\n'
                    && buffer.charAt(buffer.length() - 2) == '\r'
                    && buffer.charAt(buffer.length() - 1) == '\n')) {
            buffer.appendCodePoint(input.read());
        }
        return Utils.cleanText(buffer.toString()).trim();
    }

    private void parseHeaders() throws Exception {
        String rawHeaders = getRawHeaders();
        String[] lines = rawHeaders.split("\n");

        for (String line : lines) {
            String[] parts = line.split(":");
            String key = parts[0].trim();
            String value = parts[1].trim();
            request.headers.put(key, value);

            if (key.equals(CONTENT_LENGTH.toString())) {
                request.contentLength = Integer.parseInt(value);
            }
        }
    }

    private String getContent() throws Exception {
        int contentLength = request.contentLength;
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < contentLength; i++) {
            buffer.appendCodePoint(input.read());
        }
        return Utils.cleanText(buffer.toString());
    }


    private void parse() throws Exception {
        String startLine = getStartLine();
        String[] opts = startLine.split(" ");

        request.method = opts[0];
        request.version = opts[2];

        request.setUri(opts[1]);
        parseHeaders();
        request.content = getContent();
    }


    public static HttpRequest parse(DataInputStream input) {
        try {
            HttpRequestParser parser = new HttpRequestParser(input);
            parser.parse();
            return parser.request;
        } catch (Exception ignored) { }
        return null;
    }

}
