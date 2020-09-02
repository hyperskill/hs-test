package org.hyperskill.hstest.common;

import org.hyperskill.hstest.exception.outcomes.ErrorWithFeedback;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static Method getMainMethod(Class<?> clazz) {
        Method mainMethod;
        try {
            mainMethod = clazz.getDeclaredMethod("main", String[].class);
            mainMethod.setAccessible(true);
        } catch (NoSuchMethodException ex) {
            throw new ErrorWithFeedback(
                    "No main method found in class "
                    + clazz.getCanonicalName()
            );
        }

        boolean isMethodStatic = Modifier.isStatic(mainMethod.getModifiers());

        if (!isMethodStatic) {
            throw new ErrorWithFeedback(
                    "Main method is not static in class "
                    + clazz.getCanonicalName()
            );
        }

        boolean isMethodPublic = Modifier.isPublic(mainMethod.getModifiers());

        if (!isMethodPublic) {
            throw new ErrorWithFeedback(
                    "Main method is not public in class "
                            + clazz.getCanonicalName()
            );
        }

        return mainMethod;
    }
}
