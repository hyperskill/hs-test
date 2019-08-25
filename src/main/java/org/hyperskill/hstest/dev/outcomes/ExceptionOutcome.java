package org.hyperskill.hstest.dev.outcomes;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import static org.hyperskill.hstest.dev.exception.StackTraceUtils.filterStackTrace;
import static org.hyperskill.hstest.dev.exception.StackTraceUtils.getStackTrace;

public class ExceptionOutcome extends Outcome {

    public ExceptionOutcome(int testNum, Throwable cause) {
        testNumber = testNum;
        stackTrace = filterStackTrace(getStackTrace(cause));

        if (cause instanceof InputMismatchException
            && stackTrace.contains("java.util.Scanner")) {

            errorText = "Probably you have nextInt() (or similar Scanner method) " +
                "followed by nextLine() - in this situation nextLine() often gives an " +
                "empty string and the second nextLine() gives correct string.";

        } else if (cause instanceof NoSuchElementException
            && stackTrace.contains("java.util.Scanner")) {

            errorText = "Maybe you created more than one instance of Scanner? " +
                "You should use a single Scanner in program. " +
                "If not, this type of exception also happens if you " +
                "run out of input (tried to read more than expected).";
        }
    }

    @Override
    protected String getType() {
        return "Exception";
    }
}
