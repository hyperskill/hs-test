package org.hyperskill.hstest.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class JsonUtils {

    private JsonUtils() { }

    public static JsonElement getJson(String content) {
        return new JsonParser().parse(content);
    }

    public static String getPrettyJson(JsonElement json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }

    public static String getType(JsonElement json) {
        if (json.isJsonObject()) {
            return "object";
        } else if (json.isJsonArray()) {
            return "array";
        } else if (json.isJsonNull()) {
            return "null";
        } else if (json.isJsonPrimitive()) {
            JsonPrimitive primitive = json.getAsJsonPrimitive();

            if (primitive.isNumber()) {
                return "number";
            } else if (primitive.isBoolean()) {
                return "boolean";
            } else if (primitive.isString()) {
                return "string";
            }
        }

        return "unknown type";
    }

}
