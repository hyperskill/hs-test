package org.hyperskill.hstest.mocks.web.constants;

import java.util.NoSuchElementException;

public enum Methods {
    GET, POST, PUT, DELETE;

    public static Methods of(String method) {
        for (Methods val : Methods.values()) {
            if (val.toString().equals(method)) {
                return val;
            }
        }
        throw new NoSuchElementException("No method \"" + method + "\"");
    }
}
