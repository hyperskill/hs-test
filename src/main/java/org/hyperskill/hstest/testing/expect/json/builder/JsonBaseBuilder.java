package org.hyperskill.hstest.testing.expect.json.builder;

import com.google.gson.JsonElement;

public abstract class JsonBaseBuilder {
    public abstract boolean check(JsonElement elem);
}
