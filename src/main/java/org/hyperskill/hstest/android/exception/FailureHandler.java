package org.hyperskill.hstest.android.exception;

import org.hyperskill.hstest.android.common.Utils;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileSystemException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import static org.hyperskill.hstest.android.exception.StackTraceUtils.*;


public class FailureHandler {

    private static String getReport() {
        String os = System.getProperty("os.name");
        String java = System.getProperty("java.version");
        String vendor = System.getProperty("java.vendor");

        return
            "OS " + os + "\n" +
            "Java " + java + "\n" +
            "Vendor " + vendor + "\n" +
            "Testing library version: android";
    }

    public static String getFeedback(Exception ex, int currTest) {

        String errorText;
        String stackTraceInfo;
        if (ex.getCause() != null &&
            ex instanceof InvocationTargetException) {
            // If user failed then ex == InvocationTargetException
            // and ex.getCause() == Actual user exception
            errorText = "Exception in test #" + currTest;
            stackTraceInfo = filterStackTrace(getStackTrace(ex.getCause()));

            Throwable cause = ex.getCause();

            if (cause instanceof InputMismatchException
                && stackTraceInfo.contains("java.util.Scanner")) {

                errorText += "\n\nProbably you have nextInt() (or similar Scanner method) " +
                    "followed by nextLine() - in this situation nextLine() often gives an " +
                    "empty string and the second nextLine() gives correct string.";

            } else if (cause instanceof NoSuchElementException
                && stackTraceInfo.contains("java.util.Scanner")) {

                errorText += "\n\nMaybe you created more than one instance of Scanner? " +
                    "You should use a single Scanner in program. " +
                    "If not, this type of exception also happens if you " +
                    "run out of input (tried to read more than expected).";
            }

            if (stackTraceInfo.contains("java.lang.Runtime.exit")) {
                errorText = "Error in test #" + currTest + " - Tried to exit";
            }

        } else if (ex instanceof FileSystemException) {

            errorText = "Error in test #" + currTest ;
            stackTraceInfo = "";

            // without "class "
            String exceptionName = ex.getClass().toString().substring(6);

            String file = ((FileSystemException) ex).getFile();

            if (file.startsWith(Utils.CURRENT_DIR)) {
                file = file.substring(Utils.CURRENT_DIR.length());
            }

            errorText += "\n\n" + exceptionName + "\n\nThe file " + file +
                " can't be deleted after the end of the test. " +
                "Probably you didn't close File or Scanner.";

        } else {

            String whenErrorHappened;
            if (currTest == 0) {
                whenErrorHappened = "during testing";
            } else {
                whenErrorHappened = "in test #" + currTest;
            }

            errorText = "Fatal error " + whenErrorHappened +
                ", please send the report to Hyperskill team.\n\n" + getReport();
            if (ex.getCause() == null) {
                stackTraceInfo = getStackTrace(ex);
            } else {
                stackTraceInfo = getStackTrace(ex) +
                    "\n" + getStackTrace(ex.getCause());
            }
        }

        return errorText + "\n\n" + stackTraceInfo;
    }
}
