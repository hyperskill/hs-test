package org.hyperskill.hstest.dev.outcomes;

import org.hyperskill.hstest.dev.exception.FailureHandler;

import static org.hyperskill.hstest.dev.exception.StackTraceUtils.getStackTrace;

public class FatalErrorOutcome extends Outcome {

    public FatalErrorOutcome(int testNum, Throwable cause) {
        testNumber = testNum;
        errorText = FailureHandler.getReport();
        stackTrace = getStackTrace(cause);
        if (cause.getCause() != null) {
            stackTrace += "\n" + getStackTrace(cause.getCause());
        }
    }

    @Override
    protected String getType() {
        return "Fatal error";
    }

    @Override
    protected String getTypeSuffix() {
        return ", please send the report to Hyperskill team.";
    }
}
