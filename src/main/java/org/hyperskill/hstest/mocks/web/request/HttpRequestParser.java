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

    public static void parseUri(HttpRequest request) {
        String uri = request.uri;

        String schema = "://";
        String port = ":";
        String host = "/";
        String params = "?";
        String anchor = "#";

        if (uri.contains(schema)) {
            int index = uri.indexOf(schema);
            request.schema = uri.substring(0, index);
            uri = uri.substring(index + schema.length());
        }

        if (!uri.contains(host)) {
            uri += host;
        }

        int hostIndex = uri.indexOf(host);
        String hostAndPort = uri.substring(0, hostIndex);

        if (hostAndPort.contains(port)) {
            int portIndex = hostAndPort.indexOf(port);
            request.host = hostAndPort.substring(0, portIndex);
            request.port = Integer.parseInt(hostAndPort.substring(portIndex + port.length()));
        } else {
            request.host = hostAndPort;
        }

        uri = uri.substring(hostIndex);

        if (uri.contains(anchor)) {
            int index = uri.indexOf(anchor);
            request.anchor = uri.substring(index + anchor.length());
            uri = uri.substring(0, index);
        }

        if (uri.contains(params)) {
            int index = uri.indexOf(params);
            String strGetParams = uri.substring(index + 1);
            String[] arrayParams = strGetParams.split("&");

            for (String param : arrayParams) {
                String[] parts = param.split("=");
                String key = parts[0];
                String value = parts.length == 1 ? "" : parts[1];
                request.params.put(key, value);
            }

            uri = uri.substring(0, index);
        }

        request.endpoint = uri;
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

            if (key.equals(CONTENT_LENGTH)) {
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
        request.uri = opts[1];
        request.version = opts[2];

        parseUri(request);
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
