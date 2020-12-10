package org.hyperskill.hstest.dynamic.security;

public class ExitException extends Error {
    private final int status;

    public ExitException(int status) {
        super("Tried to exit with status " + status + ".");
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
