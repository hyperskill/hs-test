package org.hyperskill.hstest.dynamic.security;

public class ProgramExited extends Error {
    private final int status;

    public ProgramExited(int status) {
        super("Tried to exit with status " + status + ".");
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
