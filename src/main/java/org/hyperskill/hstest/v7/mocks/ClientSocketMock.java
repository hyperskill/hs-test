package org.hyperskill.hstest.v7.mocks;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSocketMock extends BaseSocketMock {

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public ClientSocketMock(int port) {
        super(port);
    }

    private Socket setUpSocket() throws IOException {
        return new Socket(InetAddress.getByName(address), port);
    }

    private void connect() throws IOException {
        socket = setUpSocket();
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {

    }
}
