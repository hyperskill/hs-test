package org.hyperskill.hstest.dev.statics;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StaticFieldsCleaner {

    private static Map<String, Class> nameToClass = new LinkedHashMap<>();
    private static Map<String, Map<Field, Object>> savedFields = new LinkedHashMap<>();

    public static String getTopPackage(Class userMainClass) {
        String className = userMainClass.getCanonicalName();
        return className.substring(0, className.indexOf("."));
    }

    private static Map<Field, Object> saveFieldsForClass(Class clazz) throws Exception {
        Map<Field, Object> savedFields = new HashMap<>();
        Field[] allFields = clazz.getDeclaredFields();
        try {
            for (Field field : allFields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    if (Modifier.isPrivate(field.getModifiers())) {
                        field.setAccessible(true);
                    }
                    Object value = ObjectsCloner.cloneObject(field.get(null));
                    savedFields.put(field, value);
                }
            }
        } catch (IllegalAccessException ex) {
            System.err.println(ex);
            throw ex;
        }
        return savedFields;
    }

    public static void saveStaticFields(String packageName) throws Exception {
        List<Class<?>> userClasses = ClassSearcher.getClassesForPackage(packageName);
        for (Class clazz : userClasses) {
            String className = clazz.getCanonicalName();
            nameToClass.put(className, clazz);
            savedFields.put(className, saveFieldsForClass(clazz));
        }
    }

    public static void resetStaticFields() throws Exception {
        for (Map.Entry<String, Class> classEntry : nameToClass.entrySet()) {
            String className = classEntry.getKey();
            for (Map.Entry<Field, Object> fieldEntry : savedFields.get(className).entrySet()) {
                Field field = fieldEntry.getKey();
                Object value = fieldEntry.getValue();
                field.set(null, ObjectsCloner.cloneObject(value));
            }
        }
    }
}
