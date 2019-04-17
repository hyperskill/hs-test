package org.hyperskill.hstest.dev.statics;

import com.cedarsoftware.util.io.JsonReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

class JsonDeserialization {

    static Object deserializeUsingGson(String serialized, Class<?> clazz) {
        Gson gson = new Gson();
        Object deserialized = gson.fromJson(serialized, clazz);
        return deserialized;
    }

    static Object deserializeUsingJsonIo(String serialized, Class<?> clazz) {
        Object deserialized = JsonReader.jsonToJava(serialized);
        return deserialized;
    }

    static Object deserializeUsingJackson(String serialized, Class<?> clazz) throws IOException {
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
        } catch (Exception e) { }

        throw new IOException("Can't deserialize object " + clazz);
    }

}
