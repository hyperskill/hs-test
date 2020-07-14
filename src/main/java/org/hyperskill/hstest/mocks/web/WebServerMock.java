package org.hyperskill.hstest.mocks.web;

import org.hyperskill.hstest.mocks.web.request.HttpRequest;
import org.hyperskill.hstest.mocks.web.request.HttpRequestParser;
import org.hyperskill.hstest.testcase.Process;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class WebServerMock implements Process {

    private static final String DELIM = "/";

    public static void main(String[] args) { // for testing
        WebServerMock ws = new WebServerMock(12345);
        ws.start();
        ws.run();
    }

    private ServerSocket serverSocket;
    private final Map<String, String> pages = new HashMap<>();
    private final int port;

    private boolean isStarted = false;
    private boolean isStopped = false;

    public WebServerMock(int port) {
        this.port = port;
    }

    public WebServerMock setPage(String url, String content) {
        return setPage(url, new WebPage().setContent(content));
    }

    public WebServerMock setPage(String url, WebPage page) {
        if (!url.startsWith(DELIM)) {
            url = DELIM + url;
        }
        pages.put(url, page.contentWithHeader());
        return this;
    }

    private String resolveRequest(DataInputStream input) {
        HttpRequest request = HttpRequestParser.parse(input);
        return request != null ? request.getEndpoint() : null;
    }

    private void sendResponse(String path, DataOutputStream output) throws Exception {
        String response;
        if (path == null) {
            response = WebPage.NOT_FOUND;
        } else {
            if (!path.startsWith(DELIM)) {
                path = DELIM + path;
            }
            response = pages.getOrDefault(path, WebPage.NOT_FOUND);
        }
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
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ignored) { }
    }

    @Override
    public void run() {
        try {
            while (serverSocket != null && !serverSocket.isClosed()) {
                isStarted = true;
                isStopped = false;
                handle(serverSocket.accept());
            }
        } catch (Exception ignored) { }
        isStarted = false;
        isStopped = true;
    }

    @Override
    public void stop() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException ignored) { }
    }

    @Override
    public boolean isStarted() {
        return isStarted;
    }

    @Override
    public boolean isStopped() {
        return isStopped;
    }
}
