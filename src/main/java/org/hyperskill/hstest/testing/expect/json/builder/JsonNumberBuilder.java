package org.hyperskill.hstest.testing.expect.json.builder;

import com.google.gson.JsonElement;
import org.hyperskill.hstest.common.JsonUtils;
import org.hyperskill.hstest.testing.expect.json.ExpectationJsonFeedback;

public class JsonNumberBuilder extends JsonBaseBuilder {
    public interface NumberChecker {
        boolean check(Number value);
    }

    NumberChecker checker;
    String failFeedback;

    public JsonNumberBuilder(NumberChecker checker, String failFeedback) {
        this.checker = checker;
        this.failFeedback = failFeedback;
    }

    @Override
    public boolean check(JsonElement elem, ExpectationJsonFeedback feedback) {
        if (!elem.isJsonPrimitive() || !elem.getAsJsonPrimitive().isNumber()) {
            feedback.fail("should be of number type, found " + JsonUtils.getType(elem));
            return false;
        }

        Number value = elem.getAsJsonPrimitive().getAsNumber();
        boolean result = checker.check(value);

        if (!result) {
            feedback.fail(failFeedback);
            return false;
        }

        return true;
    }
}
