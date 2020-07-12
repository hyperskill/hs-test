package org.hyperskill.hstest.testing.expect.json.builder;

import com.google.gson.JsonElement;
import org.hyperskill.hstest.testing.expect.json.ExpectationJsonFeedback;

public class JsonAnyBuilder extends JsonBaseBuilder {
    @Override
    public boolean check(JsonElement elem, ExpectationJsonFeedback feedback) {
        return true;
    }
}
