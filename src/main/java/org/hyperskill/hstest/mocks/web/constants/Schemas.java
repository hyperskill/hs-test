package org.hyperskill.hstest.mocks.web.constants;

public enum Schemas {
    HTTP, HTTPS;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
