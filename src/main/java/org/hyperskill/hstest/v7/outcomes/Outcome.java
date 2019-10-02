package org.hyperskill.hstest.v7.outcomes;

import org.hyperskill.hstest.v7.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.v7.exception.ExceptionWithFeedback;
import org.hyperskill.hstest.v7.exception.FailureHandler;
import org.hyperskill.hstest.v7.exception.TimeLimitException;
import org.hyperskill.hstest.v7.exception.WrongAnswerException;

import java.nio.file.FileSystemException;

import static org.hyperskill.hstest.v7.exception.FailureHandler.getUserException;
import static org.hyperskill.hstest.v7.exception.FailureHandler.isUserFailed;

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
            result += "\n\n" + errorText.trim();
        }

        if (FailureHandler.detectStaticCloneFails()) {
            result += "\n\n" + avoidStaticsMsg;
        }

        if (!stackTrace.isEmpty()) {
            result += "\n\n" + stackTrace;
        }

        String fullLog = SystemOutHandler.getOutputWithInputInjected();

        if (fullLog.trim().length() != 0) {
            result += "\n\n" +
                "Below you can see the output of your program during this test\n" +
                "Notice, that '>' symbol means the start of the input:\n\n";

            result += fullLog;
        }

        return result.trim();
    }

    public static Outcome getOutcome(Throwable t, int currTest) {
        if (t instanceof WrongAnswerException) {
            return new WrongAnswerOutcome(currTest, t.getMessage().trim());

        } else if (t instanceof ExceptionWithFeedback) {
            ExceptionWithFeedback ex = (ExceptionWithFeedback) t;
            Throwable realUserException = ex.getRealException();
            String errorText = ex.getErrorText();
            return new ExceptionOutcome(currTest, realUserException, errorText);

        } else if (isUserFailed(t)) {
            return new ExceptionOutcome(currTest, getUserException(t), "");

        } else if (t instanceof FileSystemException
            || t instanceof TimeLimitException) {

            return new ErrorOutcome(currTest, t);

        } else {
            return new FatalErrorOutcome(currTest, t);
        }
    }
}
