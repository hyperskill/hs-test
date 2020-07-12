package org.hyperskill.hstest.testing.expect.json.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.hyperskill.hstest.testing.expect.json.builder.JsonStringBuilder.StringChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonFinishedObjectBuilder extends JsonBaseBuilder {

    protected static class KeyValueChecker {
        StringChecker keyChecker;
        JsonBaseBuilder valueChecker;
        boolean requireMatch;
        boolean matched = false;

        KeyValueChecker(StringChecker keyChecker, JsonBaseBuilder valueChecker, boolean requireMatch) {
            this.keyChecker = keyChecker;
            this.valueChecker = valueChecker;
            this.requireMatch = requireMatch;
        }
    }

    List<KeyValueChecker> keyValueCheckers = new ArrayList<>();

    @Override
    public final boolean check(JsonElement elem) {
        for (KeyValueChecker checker : keyValueCheckers) {
            checker.matched = false;
        }

        if (!elem.isJsonObject()) {
            return false;
        }

        JsonObject obj = elem.getAsJsonObject();

        entries:
        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            for (KeyValueChecker checker : keyValueCheckers) {
                if (checker.keyChecker.check(key)) {
                    if (!checker.valueChecker.check(value)) {
                        return false;
                    }
                    checker.matched = true;
                    continue entries;
                }
            }

            return false;
        }

        for (KeyValueChecker checker : keyValueCheckers) {
            if (checker.requireMatch && !checker.matched) {
                return false;
            }
        }

        return true;
    }

}
