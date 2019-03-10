package org.hyperskill.hstest.dev.statics;

import com.google.gson.Gson;

public class ObjectsCloner {
    private static String serializeObject(Object object) {
        Gson gson = new Gson();
        String serialized = gson.toJson(object);
        return serialized;
    }

    private static Object deserializeObject(String serialized, Class<?> clazz) {
        Gson gson = new Gson();
        Object deserialized = gson.fromJson(serialized, clazz);
        return deserialized;
    }

    public static Object cloneObject(Object obj) {
        if (obj == null) {
            return null;
        }
        String serialized = serializeObject(obj);
        Object cloned = deserializeObject(serialized, obj.getClass());
        return cloned;
    }
}
