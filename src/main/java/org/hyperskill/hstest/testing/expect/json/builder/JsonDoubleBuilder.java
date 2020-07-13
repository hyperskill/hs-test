package org.hyperskill.hstest.testing.expect.json.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.hyperskill.hstest.common.JsonUtils;
import org.hyperskill.hstest.testing.expect.json.ExpectationJsonFeedback;

public class JsonDoubleBuilder extends JsonBaseBuilder {
    public interface DoubleChecker {
        boolean check(double value);
    }

    final DoubleChecker checker;
    final String failFeedback;

    public JsonDoubleBuilder(DoubleChecker checker, String failFeedback) {
        this.checker = checker;
        this.failFeedback = failFeedback;
    }

    @Override
    public boolean check(JsonElement elem, ExpectationJsonFeedback feedback) {
        if (!elem.isJsonPrimitive()) {
            feedback.fail("should be double, found " + JsonUtils.getType(elem));
            return false;
        }

        JsonPrimitive primitive = elem.getAsJsonPrimitive();
        if (!primitive.isNumber()) {
            feedback.fail("should be double, found " + JsonUtils.getType(elem));
            return false;
        }

        double value = primitive.getAsDouble();
        boolean result = checker.check(value);

        if (!result) {
            feedback.fail(failFeedback);
        }

        return true;
    }
}
