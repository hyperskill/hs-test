package org.hyperskill.hstest.v7.statics.serialization;

import com.cedarsoftware.util.io.JsonWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;

import java.io.IOException;


public class JsonSerialization {

    public static String serializeUsingGson(Object object) {
        Gson gson = new Gson();
        String serialized = gson.toJson(object, object.getClass());
        return serialized;
    }

    public static String serializeUsingJsonIo(Object object) {
        String serialized = JsonWriter.objectToJson(object);
        return serialized;
    }

    public static String serializeUsingJackson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        try {
            //Convert object to JSON string
            String serialized = mapper.writeValueAsString(object);
            return serialized;
        } catch (Exception e) { }

        throw new IOException("Can't serialize object " + object.getClass());
    }

}
