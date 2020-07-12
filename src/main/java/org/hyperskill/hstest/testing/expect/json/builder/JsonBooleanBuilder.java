package org.hyperskill.hstest.testing.expect.json.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.hyperskill.hstest.common.JsonUtils;
import org.hyperskill.hstest.testing.expect.json.ExpectationJsonFeedback;

public class JsonBooleanBuilder extends JsonBaseBuilder {
    public interface BooleanChecker {
        boolean check(boolean value);
    }

    final BooleanChecker checker;
    final String failFeedback;

    public JsonBooleanBuilder(BooleanChecker checker, String failFeedback) {
        this.checker = checker;
        this.failFeedback = failFeedback;
    }

    @Override
    public boolean check(JsonElement elem, ExpectationJsonFeedback feedback) {
        if (!elem.isJsonPrimitive()) {
            feedback.fail("should be of boolean type, found " + JsonUtils.getType(elem));
            return false;
        }

        JsonPrimitive primitive = elem.getAsJsonPrimitive();
        if (!primitive.isBoolean()) {
            feedback.fail("should be of boolean type, found " + JsonUtils.getType(elem));
            return false;
        }

        boolean value = primitive.getAsBoolean();
        boolean result = checker.check(value);

        if (!result) {
            feedback.fail(failFeedback);
            return false;
        }

        return true;
    }
}
