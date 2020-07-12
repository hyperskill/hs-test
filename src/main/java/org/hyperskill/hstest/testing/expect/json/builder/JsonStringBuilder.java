package org.hyperskill.hstest.testing.expect.json.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class JsonStringBuilder extends JsonBaseBuilder {
    public interface StringChecker {
        boolean check(String value);
    }

    StringChecker checker;

    public JsonStringBuilder(StringChecker checker) {
        this.checker = checker;
    }

    @Override
    public boolean check(JsonElement elem) {
        if (!elem.isJsonPrimitive()) {
            return false;
        }

        JsonPrimitive primitive = elem.getAsJsonPrimitive();
        if (!primitive.isString()) {
            return false;
        }

        String value = primitive.getAsString();
        return checker.check(value);
    }
}
