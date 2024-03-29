package org.hyperskill.hstest.testing.expect.json.builder;

import org.hyperskill.hstest.testing.expect.base.checker.KeyValueChecker;
import org.hyperskill.hstest.testing.expect.base.checker.StringChecker;

import java.util.regex.Pattern;

import static org.hyperskill.hstest.testing.expect.json.JsonChecker.any;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isArray;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isBoolean;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isDouble;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isNumber;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isString;

public class JsonObjectBuilder extends JsonFinishedObjectBuilder {

    public JsonObjectBuilder value(String key, int value) {
        return value(key, isNumber(value));
    }

    public JsonObjectBuilder value(String key, double value) {
        return value(key, isDouble(value));
    }

    public JsonObjectBuilder value(String key, boolean value) {
        return value(key, isBoolean(value));
    }

    public JsonObjectBuilder value(String key, String value) {
        return value(key, isString(value));
    }

    public JsonObjectBuilder value(String key, Pattern valueRegex) {
        return value(key, isString(valueRegex));
    }

    public JsonObjectBuilder value(String key, int... values) {
        return value(key, isArray(values));
    }

    public JsonObjectBuilder value(String key, double... value) {
        return value(key, isArray(value));
    }

    public JsonObjectBuilder value(String key, boolean... value) {
        return value(key, isArray(value));
    }

    public JsonObjectBuilder value(String key, String... value) {
        return value(key, isArray(value));
    }

    public JsonObjectBuilder value(Pattern keyRegex, int value) {
        return value(keyRegex, isNumber(value));
    }

    public JsonObjectBuilder value(Pattern keyRegex, double value) {
        return value(keyRegex, isDouble(value));
    }

    public JsonObjectBuilder value(Pattern keyRegex, boolean value) {
        return value(keyRegex, isBoolean(value));
    }

    public JsonObjectBuilder value(Pattern keyRegex, String value) {
        return value(keyRegex, isString(value));
    }

    public JsonObjectBuilder value(Pattern keyRegex, Pattern valueRegex) {
        return value(keyRegex, isString(valueRegex));
    }

    public JsonObjectBuilder value(Pattern keyRegex, int... values) {
        return value(keyRegex, isArray(values));
    }

    public JsonObjectBuilder value(Pattern keyRegex, double... value) {
        return value(keyRegex, isArray(value));
    }

    public JsonObjectBuilder value(Pattern keyRegex, boolean... value) {
        return value(keyRegex, isArray(value));
    }

    public JsonObjectBuilder value(Pattern keyRegex, String... value) {
        return value(keyRegex, isArray(value));
    }

    public JsonObjectBuilder value(StringChecker key, int value) {
        return value(key, isNumber(value));
    }

    public JsonObjectBuilder value(StringChecker key, double value) {
        return value(key, isDouble(value));
    }

    public JsonObjectBuilder value(StringChecker key, boolean value) {
        return value(key, isBoolean(value));
    }

    public JsonObjectBuilder value(StringChecker key, String value) {
        return value(key, isString(value));
    }

    public JsonObjectBuilder value(StringChecker key, Pattern valueRegex) {
        return value(key, isString(valueRegex));
    }

    public JsonObjectBuilder value(StringChecker key, int... values) {
        return value(key, isArray(values));
    }

    public JsonObjectBuilder value(StringChecker key, double... values) {
        return value(key, isArray(values));
    }

    public JsonObjectBuilder value(StringChecker key, boolean... values) {
        return value(key, isArray(values));
    }

    public JsonObjectBuilder value(StringChecker key, String... values) {
        return value(key, isArray(values));
    }

    public JsonObjectBuilder value(String key, JsonBaseBuilder value) {
        return value(k -> k.equals(key), value, "should contain a key \"" + key + "\"");
    }

    public JsonObjectBuilder value(Pattern keyRegex, JsonBaseBuilder value) {
        return value(k -> keyRegex.matcher(k).matches(), value,
                "should contain a key with pattern \"" + keyRegex + "\"");
    }

    public JsonObjectBuilder value(StringChecker key, JsonBaseBuilder value) {
        return value(key, value, "is missing a key");
    }

    public JsonObjectBuilder value(StringChecker key, JsonBaseBuilder value, String failFeedback) {
        keyValueCheckers.add(new KeyValueChecker<>(key, value, true, failFeedback));
        return this;
    }

    public JsonFinishedObjectBuilder anyOtherValues() {
        keyValueCheckers.add(new KeyValueChecker<>(key -> true, any(), false, ""));
        return this;
    }

}
