package org.hyperskill.hstest.testing.expect.json.builder;

import org.hyperskill.hstest.testing.expect.json.builder.JsonIntegerBuilder.IntegerChecker;

import java.util.regex.Pattern;

import static org.hyperskill.hstest.testing.expect.json.JsonChecker.any;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isBoolean;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isDouble;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isInteger;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isString;

public class JsonArrayBuilder extends JsonFinishedArrayBuilder {

    int currIndex = 0;

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
        return item(isInteger(value));
    }

    public JsonArrayBuilder item(double value) {
        return item(isDouble(value));
    }

    public JsonArrayBuilder item(boolean value) {
        return item(isBoolean(value));
    }

    public JsonArrayBuilder item(String value) {
        return item(isString(value));
    }

    public JsonArrayBuilder item(Pattern regex) {
        return item(isString(regex));
    }

    public JsonArrayBuilder item(JsonBaseBuilder value) {
        return item(currIndex++, value);
    }

    public JsonArrayBuilder item(int index, JsonBaseBuilder value) {
        return item(i -> i == index, value);
    }

    public JsonArrayBuilder item(IntegerChecker index, JsonBaseBuilder value) {
        arrayIndexCheckers.add(new ArrayIndexChecker(index, value, true));
        return this;
    }

    public JsonArrayBuilder everyItem(JsonBaseBuilder itemTemplate) {
        this.itemTemplate = itemTemplate;
        return this;
    }

    public JsonFinishedArrayBuilder anyOtherItems() {
        arrayIndexCheckers.add(new ArrayIndexChecker(i -> true, any(), false));
        return this;
    }

    public JsonArrayBuilder length(int length) {
        return length(len -> len == length);
    }

    public JsonArrayBuilder length(ArrayLengthChecker lengthChecker) {
        requiredLength = lengthChecker;
        return this;
    }

}
