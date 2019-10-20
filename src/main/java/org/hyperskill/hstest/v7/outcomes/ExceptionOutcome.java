package org.hyperskill.hstest.v7.outcomes;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import static org.hyperskill.hstest.v7.exception.StackTraceUtils.filterStackTrace;
import static org.hyperskill.hstest.v7.exception.StackTraceUtils.getStackTrace;


public class ExceptionOutcome extends Outcome {

    public ExceptionOutcome(int testNum, Throwable cause, String feedback) {
        testNumber = testNum;
        stackTrace = filterStackTrace(getStackTrace(cause));

        errorText = feedback;

        if (cause instanceof InputMismatchException
            && stackTrace.contains("java.util.Scanner")) {

            errorText += "\n\nProbably you have nextInt() (or similar Scanner method) " +
                "followed by nextLine() - in this situation nextLine() often gives an " +
                "empty string and another one nextLine() call gives correct string.";

        } else if (cause instanceof NoSuchElementException
            && stackTrace.contains("java.util.Scanner")) {

            errorText = "\n\nProbably your program run out of input " +
                "(Scanner tried to read more than expected).";

        }
    }

    @Override
    protected String getType() {
        return "Exception";
    }
}
