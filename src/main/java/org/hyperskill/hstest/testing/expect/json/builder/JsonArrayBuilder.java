package org.hyperskill.hstest.testing.expect.json.builder;

import org.hyperskill.hstest.testing.expect.base.checker.ArrayIndexChecker;
import org.hyperskill.hstest.testing.expect.base.checker.ArrayLengthChecker;
import org.hyperskill.hstest.testing.expect.base.checker.IntegerChecker;

import java.util.regex.Pattern;

import static org.hyperskill.hstest.testing.expect.json.JsonChecker.any;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isBoolean;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isDouble;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isInteger;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isString;

public class JsonArrayBuilder extends JsonFinishedArrayBuilder {

    int currIndex = 0;
    private boolean arrayLengthChanged = false;

    public JsonArrayBuilder() {

    }

    public JsonArrayBuilder items(int... values) {
        for (int value : values) {
            item(value);
        }
        return this;
    }

    public JsonArrayBuilder items(double... values) {
        for (double value : values) {
            item(value);
        }
        return this;
    }

    public JsonArrayBuilder items(boolean... values) {
        for (boolean value : values) {
            item(value);
        }
        return this;
    }

    public JsonArrayBuilder items(String... values) {
        for (String value : values) {
            item(value);
        }
        return this;
    }

    public JsonArrayBuilder item(int value) {
        return item(isInteger(value), "an integer value " + value);
    }

    public JsonArrayBuilder item(double value) {
        return item(isDouble(value), "a double value " + value);
    }

    public JsonArrayBuilder item(boolean value) {
        return item(isBoolean(value), "a boolean value " + value);
    }

    public JsonArrayBuilder item(String value) {
        return item(isString(value), "a string \"" + value + "\"");
    }

    public JsonArrayBuilder item(Pattern regex) {
        return item(isString(regex), "a string with pattern \"" + regex + "\"");
    }

    public JsonArrayBuilder item(JsonBaseBuilder value) {
        return item(value, "an item");
    }

    public JsonArrayBuilder item(JsonBaseBuilder value, String itemDescription) {
        return item(currIndex++, value, itemDescription);
    }

    public JsonArrayBuilder item(int index, JsonBaseBuilder value) {
        return item(index, value, "an item");
    }

    public JsonArrayBuilder item(int index, JsonBaseBuilder value, String itemDescription) {
        if (calculatedArrayLength != -1) {
            calculatedArrayLength = index + 1;
            arrayLengthChanged = true;
        }
        return item(i -> i == index, value, itemDescription + " at index " + index);
    }

    public JsonArrayBuilder item(IntegerChecker index, JsonBaseBuilder value) {
        return item(index, value, null);
    }

    public JsonArrayBuilder item(IntegerChecker index, JsonBaseBuilder value, String itemDescription) {
        if (!arrayLengthChanged) {
            calculatedArrayLength = -1;
        } else {
            arrayLengthChanged = false;
        }
        arrayIndexCheckers.add(new ArrayIndexChecker(index, value, true, itemDescription));
        return this;
    }

    public JsonArrayBuilder everyItem(JsonBaseBuilder itemTemplate) {
        calculatedArrayLength = -1;
        this.itemTemplate = itemTemplate;
        return this;
    }

    public JsonFinishedArrayBuilder anyOtherItems() {
        calculatedArrayLength = -1;
        arrayIndexCheckers.add(new ArrayIndexChecker(i -> true, any(), false, null));
        return this;
    }

    public JsonArrayBuilder length(int length) {
        return length(length, "should be equal to " + length);
    }

    public JsonArrayBuilder length(int length, String feedback) {
        return length(len -> len == length, feedback);
    }

    public JsonArrayBuilder length(IntegerChecker lengthChecker) {
        return length(lengthChecker, null);
    }

    public JsonArrayBuilder length(IntegerChecker lengthChecker, String feedback) {
        calculatedArrayLength = -1;
        requiredLength = new ArrayLengthChecker(lengthChecker, feedback);
        return this;
    }

}
