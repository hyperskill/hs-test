package org.hyperskill.hstest.v4.mocks;

abstract public class BaseSocketMock implements Runnable {

    public final int port;
    public final String address = "127.0.0.1";

    public BaseSocketMock(int port) {
        this.port = port;
    }

}
