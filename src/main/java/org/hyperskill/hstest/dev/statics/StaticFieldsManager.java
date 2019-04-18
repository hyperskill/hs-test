package org.hyperskill.hstest.dev.statics;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class StaticFieldsManager {

    private static Map<Class, Map<Field, Object>> savedFields = new LinkedHashMap<>();

    public static String getTopPackage(Class userMainClass) {
        String className = userMainClass.getCanonicalName();
        return className.substring(0, className.indexOf("."));
    }

    private static void disableWarning() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe u = (Unsafe) theUnsafe.get(null);

            Class cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field logger = cls.getDeclaredField("logger");
            u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
        } catch (Exception e) {
            // ignore
        }
    }

    private static Map<Field, Object> saveFieldsForClass(Class clazz) throws Exception {
        Map<Field, Object> savedFields = new HashMap<>();
        Field[] allFields = clazz.getDeclaredFields();
        try {
            for (Field field : allFields) {
                boolean isStatic = Modifier.isStatic(field.getModifiers());
                if (isStatic) {
                    field.setAccessible(true);
                    Field modifiersField = Field.class.getDeclaredField("modifiers");
                    modifiersField.setAccessible(true);
                    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                    Object value = ObjectsCloner.cloneObject(field.get(null));
                    savedFields.put(field, value);
                }
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return savedFields;
    }

    public static void saveStaticFields(String packageName) throws Exception {
        disableWarning();
        List<Class<?>> userClasses = ClassSearcher.getClassesForPackage(packageName);
        for (Class clazz : userClasses) {
            savedFields.put(clazz, saveFieldsForClass(clazz));
        }
    }

    public static void resetStaticFields() throws Exception {
        for (Map.Entry<Class, Map<Field, Object>> classEntry : savedFields.entrySet()) {
            for (Map.Entry<Field, Object> fieldEntry : classEntry.getValue().entrySet()) {
                Field field = fieldEntry.getKey();
                Object value = fieldEntry.getValue();
                field.set(null, ObjectsCloner.cloneObject(value));
            }
        }
    }
}
