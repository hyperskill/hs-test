package org.hyperskill.hstest.v3.mocks.socket;

import org.hyperskill.hstest.v3.testcase.Process;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientSocketMock implements Process {

    public final int port;
    private ClientMockRequestProcessor clientMockRequestProcessor;
    private List<String> requestsToServer = new ArrayList<>();
    public List<String> responsesFromServer = new ArrayList<>();
    public Exception thrown = null;

    private boolean isStarted = false;
    private boolean isStopped = false;


    public ClientSocketMock(int port, List<String> requestsToServer) {
        this(port, requestsToServer, new ClientMockRequestProcessor() {
            @Override
            public void processRequest(DataOutputStream output, String request)  throws IOException {
                output.writeUTF(request);
            }

            @Override
            public String processResponse(DataInputStream input, String response) throws IOException {
                return input.readUTF();
            }
        });
    }

    public ClientSocketMock(int port,
                            List<String> requestsToServer,
                            ClientMockRequestProcessor clientMockRequestProcessor) {

        this.port = port;
        this.requestsToServer = requestsToServer;
        this.clientMockRequestProcessor = clientMockRequestProcessor;
    }

    @Override
    public void start() {
        isStarted = true;
    }

    @Override
    public void stop() {
        isStopped = true;
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
            String[] requests = new String[requestsToServer.size()];
            for (int i = 0; i < requestsToServer.size(); i++) {
                requests[i] = requestsToServer.get(i);
            }
            DataInputStream input;
            DataOutputStream output;
            Socket socket;
            for (int i = 0; i < requests.length; i++) {
                socket = new Socket("127.0.0.1", port);
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());

                socket.setSoTimeout(1000);

                clientMockRequestProcessor.processRequest(output, requests[i]);

                responsesFromServer.add(clientMockRequestProcessor.processResponse(input, requests[i]));

                input.close();
                output.close();
                socket.close();
            }
        } catch (Exception ex) {
            thrown = ex;
        }
    }
}
