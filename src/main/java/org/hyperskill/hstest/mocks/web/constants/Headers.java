package org.hyperskill.hstest.mocks.web.constants;

public enum Headers {
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    HOST("Host"),
    AUTHORIZATION("Authorization");

    String type;
    Headers(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
