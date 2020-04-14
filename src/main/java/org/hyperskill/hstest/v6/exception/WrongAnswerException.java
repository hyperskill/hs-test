package org.hyperskill.hstest.v6.exception;

public class WrongAnswerException extends Exception {
    public WrongAnswerException(String errorText) {
        super(errorText);
    }
}
