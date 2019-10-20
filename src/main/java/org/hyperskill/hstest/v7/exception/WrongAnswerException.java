package org.hyperskill.hstest.v7.exception;


public class WrongAnswerException extends Exception {
    public WrongAnswerException(String errorText) {
        super(errorText);
    }
}
