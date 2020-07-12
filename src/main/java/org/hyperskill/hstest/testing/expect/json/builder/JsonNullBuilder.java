package org.hyperskill.hstest.testing.expect.json.builder;

import com.google.gson.JsonElement;
import org.hyperskill.hstest.common.JsonUtils;
import org.hyperskill.hstest.testing.expect.json.ExpectationJsonFeedback;

public class JsonNullBuilder extends JsonBaseBuilder {
    @Override
    public boolean check(JsonElement elem, ExpectationJsonFeedback feedback) {
        boolean result = elem.isJsonNull();

        if (!result) {
            feedback.fail("should be null, found " + JsonUtils.getType(elem));
            return false;
        }

        return true;
    }
}
