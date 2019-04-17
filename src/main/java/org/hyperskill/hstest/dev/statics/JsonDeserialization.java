package org.hyperskill.hstest.dev.statics;

import com.cedarsoftware.util.io.JsonReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

class JsonDeserialization {

    static Object deserializeUsingGson(String serialized, Class<?> clazz) {
        Gson gson = new Gson();
        Object deserialized = gson.fromJson(serialized, clazz);
        return deserialized;
    }

    static Object deserializeUsingJsonIo(String serialized, Class<?> clazz) {
        System.out.println(serialized);
        Object deserialized = JsonReader.jsonToJava(serialized);
        return deserialized;
    }

    static Object deserializeUsingJackson(String serialized, Class<?> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);

        try {
            // Convert JSON string to Object
            Object object = mapper.readValue(serialized, clazz);
            return object;
        } catch (Exception e) { }

        try {
            if (List.class.isAssignableFrom(clazz)) {
                Object object = mapper.readValue(serialized, List.class);
                return object;
            }
        } catch (Exception e) { }

        throw new IOException("Can't deserialize object " + clazz);
    }

}
