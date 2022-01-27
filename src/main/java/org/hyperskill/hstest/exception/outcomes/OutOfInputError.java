package org.hyperskill.hstest.exception.outcomes;

public class OutOfInputError extends ErrorWithFeedback {
    public OutOfInputError() {
        super("Program ran out of input. You tried to read more than expected.");
    }
}
