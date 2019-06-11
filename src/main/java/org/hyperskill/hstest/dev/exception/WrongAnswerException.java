package org.hyperskill.hstest.dev.exception;

public class WrongAnswerException extends Exception {
    public WrongAnswerException(String errorText) {
        super(errorText);
    }
}
