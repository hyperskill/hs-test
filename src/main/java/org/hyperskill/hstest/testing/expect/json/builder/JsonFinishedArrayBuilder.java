package org.hyperskill.hstest.testing.expect.json.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.hyperskill.hstest.testing.expect.json.builder.JsonIntegerBuilder.IntegerChecker;

import java.util.ArrayList;
import java.util.List;

import static org.hyperskill.hstest.testing.expect.json.JsonChecker.any;

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

    JsonBaseBuilder itemTemplate = any();
    ArrayLengthChecker requiredLength = len -> true;
    List<ArrayIndexChecker> arrayIndexCheckers = new ArrayList<>();

    @Override
    public final boolean check(JsonElement elem) {
        for (ArrayIndexChecker checker : arrayIndexCheckers) {
            checker.matched = false;
        }

        if (!elem.isJsonArray()) {
            return false;
        }

        JsonArray array = elem.getAsJsonArray();

        int length = array.size();
        if (!requiredLength.check(length)) {
            return false;
        }

        entries:
        for (int index = 0; index < array.size(); index++) {
            JsonElement value = array.get(index);

            if (!itemTemplate.check(value)) {
                return false;
            }

            for (ArrayIndexChecker checker : arrayIndexCheckers) {
                if (checker.indexChecker.check(index)) {
                    if (!checker.valueChecker.check(value)) {
                        return false;
                    }
                    checker.matched = true;
                    continue entries;
                }
            }

            return false;
        }

        for (ArrayIndexChecker checker : arrayIndexCheckers) {
            if (checker.requireMatch && !checker.matched) {
                return false;
            }
        }

        return true;
    }
}
