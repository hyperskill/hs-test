package org.hyperskill.hstest.dev.exception;

import org.hyperskill.hstest.dev.common.FileUtils;
import org.hyperskill.hstest.dev.statics.ObjectsCloner;
import org.hyperskill.hstest.dev.statics.StaticFieldsManager;
import org.hyperskill.hstest.dev.statics.serialization.Serialized;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileSystemException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import static org.hyperskill.hstest.dev.exception.StackTraceUtils.*;


public class FailureHandler {

    public static boolean detectStaticCloneFails() {
        return !StaticFieldsManager.cantClone.isEmpty()
            || !StaticFieldsManager.cantReset.isEmpty()
            || !ObjectsCloner.circularLinks.isEmpty()
            || !ObjectsCloner.cantDeserialize.isEmpty()
            || !ObjectsCloner.cantSerialize.isEmpty();
    }

    public static final String avoidStaticsMsg =
        "We detected that you are using static variables, " +
        "but they are not fully supported in testing. " +
        "It might happen that if you try to avoid using " +
        "them you will pass this stage.";

    private static String getReport() {
        String os = System.getProperty("os.name");
        String java = System.getProperty("java.version");
        String vendor = System.getProperty("java.vendor");

        String info =
            "OS " + os + "\n" +
            "Java " + java + "\n" +
            "Vendor " + vendor + "\n" +
            "Testing library version 5";

        StringBuilder cantClone = new StringBuilder("Cannot be cloned: ");

        if (StaticFieldsManager.cantClone.isEmpty()) {
            cantClone.append("nothing");
        } else {
            for (Class clazz : StaticFieldsManager.cantClone.keySet()) {
                Exception exception = StaticFieldsManager.cantClone.get(clazz);
                String stackTrace = getStackTrace(exception);
                String filteredStackTrace = removeNonLibraryClasses(stackTrace);

                cantClone.append("\n");
                cantClone.append(clazz.toString());
                cantClone.append("\n");
                cantClone.append(filteredStackTrace);
            }
        }

        StringBuilder cantReset = new StringBuilder("Cannot be reset: ");

        if (StaticFieldsManager.cantReset.isEmpty()) {
            cantReset.append("nothing");
        } else {
            for (Field field : StaticFieldsManager.cantReset.keySet()) {
                Exception exception = StaticFieldsManager.cantReset.get(field);
                String stackTrace = getStackTrace(exception);
                String filteredStackTrace = removeNonLibraryClasses(stackTrace);

                cantReset.append("\n");
                cantReset.append(field.toString());
                cantReset.append("\n");
                cantReset.append(filteredStackTrace);
            }
        }

        StringBuilder cantDeserialize = new StringBuilder("Cannot deserialize: ");

        if (ObjectsCloner.cantDeserialize.isEmpty()) {
            cantDeserialize.append("nothing");
        } else {
            for (Serialized serialized : ObjectsCloner.cantDeserialize) {

                cantDeserialize.append("\n");
                cantDeserialize.append(serialized.sourceClass.toString());
                cantDeserialize.append("\nJackson:\n");

                if (serialized.jacksonDeserialized != null) {
                    String stackTrace = getStackTrace(serialized.jacksonDeserialized);
                    String filteredStackTrace = removeNonLibraryClasses(stackTrace);
                    cantDeserialize.append(filteredStackTrace);
                } else {
                    cantDeserialize.append("nothing");
                }

                cantDeserialize.append("\nJsonIo:\n");

                if (serialized.jsonioDeserialized != null) {
                    String stackTrace = getStackTrace(serialized.jsonioDeserialized);
                    String filteredStackTrace = removeNonLibraryClasses(stackTrace);
                    cantDeserialize.append(filteredStackTrace);
                } else {
                    cantDeserialize.append("nothing");
                }
            }
        }

        StringBuilder cantSerialize = new StringBuilder("Cannot serialize: ");

        if (ObjectsCloner.cantSerialize.isEmpty()) {
            cantSerialize.append("nothing");
        } else {
            for (Serialized serialized : ObjectsCloner.cantSerialize) {

                cantSerialize.append("\n");
                cantSerialize.append(serialized.sourceClass.toString());
                cantSerialize.append("\nJackson:\n");

                if (serialized.jacksonSerialized != null) {
                    String stackTrace = getStackTrace(serialized.jacksonSerialized);
                    String filteredStackTrace = removeNonLibraryClasses(stackTrace);
                    cantSerialize.append(filteredStackTrace);
                } else {
                    cantSerialize.append("nothing");
                }

                cantSerialize.append("\nJsonIo:\n");

                if (serialized.jsonioSerialized != null) {
                    String stackTrace = getStackTrace(serialized.jsonioSerialized);
                    String filteredStackTrace = removeNonLibraryClasses(stackTrace);
                    cantSerialize.append(filteredStackTrace);
                } else {
                    cantSerialize.append("nothing");
                }
            }
        }

        StringBuilder circularLinks = new StringBuilder("Circular links: ");

        if (ObjectsCloner.circularLinks.isEmpty()) {
            circularLinks.append("nothing");
        } else {
            for (Serialized serialized : ObjectsCloner.circularLinks) {
                circularLinks.append("\n");
                circularLinks.append(serialized.sourceClass.toString());
            }
        }

        return info + "\n\n" +
            cantClone.toString() + "\n" +
            cantReset.toString() + "\n" +
            cantDeserialize.toString() + "\n" +
            cantSerialize.toString() + "\n" +
            circularLinks.toString() + "\n";
    }

    public static String getFeedback(Throwable t, int currTest) {

        String errorText;
        String stackTraceInfo;

        if (t instanceof WrongAnswerException) {

            errorText = "Wrong answer in test #" + currTest
                + "\n\n" + t.getMessage().trim();

            if (FailureHandler.detectStaticCloneFails()) {
                errorText += "\n\n" + FailureHandler.avoidStaticsMsg;
            }

            stackTraceInfo = "";

        } else if (t.getCause() != null &&
            t instanceof InvocationTargetException) {
            // If user failed then t == InvocationTargetException
            // and t.getCause() == Actual user exception
            errorText = "Exception in test #" + currTest;
            stackTraceInfo = filterStackTrace(getStackTrace(t.getCause()));

            Throwable cause = t.getCause();

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

            if (detectStaticCloneFails()) {
                errorText += "\n\n" + avoidStaticsMsg;
            }

        } else if (t instanceof FileSystemException) {

            errorText = "Error in test #" + currTest ;
            stackTraceInfo = "";

            // without "class "
            String exceptionName = t.getClass().toString().substring(6);

            String file = ((FileSystemException) t).getFile();

            if (file.startsWith(FileUtils.CURRENT_DIR)) {
                file = file.substring(FileUtils.CURRENT_DIR.length());
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
            if (t.getCause() == null) {
                stackTraceInfo = getStackTrace(t);
            } else {
                stackTraceInfo = getStackTrace(t) +
                    "\n" + getStackTrace(t.getCause());
            }
        }

        return (errorText + "\n\n" + stackTraceInfo).trim();
    }
}
