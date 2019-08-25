package org.hyperskill.hstest.dev.outcomes;

import org.hyperskill.hstest.dev.common.FileUtils;

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
        }
    }

    @Override
    protected String getType() {
        return "Error";
    }
}
