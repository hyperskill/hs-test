package org.hyperskill.hstest.outcomes;

import org.hyperskill.hstest.exception.FailureHandler;
import org.hyperskill.hstest.exception.StackTraceUtils;

public class UnexpectedErrorOutcome extends Outcome {

    public UnexpectedErrorOutcome(int testNum, Throwable cause) {
        testNumber = testNum;
        errorText = "We have recorded this bug and will fix it soon.\n\n"
                + FailureHandler.getReport();
        stackTrace = StackTraceUtils.getStackTrace(cause);
        if (cause.getCause() != null) {
            stackTrace += "\n" + StackTraceUtils.getStackTrace(cause.getCause());
        }
    }

    @Override
    protected String getType() {
        return "Unexpected error";
    }
}
