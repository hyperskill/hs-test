package org.hyperskill.hstest.dynamic.extractors;

import org.hyperskill.hstest.exception.outcomes.UnexpectedError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnnotatedDataExtractor extends AnnotatedExtractor<List<Object[]>> {

    public AnnotatedDataExtractor(String name, Object obj) {
        super(name, obj);
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

    @Override
    protected List<Object[]> convert(Object rawData) {
        if (rawData == null) {
            throw new UnexpectedError(provider + " should not return null, found null");
        }

        Object[] objects = getAsObjectArray(rawData);

        if (objects == null) {
            throw new UnexpectedError(provider
                + " should be/should return array, found " + rawData.getClass().getName());
        }

        if (objects.length == 0) {
            throw new UnexpectedError(provider
                + " should be/should return an array with length > 0, found empty array.");
        }

        boolean foundArraysInside = false;
        boolean foundObjectsInside = false;
        int argsLength = 0;

        for (Object obj : objects) {
            if (obj == null) {
                throw new UnexpectedError(provider
                    + " should not be/should not return an array that contains null inside.");
            }

            if (obj instanceof Object[]) {
                foundArraysInside = true;
                int currLength = ((Object[]) obj).length;

                if (argsLength != 0 && argsLength != currLength) {
                    throw new UnexpectedError(provider + " should be/should return an array "
                        + "with equal length of its inner arrays. Found sizes: " + argsLength + ", " + currLength);
                }

                if (currLength == 0) {
                    throw new UnexpectedError(provider + " should be/should return an array "
                        + "with length > 0 of its inner arrays. Found array with length = 0");
                }

                argsLength = currLength;
            } else {
                foundObjectsInside = true;
                argsLength = 1;
            }

            if (foundArraysInside && foundObjectsInside) {
                throw new UnexpectedError("Found arrays and non-array objects inside "
                    + provider + ". Leave one of them.");
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
}
