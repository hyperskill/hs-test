package org.hyperskill.hstest.dynamic.extractors;

import org.hyperskill.hstest.exception.outcomes.UnexpectedError;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

abstract public class AnnotatedExtractor<T> {

    private interface DataSource {
        Object getData() throws IllegalAccessException, InvocationTargetException;
    }

    protected final String provider;
    private final String name;
    private final Object obj;

    public AnnotatedExtractor(String name, Object obj) {
        provider = "data provider \"" + name + "\" in class " + obj.getClass().getSimpleName();
        this.name = name;
        this.obj = obj;
    }

    abstract protected T convert(Object rawData);

    final public T extract() {
        DataSource dataSource = determineDataSource();
        Object rawData = extractRawData(dataSource);
        return convert(rawData);
    }

    private DataSource determineDataSource() {
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

    private Object extractRawData(DataSource source) {
        try {
            return source.getData();
        } catch (IllegalAccessException e) {
            throw new UnexpectedError("Can't access " + provider
                + ". Is it declared as public?");
        } catch (InvocationTargetException e) {
            throw new UnexpectedError("Exception " + e.getCause().getClass()
                + " running " + provider + ".", e.getCause());
        }
    }

}
