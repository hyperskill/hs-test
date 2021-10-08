package org.hyperskill.hstest.mocks.web.constants;

public enum StatusCodes {
    CODE_200("200 OK"),
    CODE_404("404 Not Found");

    String code;
    StatusCodes(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
