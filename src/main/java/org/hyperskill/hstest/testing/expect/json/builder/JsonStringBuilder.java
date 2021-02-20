package org.hyperskill.hstest.testing.expect.json.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.hyperskill.hstest.common.JsonUtils;
import org.hyperskill.hstest.testing.expect.base.checker.StringChecker;
import org.hyperskill.hstest.testing.expect.json.ExpectationJsonFeedback;

public class JsonStringBuilder extends JsonBaseBuilder {
    final StringChecker checker;
    final String failFeedback;

    public JsonStringBuilder(StringChecker checker, String failFeedback) {
        this.checker = checker;
        this.failFeedback = failFeedback;
    }

    @Override
    public boolean check(JsonElement elem, ExpectationJsonFeedback feedback) {
        if (!elem.isJsonPrimitive()) {
            feedback.fail("should be string, found " + JsonUtils.getType(elem));
            return false;
        }

        JsonPrimitive primitive = elem.getAsJsonPrimitive();
        if (!primitive.isString()) {
            feedback.fail("should be string, found " + JsonUtils.getType(elem));
            return false;
        }

        String value = primitive.getAsString();
        boolean result = checker.check(value);

        if (!result) {
            feedback.fail(failFeedback + ", found \"" + value + "\"");
            return false;
        }

        return true;
    }
}
