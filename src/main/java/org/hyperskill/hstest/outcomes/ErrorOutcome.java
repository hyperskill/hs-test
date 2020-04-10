package org.hyperskill.hstest.outcomes;

import org.hyperskill.hstest.common.FileUtils;
import org.hyperskill.hstest.exception.outcomes.ErrorWithFeedback;
import org.hyperskill.hstest.exception.testing.TimeLimitException;

import java.nio.file.FileSystemException;

public class ErrorOutcome extends Outcome {

    public ErrorOutcome(int testNum, Throwable cause) {
        testNumber = testNum;
        if (cause instanceof FileSystemException) {
            initFileSystemException((FileSystemException) cause);

        } else if (cause instanceof TimeLimitException) {
            initTimeLimitException((TimeLimitException) cause);

        } else if (cause instanceof ErrorWithFeedback) {
            errorText = ((ErrorWithFeedback) cause).getErrorText();
        }
    }

    private void initFileSystemException(FileSystemException ex) {
        // without "class "
        String exceptionName = ex.getClass().toString().substring(6);
        String file = ex.getFile();

        if (file.startsWith(FileUtils.CURRENT_DIR)) {
            file = file.substring(FileUtils.CURRENT_DIR.length());
        }

        errorText = exceptionName + "\n\nThe file " + file
            + " can't be deleted after the end of the test. "
            + "Probably you didn't close File or Scanner.";
    }

    private void initTimeLimitException(TimeLimitException ex) {
        int timeLimit = ex.getTimeLimitMs();
        String timeUnit = "milliseconds";
        if (timeLimit > 1999) {
            timeLimit /= 1000;
            timeUnit = "seconds";
        }
        errorText = "In this test, the program is running for a long time, "
            + "more than " + timeLimit + " " + timeUnit + ". Most likely, "
            + "the program has gone into an infinite loop.";
    }

    @Override
    protected String getType() {
        return "Error";
    }
}
