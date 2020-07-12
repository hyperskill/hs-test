package org.hyperskill.hstest.testing.expect.json.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class JsonNumberBuilder extends JsonBaseBuilder {
    public interface NumberChecker {
        boolean check(Number value);
    }

    NumberChecker checker;

    public JsonNumberBuilder(NumberChecker checker) {
        this.checker = checker;
    }

    @Override
    public boolean check(JsonElement elem) {
        if (!elem.isJsonPrimitive()) {
            return false;
        }

        JsonPrimitive primitive = elem.getAsJsonPrimitive();
        if (!primitive.isNumber()) {
            return false;
        }

        Number value = primitive.getAsNumber();
        return checker.check(value);
    }
}
