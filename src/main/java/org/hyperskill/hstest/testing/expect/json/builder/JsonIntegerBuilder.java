package org.hyperskill.hstest.testing.expect.json.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class JsonIntegerBuilder extends JsonBaseBuilder {

    public interface IntegerChecker {
        boolean check(int value);
    }

    IntegerChecker checker;

    public JsonIntegerBuilder(IntegerChecker checker) {
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

        int value = primitive.getAsInt();

        if (!primitive.getAsNumber().toString().equals("" + value)) {
            return false;
        }

        return checker.check(value);
    }
}
