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
        String itemDescription;

        ArrayIndexChecker(IntegerChecker indexChecker,
                          JsonBaseBuilder valueChecker,
                          boolean requireMatch,
                          String itemDescription) {
            this.indexChecker = indexChecker;
            this.valueChecker = valueChecker;
            this.requireMatch = requireMatch;
            this.itemDescription = itemDescription;
        }
    }

    public interface ArrayLengthChecker {
        boolean check(int length);
    }

    protected static class ArrayLengthCheckerWithFeedback {
        ArrayLengthChecker checker;
        String feedback;

        ArrayLengthCheckerWithFeedback(ArrayLengthChecker checker, String feedback) {
            this.checker = checker;
            this.feedback = feedback;
        }
    }

    protected int calculatedArrayLength = 0;

    JsonBaseBuilder itemTemplate = null;
    ArrayLengthCheckerWithFeedback requiredLength = null;
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

        if (requiredLength == null && calculatedArrayLength >= 0 && this instanceof JsonArrayBuilder) {
            ((JsonArrayBuilder) this).length(calculatedArrayLength);
        }

        JsonArray array = elem.getAsJsonArray();
        int length = array.size();

        if (requiredLength != null && !requiredLength.checker.check(length)) {
            String result = "has an incorrect length";
            if (requiredLength.feedback != null) {
                result += ": " + requiredLength.feedback + ", found " + length;
            }
            feedback.fail(result);
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
                String result = "is missing an item";
                if (checker.itemDescription != null) {
                    result += ": " + checker.itemDescription;
                }
                feedback.fail(result);
                return false;
            }
        }

        return true;
    }
}
