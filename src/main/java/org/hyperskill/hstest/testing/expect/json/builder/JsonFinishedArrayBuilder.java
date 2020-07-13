package org.hyperskill.hstest.testing.expect.json.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.hyperskill.hstest.common.JsonUtils;
import org.hyperskill.hstest.testing.expect.json.ExpectationJsonFeedback;
import org.hyperskill.hstest.testing.expect.json.builder.JsonIntegerBuilder.IntegerChecker;

import java.util.ArrayList;
import java.util.List;

public class JsonFinishedArrayBuilder extends JsonBaseBuilder {

    protected static class ArrayIndexChecker {
        IntegerChecker indexChecker;
        JsonBaseBuilder valueChecker;
        boolean requireMatch;
        boolean matched = false;

        ArrayIndexChecker(IntegerChecker indexChecker, JsonBaseBuilder valueChecker, boolean requireMatch) {
            this.indexChecker = indexChecker;
            this.valueChecker = valueChecker;
            this.requireMatch = requireMatch;
        }
    }

    protected interface ArrayLengthChecker {
        boolean check(int length);
    }

    JsonBaseBuilder itemTemplate = null;
    ArrayLengthChecker requiredLength = null;
    final List<ArrayIndexChecker> arrayIndexCheckers = new ArrayList<>();

    @Override
    public final boolean check(JsonElement elem, ExpectationJsonFeedback feedback) {
        for (ArrayIndexChecker checker : arrayIndexCheckers) {
            checker.matched = false;
        }

        if (!elem.isJsonArray()) {
            feedback.fail("should be array, found " + JsonUtils.getType(elem));
            return false;
        }

        JsonArray array = elem.getAsJsonArray();

        int length = array.size();
        if (requiredLength != null && !requiredLength.check(length)) {
            feedback.fail("has an incorrect length");
            return false;
        }

        entries:
        for (int index = 0; index < length; index++) {
            JsonElement value = array.get(index);

            if (itemTemplate != null) {
                feedback.addPath("" + index);
                boolean result = itemTemplate.check(value, feedback);
                feedback.removePath();

                if (!result) {
                    return false;
                }
            }

            for (ArrayIndexChecker checker : arrayIndexCheckers) {
                if (checker.indexChecker.check(index)) {
                    feedback.addPath("" + index);
                    boolean result = checker.valueChecker.check(value, feedback);
                    feedback.removePath();

                    if (!result) {
                        return false;
                    }

                    checker.matched = true;
                    continue entries;
                }
            }

            if (requiredLength != null || itemTemplate != null) {
                continue;
            }

            feedback.fail("shouldn't have the element with index " + index);
            return false;
        }

        for (ArrayIndexChecker checker : arrayIndexCheckers) {
            if (checker.requireMatch && !checker.matched) {
                feedback.fail("should have some items that are missing");
                return false;
            }
        }

        return true;
    }
}
