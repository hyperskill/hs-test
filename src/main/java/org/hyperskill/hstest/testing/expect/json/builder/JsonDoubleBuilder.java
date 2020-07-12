package org.hyperskill.hstest.testing.expect.json.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class JsonDoubleBuilder extends JsonBaseBuilder {
    public interface DoubleChecker {
        boolean check(double value);
    }

    DoubleChecker checker;

    public JsonDoubleBuilder(DoubleChecker checker) {
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

        double value = primitive.getAsDouble();
        return checker.check(value);
    }
}
