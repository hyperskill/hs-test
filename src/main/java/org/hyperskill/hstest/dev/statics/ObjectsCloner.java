package org.hyperskill.hstest.dev.statics;


import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ObjectsCloner {

    public static List<Class<?>> cantSerialize = new ArrayList<>();
    public static List<Class<?>> cantDeserialize = new ArrayList<>();

    private static String serializeObject(Object object) {
        try {
            return JsonSerialization.serializeUsingJackson(object);
        } catch (IOException ex) {
            return null;
        }
    }

    private static Object deserializeObject(String serialized, Class<?> clazz) {
        try {
            return JsonDeserialization.deserializeUsingJackson(serialized, clazz);
        } catch (IOException ex) {
            return null;
        }

    }

    public static Object cloneObject(Object obj) {
        // Jackson gives StackOverFlow exception serializing Scanner
        // but since user can't use multiple Scanner's in program
        // it's really not necessary to clone Scanner
        // also when testing swing don't need to clone Component objects
        if (obj instanceof Scanner || obj instanceof Component) {
            return obj;
        }

        if (obj == null) {
            return null;
        }

        String serialized = serializeObject(obj);
        if (serialized == null) {
            cantSerialize.add(obj.getClass());
            return obj;
        }

        Object cloned = deserializeObject(serialized, obj.getClass());
        if (cloned == null) {
            cantDeserialize.add(obj.getClass());
            return obj;
        }

        return cloned;
    }
}
