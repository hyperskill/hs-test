package org.hyperskill.hstest.v7.mocks.socket;

import org.hyperskill.hstest.v7.testcase.Process;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSocketMock implements Process {

    public final int port;
    public final ServerMockRequestProcessor serverMockRequestProcessor;
    public List<String> responsesFromClient = new ArrayList<>();
    String[] setupCommands;
    public Exception thrown = null;

    ServerSocket serverSocket = null;
    boolean isListening;

    private boolean isStarted = false;
    private boolean isStopped = false;

    public ServerSocketMock(int port) {
        this(port, new String[0]);
    }

    public ServerSocketMock(int port, String[] setupCommands) {
        this(port, setupCommands, new ServerMockRequestProcessor() {
            @Override
            public void setup(String[] setupCommands) {
                //Nothing by default
            }

            @Override
            public boolean processRequest(DataOutputStream output,
                                          DataInputStream input,
                                          String request) throws IOException {
                output.writeUTF("");
                return false;
            }

            @Override
            public String processResponse(DataInputStream input) throws IOException {
                return input.readUTF();
            }
        });
    }

    public ServerSocketMock(int port, ServerMockRequestProcessor serverMockRequestProcessor) {
        this(port, new String[0], serverMockRequestProcessor);
    }

    public ServerSocketMock(int port,
                            String[] setupCommands,
                            ServerMockRequestProcessor serverMockRequestProcessor) {
        this.port = port;
        this.setupCommands = setupCommands;
        this.serverMockRequestProcessor = serverMockRequestProcessor;
    }

    @Override
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            serverSocket.setSoTimeout(1000);
            isListening = true;
            isStarted = true;
        } catch (Exception ignored) { }
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

    @Override
    public void run() {
        try {
            serverMockRequestProcessor.setup(setupCommands);
            DataInputStream input;
            DataOutputStream output;
            Socket socket;

            socket = serverSocket.accept();
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            socket.setSoTimeout(1000);
            while (isListening) {
                String clientRequest;
                clientRequest = serverMockRequestProcessor.processResponse(input);
                responsesFromClient.add(clientRequest);
                isListening = serverMockRequestProcessor.processRequest(output, input, clientRequest);
            }
            stop();
        } catch (Exception ex) {
            thrown = ex;
        }
        isStopped = true;
    }
}
