package org.hyperskill.hstest.common;

import org.hyperskill.hstest.exception.outcomes.ErrorWithFeedback;
import org.hyperskill.hstest.exception.outcomes.OutcomeError;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class ReflectionUtils {

    private ReflectionUtils() { }

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

    public static Object invokeMethod(Method method, Object obj) {
        method.setAccessible(true);

        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        String feedback = "Cannot invoke method \""
            + className + "." + methodName + "\".";

        try {
            return method.invoke(obj);
        } catch (InvocationTargetException ex) {
            if (ex.getCause() instanceof OutcomeError) {
                throw (OutcomeError) ex.getCause();
            }
            throw new UnexpectedError(feedback, ex.getCause());
        } catch (IllegalAccessException ex) {
            if (!Modifier.isPublic(method.getModifiers())) {
                feedback += " Try to declare method as public.";
            }
            throw new UnexpectedError(feedback, ex);
        }
    }

    public static <V, C extends Class<V>> Stream<V> getObjectsFromField(Field field, Object obj, C clazz) {
        field.setAccessible(true);

        String className = field.getDeclaringClass().getSimpleName();
        String methodName = field.getName();
        String feedback = "Error getting value of the field \""
            + className + "." + methodName + "\" .";

        try {
            Object var = field.get(obj);

            if (var == null) {
                throw new UnexpectedError(feedback
                    + " Expected non-null value, found null");
            }

            Class<?> realClass = var.getClass();
            Class<?> arrayClass = Array.newInstance(clazz, 0).getClass();

            List<V> objects = new ArrayList<>();

            if (realClass == clazz) {
                objects.add((V) var);

            } else if (var instanceof List) {
                List<?> varList = (List<?>) var;

                if (varList.size() == 0) {
                    throw new UnexpectedError(feedback
                        + " Expected non-empty list, found empty");
                }

                for (Object value : varList) {
                    if (value == null) {
                        throw new UnexpectedError(feedback
                            + " Expected list without nulls");
                    }
                    Class<?> valueClass = value.getClass();
                    if (!clazz.isAssignableFrom(valueClass)) {
                        throw new UnexpectedError(feedback
                            + " Expected list of values of " + clazz
                            + ", found value of " + valueClass);
                    }

                    objects.add((V) value);
                }

            } else if (realClass == arrayClass) {
                Object[] varArray = (Object[]) var;

                if (varArray.length == 0) {
                    throw new UnexpectedError(feedback
                        + " Expected non-empty array, found empty");
                }

                for (Object value : varArray) {
                    if (value == null) {
                        throw new UnexpectedError(feedback
                            + " Expected array without nulls");
                    }
                    Class<?> valueClass = value.getClass();
                    if (!clazz.isAssignableFrom(valueClass)) {
                        throw new UnexpectedError(feedback
                            + " Expected array of values of " + clazz
                            + ", found value of " + valueClass);
                    }

                    objects.add((V) value);
                }

            } else {
                throw new UnexpectedError("Cannot cast "
                    + "the field \"" + field.getName() + "\" to a List or array");
            }

            return objects.stream();

        } catch (IllegalAccessException ex) {
            if (!Modifier.isPublic(field.getModifiers())) {
                feedback += " Try to declare field as public";
            }
            throw new UnexpectedError(feedback, ex);
        }
    }
}
