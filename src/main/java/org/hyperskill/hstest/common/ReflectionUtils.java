package org.hyperskill.hstest.common;

import org.hyperskill.hstest.dynamic.ClassSearcher;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.exception.outcomes.ErrorWithFeedback;
import org.hyperskill.hstest.exception.outcomes.OutcomeError;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.stage.StageTest;

import java.io.File;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    public static boolean hasMainMethod(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.getName().equals("main")
                && m.getReturnType() == Void.TYPE
                && m.getParameterTypes().length == 1
                && m.getParameterTypes()[0] == String[].class) {

                boolean isMethodPublic = Modifier.isPublic(m.getModifiers());
                boolean isMethodStatic = Modifier.isStatic(m.getModifiers());

                if (isMethodPublic && isMethodStatic) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Object invokeMethod(Method method, Object obj, Object[] args) {
        method.setAccessible(true);

        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        String location = "\"" + className + "." + methodName + "\"";

        try {
            return method.invoke(obj, args);
        } catch (InvocationTargetException ex) {
            if (ex.getCause() instanceof OutcomeError) {
                throw (OutcomeError) ex.getCause();
            }
            String feedback = "An exception happened while running the method " + location + ".";
            throw new UnexpectedError(feedback, ex.getCause());
        } catch (IllegalAccessException ex) {
            String feedback = "Cannot invoke the method " + location + ".";
            if (!Modifier.isPublic(method.getModifiers())) {
                feedback += " Try to declare method as public.";
            }
            throw new UnexpectedError(feedback, ex);
        }
    }

    public static <V, C extends Class<V>> List<V> getObjectsFromField(Field field, Object obj, C clazz) {
        field.setAccessible(true);

        String className = field.getDeclaringClass().getSimpleName();
        String methodName = field.getName();
        String feedback = "Error getting value of the field \""
            + className + "." + methodName + "\".";

        try {
            Object var = field.get(obj);

            if (var == null) {
                throw new UnexpectedError(feedback
                    + " Expected non-null value, found null");
            }

            Class<?> realClass = var.getClass();
            Class<?> arrayClass = Array.newInstance(clazz, 0).getClass();

            List<V> objects = new ArrayList<>();

            if (clazz.isAssignableFrom(realClass)) {
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
                    + "the field \"" + field.getName() + "\" to List or array or " + clazz);
            }

            return objects;

        } catch (IllegalAccessException ex) {
            if (!Modifier.isPublic(field.getModifiers())) {
                feedback += " Try to declare field as public";
            }
            throw new UnexpectedError(feedback, ex);
        }
    }

    /**
     * Get all classes which package name that starts with "packageName".
     * It doesn't search among library jars, only among compiled class files.
     * I.e. among all test files and among all user files.
     */
    public static List<Class<?>> getAllClassesFromPackage(String packageName) {
        return ClassSearcher.getClassesForPackage(packageName);
    }

    /**
     * Checks if this annotated element can be used as dynamic test.
     * It can be of type Method or Field.
     */
    public static boolean isDynamicTest(AnnotatedElement elem) {
        return elem.isAnnotationPresent(DynamicTest.class)
            || elem.isAnnotationPresent(DynamicTestingMethod.class);
    }

    public static boolean canBeBoxed(Class<?> typeFrom, Class<?> typeTo) {
        return typeFrom == int.class && typeTo == Integer.class
            || typeFrom == long.class && typeTo == Long.class
            || typeFrom == short.class && typeTo == Short.class
            || typeFrom == char.class && typeTo == Character.class
            || typeFrom == byte.class && typeTo == Byte.class
            || typeFrom == boolean.class && typeTo == Boolean.class
            || typeFrom == float.class && typeTo == Float.class
            || typeFrom == double.class && typeTo == Double.class;
    }

    public static List<Field> getAllFields(Object obj) {
        return Stream.of(
            obj.getClass().getDeclaredFields(),
            obj.getClass().getFields())
            .flatMap(Stream::of)
            .distinct()
            .collect(Collectors.toList());
    }

    public static <T extends StageTest<?>> boolean isTests(T stage) throws URISyntaxException {
        var clazz = new File(stage
            .getClass()
            .getProtectionDomain()
            .getCodeSource()
            .getLocation().toURI())
            .getAbsolutePath();

        return clazz.contains(File.separator + "hs-test" + File.separator + "build")
            || clazz.contains(File.separator + "hs-test" + File.separator + "out");
    }

    public static <T extends StageTest<?>> void setupCwd(T stage) {
        String testDir = FileUtils.cwd()
            + File.separator + "src"
            + File.separator + "test"
            + File.separator + "java"
            + File.separator + stage.getClass().getPackageName().replace(".", File.separator);

        File file = new File(testDir);
        if (file.getName().equals("test")) {
            testDir = file.getParent();
        }

        FileUtils.chdir(testDir);
    }
}
