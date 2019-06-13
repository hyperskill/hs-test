package org.hyperskill.hstest.v5.statics.serialization;

import com.cedarsoftware.util.io.JsonReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonDeserialization {

    public static Object deserializeUsingGson(String serialized, Class<?> clazz) throws IOException {
        Gson gson = new Gson();
        try {
            return gson.fromJson(serialized, clazz);
        } catch (ClassCastException ex) { }

        try {
            if (List.class.isAssignableFrom(clazz)) {
                return gson.fromJson(serialized, List.class);
            }
            if (Set.class.isAssignableFrom(clazz)) {
                return gson.fromJson(serialized, Set.class);
            }
            if (Map.class.isAssignableFrom(clazz)) {
                return gson.fromJson(serialized, Map.class);
            }
            if (Collection.class.isAssignableFrom(clazz)) {
                return gson.fromJson(serialized, Collection.class);
            }
        } catch (Exception e) { }

        throw new IOException("Can't deserialize object " + clazz);
    }

    public static Object deserializeUsingJsonIo(String serialized, Class<?> clazz) {
        Object deserialized = JsonReader.jsonToJava(serialized);
        return deserialized;
    }

    public static Object deserializeUsingJackson(String serialized, Class<?> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);

        try {
            // Convert JSON string to Object
            return mapper.readValue(serialized, clazz);
        } catch (Exception e) { }

        try {
            if (List.class.isAssignableFrom(clazz)) {
                return mapper.readValue(serialized, List.class);
            }
            if (Set.class.isAssignableFrom(clazz)) {
                return mapper.readValue(serialized, Set.class);
            }
            if (Map.class.isAssignableFrom(clazz)) {
                return mapper.readValue(serialized, Map.class);
            }
            if (Collection.class.isAssignableFrom(clazz)) {
                return mapper.readValue(serialized, Collection.class);
            }
        } catch (Exception e) { }

        throw new IOException("Can't deserialize object " + clazz);
    }

}
