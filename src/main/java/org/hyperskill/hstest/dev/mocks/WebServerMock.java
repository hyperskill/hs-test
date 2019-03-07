package org.hyperskill.hstest.dev.mocks;

import org.hyperskill.hstest.dev.common.Utils;
import org.hyperskill.hstest.dev.testcase.Process;

import java.io.*;
import java.net.*;
import java.util.*;

public class WebServerMock implements Process {

    private ServerSocket serverSocket;
    private Map<String, String> pages = new HashMap<>();
    private int port;

    private static final String request =
        "GET __path__ HTTP/1.1\r\n" +
        "Host: __host__\r\n\r\n";

    private static final String notFound =
        "HTTP/1.1 404 Not Found\r\n" +
        "Content-Length: 0\r\n\r\n";

    private static final String response =
        "HTTP/1.1 200 OK\r\n" +
        "Content-Length: __length__\r\n\r\n";

    public WebServerMock(int port) {
        this.port = port;
    }

    private String contentWithHeader(String content) {
        String size = Integer.toString(content.length());
        String header = response.replaceAll("__length__", size);
        return header + content;
    }

    public WebServerMock setPage(String url, String content) {
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        pages.put(url, contentWithHeader(content));
        return this;
    }

    private String resolveRequest(DataInputStream input) throws Exception {

        StringBuilder buffer = new StringBuilder();
        while (buffer.length() < 4 ||
              !(buffer.charAt(buffer.length() - 4) == '\r' &&
                buffer.charAt(buffer.length() - 3) == '\n' &&
                buffer.charAt(buffer.length() - 2) == '\r' &&
                buffer.charAt(buffer.length() - 1) == '\n')) {
            buffer.appendCodePoint(input.read());
        }

        String header = Utils.normalizeLineEndings(buffer.toString());;
        String query = header.split("\n")[0];
        return query.split(" ")[1];
    }

    private void sendResponse(String path, DataOutputStream output) throws Exception {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        String response = pages.getOrDefault(path, notFound);
        for (char c : response.toCharArray()) {
            output.write(c);
        }
    }

    private void handle(Socket socket) throws Exception {
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        sendResponse(resolveRequest(input), output);
        input.close();
        output.close();
        socket.close();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                handle(serverSocket.accept());
            }
        } catch (Exception ignored) { }
    }

    @Override
    public void close() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }
}
