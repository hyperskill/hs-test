package org.hyperskill.hstest.testing.expect.json.builder;

import com.google.gson.JsonElement;

public class JsonAnyBuilder extends JsonBaseBuilder {
    @Override
    public boolean check(JsonElement elem) {
        return true;
    }
}
