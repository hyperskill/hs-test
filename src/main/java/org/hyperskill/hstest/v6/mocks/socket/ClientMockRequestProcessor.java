package org.hyperskill.hstest.v6.mocks.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface ClientMockRequestProcessor {

    void processRequest(DataOutputStream output, String request) throws IOException;

    String processResponse(DataInputStream input, String response) throws IOException;

}
