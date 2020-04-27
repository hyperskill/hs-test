package org.hyperskill.hstest.common;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
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

    public static int getLineNumber(Method method) {
        int result = 0;

        String methodName = method.getName();
        String className = method.getDeclaringClass().getCanonicalName();
        ClassPool pool = ClassPool.getDefault();

        try {
            CtClass cc = pool.get(className);
            CtMethod ctMethod = cc.getDeclaredMethod(methodName);
            result = ctMethod.getMethodInfo().getLineNumber(0);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }
}
