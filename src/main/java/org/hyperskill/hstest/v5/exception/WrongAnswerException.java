package org.hyperskill.hstest.v5.exception;

public class WrongAnswerException extends Exception {
    public WrongAnswerException(String errorText) {
        super(errorText);
    }
}
