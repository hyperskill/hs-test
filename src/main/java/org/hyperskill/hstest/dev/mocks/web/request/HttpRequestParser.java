package org.hyperskill.hstest.dev.mocks.web.request;

import org.hyperskill.hstest.dev.common.Utils;

import java.io.DataInputStream;

public class HttpRequestParser {

    private DataInputStream input;
    private HttpRequest request;

    private HttpRequestParser(DataInputStream input) {
        this.input = input;
        request = new HttpRequest();
    }

    private int parseContentLength() {
        for (String line : request.header.trim().split("\n")) {
            if (line.trim().startsWith("Content-Length")) {
                try {
                    String number = line.split(":")[1].trim();
                    return Integer.parseInt(number);
                } catch (Exception ignored) { }
            }
        }
        return 0;
    }

    private String getStartLine() throws Exception {
        StringBuilder buffer = new StringBuilder();
        while (buffer.length() < 4 ||
            !(buffer.charAt(buffer.length() - 2) == '\r' &&
                buffer.charAt(buffer.length() - 1) == '\n')) {
            buffer.appendCodePoint(input.read());
        }
        return Utils.normalizeLineEndings(buffer.toString()).trim();
    }

    private String getHeader() throws Exception {
        StringBuilder buffer = new StringBuilder();
        while (buffer.length() < 4 ||
            !(buffer.charAt(buffer.length() - 4) == '\r' &&
                buffer.charAt(buffer.length() - 3) == '\n' &&
                buffer.charAt(buffer.length() - 2) == '\r' &&
                buffer.charAt(buffer.length() - 1) == '\n')) {
            buffer.appendCodePoint(input.read());
        }
        return Utils.normalizeLineEndings(buffer.toString());
    }

    private String getContent() throws Exception {
        int contentLength = request.contentLength;
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < contentLength; i++) {
            buffer.appendCodePoint(input.read());
        }
        return Utils.normalizeLineEndings(buffer.toString());
    }


    private void parse() throws Exception {
        String startLine = getStartLine();
        String[] opts = startLine.split(" ");

        request.method = opts[0];
        request.uri = opts[1];
        request.version = opts[2];

        if (request.uri.contains("?")) {
            int index = request.uri.indexOf("?");
            request.getParams = request.uri.substring(index + 1);
            request.uri = request.uri.substring(0, index);
        }

        request.header = getHeader();
        request.contentLength = parseContentLength();

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
