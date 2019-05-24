package org.hyperskill.hstest.android.mocks;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ServerSocketMock extends BaseSocketMock {

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private ServerSocket serverSocket;
    private final List<String> requests;

    public ServerSocketMock(int port, List<String> requests) {
        super(port);
        this.requests = requests;
    }

    private Socket setUpSocket() throws IOException {
        serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));
        serverSocket.setReuseAddress(true);
        serverSocket.setSoTimeout(1000);
        return serverSocket.accept();
    }

    private void connect() throws IOException {
        socket = setUpSocket();
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
    }

    private void disconnect() throws IOException {
        input.close();
        output.close();
        socket.close();
        serverSocket.close();
    }

    @Override
    public void run() {
        List<String> messagesReceived = new ArrayList<>();
        try {
            connect();
            socket.setSoTimeout(1000);
            String message = input.readUTF();
            messagesReceived.add(message);
            if (message.trim().equals(message.trim())) {
                output.writeUTF("All files were sent!");
            }
            else output.writeUTF("");
            disconnect();
        } catch (Exception ex) {
            messagesReceived.add(ex.getMessage());
        }
    }
}
