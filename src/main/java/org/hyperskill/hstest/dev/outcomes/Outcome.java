package org.hyperskill.hstest.dev.outcomes;

import org.hyperskill.hstest.dev.exception.FailureHandler;

public abstract class Outcome {

    int testNumber = 0;
    String errorText = "";
    String stackTrace = "";

    private static final String avoidStaticsMsg =
        "We detected that you are using static variables, " +
        "but they are not fully supported in testing. " +
        "It might happen that if you try to avoid using " +
        "them you will pass this stage.";

    protected abstract String getType();

    protected String getTypeSuffix() {
        return "";
    }

    @Override
    public final String toString() {

        String whenErrorHappened;
        if (testNumber == 0) {
            whenErrorHappened = " during testing";
        } else {
            whenErrorHappened = " in test #" + testNumber;
        }

        String result =
            getType() + whenErrorHappened + getTypeSuffix();

        if (!errorText.isEmpty()) {
            result += "\n\n" + errorText;
        }

        if (FailureHandler.detectStaticCloneFails()) {
            result += "\n\n" + avoidStaticsMsg;
        }

        if (!stackTrace.isEmpty()) {
            result += "\n\n" + stackTrace;
        }

        return result.trim();
    }
}
