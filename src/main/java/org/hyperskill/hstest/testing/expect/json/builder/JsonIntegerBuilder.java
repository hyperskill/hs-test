package org.hyperskill.hstest.testing.expect.json.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.hyperskill.hstest.common.JsonUtils;
import org.hyperskill.hstest.testing.expect.json.ExpectationJsonFeedback;

public class JsonIntegerBuilder extends JsonBaseBuilder {

    public interface IntegerChecker {
        boolean check(int value);
    }

    final IntegerChecker checker;
    final String failFeedback;

    public JsonIntegerBuilder(IntegerChecker checker, String failFeedback) {
        this.checker = checker;
        this.failFeedback = failFeedback;
    }

    @Override
    public boolean check(JsonElement elem, ExpectationJsonFeedback feedback) {
        if (!elem.isJsonPrimitive()) {
            feedback.fail("should be integer, found " + JsonUtils.getType(elem));
            return false;
        }

        JsonPrimitive primitive = elem.getAsJsonPrimitive();
        if (!primitive.isNumber()) {
            feedback.fail("should be integer, found " + JsonUtils.getType(elem));
            return false;
        }

        int value = primitive.getAsInt();

        if (!primitive.getAsNumber().toString().equals("" + value)) {
            feedback.fail("should be integer, found double");
            return false;
        }

        boolean result = checker.check(value);

        if (!result) {
            feedback.fail(failFeedback + ", found " + value);
            return false;
        }

        return true;
    }
}
