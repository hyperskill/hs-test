package org.hyperskill.hstest.outcomes;

import org.hyperskill.hstest.common.FileUtils;
import org.hyperskill.hstest.exception.outcomes.ErrorWithFeedback;
import org.hyperskill.hstest.exception.testing.InfiniteLoopException;
import org.hyperskill.hstest.exception.testing.TimeLimitException;

import java.nio.file.FileSystemException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorOutcome extends Outcome {

    public ErrorOutcome(int testNum, Throwable cause) {
        testNumber = testNum;
        if (cause instanceof FileSystemException) {
            initFileSystemException((FileSystemException) cause);

        } else if (cause instanceof TimeLimitException) {
            initTimeLimitException((TimeLimitException) cause);

        } else if (cause instanceof NumberFormatException) {
            initNumberFormatException((NumberFormatException) cause);

        } else if (cause instanceof ErrorWithFeedback) {
            errorText = ((ErrorWithFeedback) cause).getErrorText();

        } else if (cause instanceof InfiniteLoopException) {
            errorText = "Infinite loop detected.\n" + cause.getMessage();
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

    private void initNumberFormatException(NumberFormatException ex) {
        StackTraceElement[] elements = ex.getStackTrace();

        String lastPrimitive = "";

        for (StackTraceElement elem : elements) {
            switch (elem.getClassName()) {
                case "java.lang.Integer":
                case "java.lang.Long":
                case "java.lang.Float":
                case "java.lang.Double":
                case "java.lang.Short":
                case "java.lang.Byte":
                    lastPrimitive = elem.getClassName();
            }
        }

        String className;
        if (lastPrimitive.isEmpty()) {
            className = "number";
        } else {
            className = lastPrimitive.replace("java.lang.", "");
        }

        errorText = "Cannot parse " + className;

        Matcher matcher = Pattern.compile("[^\"]*\"(.*)\"")
                .matcher(ex.getMessage().replace("\n", " "));

        if (matcher.find()) {
            errorText += " from the output part \"" + matcher.group(1).trim() + "\"";
        }

        errorText += "\n\n" + ex.toString().replace("\n", " ");
    }

    @Override
    protected String getType() {
        return "Error";
    }
}
