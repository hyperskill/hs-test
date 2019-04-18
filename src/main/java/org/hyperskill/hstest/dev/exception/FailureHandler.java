package org.hyperskill.hstest.dev.exception;

import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;

import static org.hyperskill.hstest.dev.exception.StackTraceUtils.filterStackTrace;
import static org.hyperskill.hstest.dev.exception.StackTraceUtils.getStackTrace;


public class FailureHandler {

    public static String getFeedback(Exception ex, int currTest) {

        String errorText;
        String stackTraceInfo;
        if (ex.getCause() != null &&
            ex instanceof InvocationTargetException) {
            // If user failed then ex == InvocationTargetException
            // and ex.getCause() == Actual user exception
            errorText = "Exception in test #" + currTest;
            stackTraceInfo = filterStackTrace(getStackTrace(ex.getCause()));

            if (ex.getCause() instanceof NoSuchElementException
                && stackTraceInfo.contains("java.util.Scanner")) {
                stackTraceInfo = "Maybe you created more than one instance of Scanner? " +
                    "You should use a single Scanner in program.\n\n" + stackTraceInfo;
            }

            if (stackTraceInfo.contains("java.lang.Runtime.exit")) {
                errorText = "Error in test #" + currTest + " - Tried to exit";
            }
        } else {
            String whenErrorHappened;
            if (currTest == 0) {
                whenErrorHappened = "during testing";
            } else {
                whenErrorHappened = "in test #" + currTest;
            }

            errorText = "Fatal error " + whenErrorHappened +
                ", please send the report to Hyperskill team.";
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
