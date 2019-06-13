package org.hyperskill.hstest.v5.statics;


import org.hyperskill.hstest.v5.statics.serialization.JsonDeserialization;
import org.hyperskill.hstest.v5.statics.serialization.JsonSerialization;
import org.hyperskill.hstest.v5.statics.serialization.Serialized;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ObjectsCloner {

    public static List<Serialized> cantSerialize = new ArrayList<>();
    public static List<Serialized> cantDeserialize = new ArrayList<>();
    public static List<Serialized> circularLinks = new ArrayList<>();

    private static Serialized serializeObject(Object object) {
        Serialized serialized = new Serialized();

        serialized.objectToSerialize = object;
        serialized.sourceClass = object.getClass();

        try {
            serialized.gson = JsonSerialization.serializeUsingGson(object);
        } catch (StackOverflowError ex) {
            serialized.isCircular = true;
            return serialized;
        } catch (Exception ex) {
            serialized.gsonSerialized = ex;
        }

        try {
            serialized.jsonio = JsonSerialization.serializeUsingJsonIo(object);
        } catch (Exception ex) {
            serialized.jsonioSerialized = ex;
        }

        try {
            serialized.jackson = JsonSerialization.serializeUsingJackson(object);
        } catch (Exception ex) {
            serialized.jacksonSerialized = ex;
        }

        return serialized;
    }

    private static Object deserializeObject(Serialized serialized, Class<?> clazz) {
        try {
            return JsonDeserialization.deserializeUsingJsonIo(serialized.jsonio, clazz);
        } catch (Exception ex) {
            serialized.jsonioDeserialized = ex;
        }

        try {
            return JsonDeserialization.deserializeUsingJackson(serialized.jackson, clazz);
        } catch (Exception ex) {
            serialized.jacksonDeserialized = ex;
        }

        return null;
    }

    public static Object cloneObject(Object obj) {
        // Since user can't use multiple Scanner's in program
        // it's really not necessary to clone Scanner
        // also when testing swing don't need to clone Component objects
        if (obj instanceof Scanner || obj instanceof Component) {
            return obj;
        }

        if (obj == null) {
            return null;
        }

        Serialized serialized = serializeObject(obj);

        // Serialization breaks circular links between objects so we shouldn't serialize them
        if (serialized.isCircular) {
            circularLinks.add(serialized);
            return obj;
        }

        if (serialized.cantSerialize()) {
            cantSerialize.add(serialized);
            return obj;
        }

        Object cloned = deserializeObject(serialized, obj.getClass());
        if (cloned == null) {
            cantDeserialize.add(serialized);
            return obj;
        }

        return cloned;
    }
}
