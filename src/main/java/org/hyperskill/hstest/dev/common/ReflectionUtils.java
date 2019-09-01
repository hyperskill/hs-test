package org.hyperskill.hstest.dev.common;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionUtils {

    private ReflectionUtils() {}

    public static Method getMainMethod(Class<?> clazz) throws Exception {

        Method mainMethod;
        try {
            mainMethod = clazz.getDeclaredMethod("main", String[].class);
            mainMethod.setAccessible(true);
        } catch (NoSuchMethodException ex) {
            throw new Exception("No main method found");
        }

        boolean isMethodStatic = Modifier.isStatic(mainMethod.getModifiers());

        if (!isMethodStatic) {
            throw new Exception("Main method is not static");
        }

        return mainMethod;
    }

}
