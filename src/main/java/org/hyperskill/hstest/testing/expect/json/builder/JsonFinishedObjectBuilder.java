package org.hyperskill.hstest.testing.expect.json.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.hyperskill.hstest.common.JsonUtils;
import org.hyperskill.hstest.testing.expect.base.checker.StringChecker;
import org.hyperskill.hstest.testing.expect.json.ExpectationJsonFeedback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonFinishedObjectBuilder extends JsonBaseBuilder {

    protected static class KeyValueChecker {
        StringChecker keyChecker;
        String failFeedback;
        JsonBaseBuilder valueChecker;
        boolean requireMatch;
        boolean matched = false;

        KeyValueChecker(StringChecker keyChecker, JsonBaseBuilder valueChecker,
                        boolean requireMatch, String failFeedback) {
            this.keyChecker = keyChecker;
            this.failFeedback = failFeedback;
            this.valueChecker = valueChecker;
            this.requireMatch = requireMatch;
        }
    }

    final List<KeyValueChecker> keyValueCheckers = new ArrayList<>();

    @Override
    public final boolean check(JsonElement elem, ExpectationJsonFeedback feedback) {
        for (KeyValueChecker checker : keyValueCheckers) {
            checker.matched = false;
        }

        if (!elem.isJsonObject()) {
            feedback.fail("should be object, found " + JsonUtils.getType(elem));
            return false;
        }

        JsonObject obj = elem.getAsJsonObject();

        entries:
        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            for (KeyValueChecker checker : keyValueCheckers) {
                if (checker.keyChecker.check(key)) {
                    feedback.addPath(key);
                    boolean result = checker.valueChecker.check(value, feedback);
                    feedback.removePath();

                    if (!result) {
                        return false;
                    }

                    checker.matched = true;
                    continue entries;
                }
            }

            feedback.fail("shouldn't have the key \"" + key + "\"");
            return false;
        }

        for (KeyValueChecker checker : keyValueCheckers) {
            if (checker.requireMatch && !checker.matched) {
                feedback.fail(checker.failFeedback);
                return false;
            }
        }

        return true;
    }

}
