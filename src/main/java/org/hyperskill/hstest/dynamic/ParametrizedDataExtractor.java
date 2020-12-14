package org.hyperskill.hstest.dynamic;

import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.stage.StageTest;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParametrizedDataExtractor {
    private ParametrizedDataExtractor () { }

    private interface DataSource {
        Object getData() throws IllegalAccessException, InvocationTargetException;
    }

    private static <T extends StageTest<?>> DataSource determineDataSource(String name, T obj) {
        Class<?> getDataFrom = obj.getClass();
        String className = getDataFrom.getSimpleName();

        boolean methodFound = true;
        Method method = null;
        try {
            method = getDataFrom.getDeclaredMethod(name);
            method.setAccessible(true);
            if (method.getParameterCount() != 0) {
                throw new UnexpectedError("Data provider method " + name + " in class "
                    + className + " should not take any parameters.");
            }
        } catch (NoSuchMethodException ignored) {
            methodFound = false;
        }

        boolean fieldFound = true;
        Field field = null;
        try {
            field = getDataFrom.getDeclaredField(name);
            field.setAccessible(true);
        } catch (NoSuchFieldException ignored) {
            fieldFound = false;
        }

        if (methodFound && fieldFound) {
            throw new UnexpectedError("Found both field and method named \"" + name
                + "\" as a data provider in class " + className + ". Leave one of them.");
        }

        if (!methodFound && !fieldFound) {
            throw new UnexpectedError("Class " + className + " doesn't contain"
                + " field or method named \"" + name + "\" as a data provider.");
        }

        if (methodFound) {
            Method finalMethod = method;
            return () -> finalMethod.invoke(obj);
        } else {
            Field finalField = field;
            return () -> finalField.get(obj);
        }
    }

    private static Object extractRawData(DataSource source, String providerName) {
        try {
            return source.getData();
        } catch (IllegalAccessException e) {
            throw new UnexpectedError("Can't access " + providerName
                + ". Is it declared as public?");
        } catch (InvocationTargetException e) {
            throw new UnexpectedError("Exception " + e.getCause().getClass()
                + " running " + providerName + ".", e.getCause());
        }
    }

    private static Object[] getAsObjectArray(Object rawData) {
        if (rawData instanceof Object[]) {
            return (Object[]) rawData;

        } else if (rawData instanceof int[]) {
            return Arrays.stream((int[]) rawData).boxed().toArray();

        } else if (rawData instanceof long[]) {
            return Arrays.stream((long[]) rawData).boxed().toArray();

        } else if (rawData instanceof short[]) {
            throw new UnexpectedError(
                "short[] data provider isn't supported, use java.lang.Short[]");

        } else if (rawData instanceof char[]) {
            throw new UnexpectedError(
                "char[] data provider isn't supported, use java.lang.Character[]");

        } else if (rawData instanceof byte[]) {
            throw new UnexpectedError(
                "byte[] data provider isn't supported, use java.lang.Byte[]");

        } else if (rawData instanceof boolean[]) {
            throw new UnexpectedError(
                "boolean[] data provider isn't supported, use java.lang.Boolean[]");

        } else if (rawData instanceof float[]) {
            throw new UnexpectedError(
                "float[] data provider isn't supported, use java.lang.Float[]");

        } else if (rawData instanceof double[]) {
            return Arrays.stream((double[]) rawData).boxed().toArray();

        } else {
            return null;
        }
    }

    private static List<Object[]> analyzeData(Object rawData, String providerName) {
        Object[] objects = getAsObjectArray(rawData);

        if (objects == null) {
            throw new UnexpectedError(providerName
                + " should be/should return array, found " + rawData.getClass().getName());
        }

        if (objects.length == 0) {
            throw new UnexpectedError(providerName
                + " should be/should return an array with length > 0, found empty array.");
        }

        boolean foundArraysInside = false;
        boolean foundObjectsInside = false;
        int argsLength = 0;

        for (Object obj : objects) {
            if (obj instanceof Object[]) {
                foundArraysInside = true;
                int currLength = ((Object[]) obj).length;

                if (argsLength != 0 && argsLength != currLength) {
                    throw new UnexpectedError(providerName + " should be/should return an array "
                        + "with equal length of its inner arrays. Found sizes: " + argsLength + ", " + currLength);
                }

                if (currLength == 0) {
                    throw new UnexpectedError(providerName + " should be/should return an array "
                        + "with length > 0 of its inner arrays. Found array with length = 0");
                }

                argsLength = currLength;
            } else {
                foundObjectsInside = true;
                argsLength = 1;
            }

            if (foundArraysInside && foundObjectsInside) {
                throw new UnexpectedError("Found arrays and non-array objects inside "
                    + providerName + ". Leave one of them.");
            }
        }

        List<Object[]> argsList = new ArrayList<>();

        for (Object obj : objects) {
            if (foundArraysInside) {
                argsList.add((Object[]) obj);
            } else {
                argsList.add(new Object[]{ obj });
            }
        }

        return argsList;
    }

    public static <T extends StageTest<?>> List<Object[]> extractParametrizedData(String name, T obj) {
        DataSource dataSource = determineDataSource(name, obj);
        String provider = "data provider \"" + name + "\" in class " + obj.getClass().getSimpleName();
        Object rawData = extractRawData(dataSource, provider);
        return analyzeData(rawData, provider);
    }
}
