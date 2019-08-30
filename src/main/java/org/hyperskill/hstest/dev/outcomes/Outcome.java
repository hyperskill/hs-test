package org.hyperskill.hstest.dev.outcomes;

import org.hyperskill.hstest.dev.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.dev.exception.FailureHandler;
import org.hyperskill.hstest.dev.exception.WrongAnswerException;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileSystemException;

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

        result += "\n\n" +
            "We saved output of your program. You can see it below\n" +
            "Notice, that '>' symbol means the start of the input:\n\n";

        result += SystemOutHandler.getOutputWithInputInjected();

        return result.trim();
    }

    public static Outcome getOutcome(Throwable t, int currTest) {
        if (t instanceof WrongAnswerException) {
            return new WrongAnswerOutcome(currTest, t.getMessage().trim());

        } else if (t.getCause() != null &&
            t instanceof InvocationTargetException) {
            // If user failed then t == InvocationTargetException
            // and t.getCause() == Actual user exception
            return new ExceptionOutcome(currTest, t.getCause());

        } else if (t instanceof FileSystemException) {
            return new ErrorOutcome(currTest, t);

        } else {
            return new FatalErrorOutcome(currTest, t);
        }
    }
}
