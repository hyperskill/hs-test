package org.hyperskill.hstest.testing.expect.json.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class JsonBooleanBuilder extends JsonBaseBuilder {
    public interface BooleanChecker {
        boolean check(boolean value);
    }

    BooleanChecker checker;

    public JsonBooleanBuilder(BooleanChecker checker) {
        this.checker = checker;
    }

    @Override
    public boolean check(JsonElement elem) {
        if (!elem.isJsonPrimitive()) {
            return false;
        }

        JsonPrimitive primitive = elem.getAsJsonPrimitive();
        if (!primitive.isBoolean()) {
            return false;
        }

        boolean value = primitive.getAsBoolean();
        return checker.check(value);
    }
}
