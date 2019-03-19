package org.hyperskill.hstest.dev.mocks.web;

import org.hyperskill.hstest.dev.common.Utils;
import org.hyperskill.hstest.dev.testcase.Process;

import java.io.*;
import java.net.*;
import java.util.*;

public class WebServerMock implements Process {

    private ServerSocket serverSocket;
    private Map<String, String> pages = new HashMap<>();
    private int port;

    private boolean isStarted = false;
    private boolean isStopped = false;

    public WebServerMock(int port) {
        this.port = port;
    }

    public WebServerMock setPage(String url, String content) {
        return setPage(url, new WebPage().setContent(content));
    }

    public WebServerMock setPage(String url, WebPage page) {
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        pages.put(url, page.contentWithHeader());
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
        String response = pages.getOrDefault(path, WebPage.notFound);
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
        }
        catch (IOException ignored) { }
    }

    @Override
    public void run() {
        try {
            while (serverSocket != null && !serverSocket.isClosed()) {
                isStarted = true;
                handle(serverSocket.accept());
            }
        } catch (Exception ignored) { }
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
