package org.hyperskill.hstest.v7.exception;

import org.hyperskill.hstest.v7.statics.ObjectsCloner;
import org.hyperskill.hstest.v7.statics.StaticFieldsManager;
import org.hyperskill.hstest.v7.statics.serialization.Serialized;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static org.hyperskill.hstest.v7.exception.StackTraceUtils.*;


public class FailureHandler {

    public static boolean detectStaticCloneFails() {
        return !StaticFieldsManager.cantClone.isEmpty()
            || !StaticFieldsManager.cantReset.isEmpty()
            || !ObjectsCloner.circularLinks.isEmpty()
            || !ObjectsCloner.cantDeserialize.isEmpty()
            || !ObjectsCloner.cantSerialize.isEmpty();
    }

    public static boolean isUserFailed(Throwable t) {
        // If user failed then t == InvocationTargetException
        // and t.getCause() == Actual user exception
        return t.getCause() != null
            && t instanceof InvocationTargetException;
    }

    public static Throwable getUserException(Throwable t) {
        if (isUserFailed(t)) {
            return t.getCause();
        }
        return null;
    }

    public static String getReport() {
        String os = System.getProperty("os.name");
        String java = System.getProperty("java.version");
        String vendor = System.getProperty("java.vendor");

        String info =
            "OS " + os + "\n" +
            "Java " + java + "\n" +
            "Vendor " + vendor + "\n" +
            "Testing library version 7";

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
}
