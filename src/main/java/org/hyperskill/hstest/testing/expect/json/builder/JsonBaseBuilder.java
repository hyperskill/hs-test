package org.hyperskill.hstest.testing.expect.json.builder;

import com.google.gson.JsonElement;
import org.hyperskill.hstest.testing.expect.json.ExpectationJsonFeedback;

public abstract class JsonBaseBuilder {
    public abstract boolean check(JsonElement elem, ExpectationJsonFeedback feedback);
}
