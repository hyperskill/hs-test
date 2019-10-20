package org.hyperskill.hstest.v7.outcomes;

import org.hyperskill.hstest.v7.common.FileUtils;
import org.hyperskill.hstest.v7.exception.TimeLimitException;

import java.nio.file.FileSystemException;


public class ErrorOutcome extends Outcome {

    public ErrorOutcome(int testNum, Throwable cause) {
        testNumber = testNum;
        if (cause instanceof FileSystemException) {
            // without "class "
            String exceptionName = cause.getClass().toString().substring(6);

            String file = ((FileSystemException) cause).getFile();

            if (file.startsWith(FileUtils.CURRENT_DIR)) {
                file = file.substring(FileUtils.CURRENT_DIR.length());
            }

            errorText = exceptionName + "\n\nThe file " + file +
                " can't be deleted after the end of the test. " +
                "Probably you didn't close File or Scanner.";

        } else if (cause instanceof TimeLimitException) {
            int timeLimit = ((TimeLimitException) cause).getTimeLimitMs();
            String timeUnit;
            if (timeLimit > 1999) {
                timeLimit /= 1000;
                timeUnit = "seconds";
            } else {
                timeUnit = "milliseconds";
            }
            errorText = "In this test, the program is running for a long time, " +
                "more than " + timeLimit + " " + timeUnit + ". Most likely, " +
                "the program has gone into an infinite loop.";
        }
    }

    @Override
    protected String getType() {
        return "Error";
    }
}
