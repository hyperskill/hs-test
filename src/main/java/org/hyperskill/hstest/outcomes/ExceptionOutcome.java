package org.hyperskill.hstest.outcomes;

import org.hyperskill.hstest.exception.outcomes.ExceptionWithFeedback;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import static org.hyperskill.hstest.exception.StackTraceUtils.filterStackTrace;
import static org.hyperskill.hstest.exception.StackTraceUtils.getStackTrace;

public class ExceptionOutcome extends Outcome {

    public ExceptionOutcome(int testNum, ExceptionWithFeedback ex) {
        Throwable cause = ex.getRealException();
        String feedback = ex.getErrorText();

        testNumber = testNum;
        stackTrace = filterStackTrace(getStackTrace(cause));

        errorText = feedback;

        if (stackTrace.contains("java.util.Scanner")) {
            if (cause instanceof InputMismatchException) {

                errorText += "\n\nProbably you have nextInt() (or similar Scanner method) "
                    + "followed by nextLine() - in this situation nextLine() often gives an "
                    + "empty string and another one nextLine() call gives correct string.";

            } else if (cause instanceof NoSuchElementException) {

                errorText = "\n\nProbably your program run out of input "
                    + "(Scanner tried to read more than expected).";

            }
        }
    }

    @Override
    protected String getType() {
        return "Exception";
    }
}
