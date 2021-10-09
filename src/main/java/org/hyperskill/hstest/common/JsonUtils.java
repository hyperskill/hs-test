package org.hyperskill.hstest.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import org.hyperskill.hstest.exception.outcomes.PresentationError;

public final class JsonUtils {

    private JsonUtils() { }

    public static JsonElement getJson(String content) {
        try {
            return new JsonParser().parse(content);
        } catch (JsonSyntaxException ex) {
            throw new PresentationError("Expected JSON, got something else.\n"
                + ex.getMessage() + "\n\n" + "Content:\n" + content);
        }
    }

    public static String getPrettyJson(JsonElement json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return Utils.cleanText(gson.toJson(json));
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
