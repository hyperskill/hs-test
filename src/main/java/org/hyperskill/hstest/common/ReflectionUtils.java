package org.hyperskill.hstest.common;

import org.hyperskill.hstest.exception.outcomes.FatalError;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class ReflectionUtils {

    private ReflectionUtils() { }

    public static Method getMainMethod(Class<?> clazz) {
        Method mainMethod;
        try {
            mainMethod = clazz.getDeclaredMethod("main", String[].class);
            mainMethod.setAccessible(true);
        } catch (NoSuchMethodException ex) {
            throw new FatalError("No main method found");
        }

        boolean isMethodStatic = Modifier.isStatic(mainMethod.getModifiers());

        if (!isMethodStatic) {
            throw new FatalError("Main method is not static");
        }

        return mainMethod;
    }
}
