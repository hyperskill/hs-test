package org.hyperskill.hstest.outcomes;

import org.hyperskill.hstest.dynamic.output.OutputHandler;
import org.hyperskill.hstest.exception.FailureHandler;

import static org.hyperskill.hstest.exception.StackTraceUtils.getStackTrace;

public class UnexpectedErrorOutcome extends Outcome {

    public UnexpectedErrorOutcome(int testNum, Throwable cause) {
        testNumber = testNum;
        errorText = "We have recorded this bug and will fix it soon.\n\n"
                + FailureHandler.getReport();
        stackTrace = getStackTrace(cause);
        if (cause.getCause() != null) {
            stackTrace += "\n" + getStackTrace(cause.getCause());
        }

        String programStderr = OutputHandler.getErr();
        if (programStderr.length() > 0) {
            stackTrace = stackTrace.trim() + "\n\n" + programStderr;
        }
    }

    @Override
    protected String getType() {
        return "Unexpected error";
    }
}
