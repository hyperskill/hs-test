package org.hyperskill.hstest.outcomes;

import org.hyperskill.hstest.exception.FailureHandler;
import org.hyperskill.hstest.exception.StackTraceUtils;

public class FatalErrorOutcome extends Outcome {

    public FatalErrorOutcome(int testNum, Throwable cause) {
        super(testNum, FailureHandler.getReport(), "");
        stackTrace = StackTraceUtils.getStackTrace(cause);
        if (cause.getCause() != null) {
            stackTrace += "\n" + StackTraceUtils.getStackTrace(cause.getCause());
        }
    }

    @Override
    protected String getType() {
        return "Fatal error";
    }

    @Override
    protected String getTypeSuffix() {
        return ", please send the report to support@hyperskill.org";
    }
}
