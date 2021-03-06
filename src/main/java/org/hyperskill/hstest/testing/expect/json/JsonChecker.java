package org.hyperskill.hstest.testing.expect.json;

import org.hyperskill.hstest.testing.expect.base.checker.BooleanChecker;
import org.hyperskill.hstest.testing.expect.base.checker.DoubleChecker;
import org.hyperskill.hstest.testing.expect.base.checker.IntegerChecker;
import org.hyperskill.hstest.testing.expect.base.checker.NumberChecker;
import org.hyperskill.hstest.testing.expect.base.checker.StringChecker;
import org.hyperskill.hstest.testing.expect.json.builder.JsonAnyBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonArrayBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonBaseBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonBooleanBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonDoubleBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonFinishedArrayBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonFinishedObjectBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonIntegerBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonNullBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonNumberBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonObjectBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonStringBuilder;

import java.util.regex.Pattern;

public final class JsonChecker {
    private JsonChecker() { }

    public static JsonAnyBuilder any() {
        return new JsonAnyBuilder();
    }

    public static JsonObjectBuilder isObject() {
        return new JsonObjectBuilder();
    }

    public static JsonFinishedObjectBuilder isObject(JsonAnyBuilder unused) {
        return isObject().anyOtherValues();
    }

    public static JsonArrayBuilder isArray() {
        return new JsonArrayBuilder();
    }

    public static JsonArrayBuilder isArray(int length) {
        return isArray().length(length);
    }

    public static JsonArrayBuilder isArray(IntegerChecker lengthChecker) {
        return isArray().length(lengthChecker);
    }

    public static JsonArrayBuilder isArray(JsonBaseBuilder itemsTemplate) {
        return isArray().everyItem(itemsTemplate);
    }

    public static JsonArrayBuilder isArray(int length, JsonBaseBuilder itemsTemplate) {
        return isArray().length(length).everyItem(itemsTemplate);
    }

    public static JsonArrayBuilder isArray(IntegerChecker lengthChecker, JsonBaseBuilder itemsTemplate) {
        return isArray().length(lengthChecker).everyItem(itemsTemplate);
    }

    public static JsonFinishedArrayBuilder isArray(int... values) {
        return isArray().length(values.length).items(values);
    }

    public static JsonFinishedArrayBuilder isArray(double... values) {
        return isArray().length(values.length).items(values);
    }

    public static JsonFinishedArrayBuilder isArray(boolean... values) {
        return isArray().length(values.length).items(values);
    }

    public static JsonFinishedArrayBuilder isArray(String... values) {
        return isArray().length(values.length).items(values);
    }

    public static JsonStringBuilder isString() {
        return isString(v -> true);
    }

    public static JsonStringBuilder isString(String value) {
        return isString(v -> v.equals(value), "should equal to \"" + value + "\"");
    }

    public static JsonStringBuilder isString(Pattern regex) {
        return isString(regex, "should match pattern \"" + regex + "\"");
    }

    public static JsonStringBuilder isString(Pattern regex, String failFeedback) {
        return isString(v -> regex.matcher(v).matches(), failFeedback);
    }

    public static JsonStringBuilder isString(StringChecker checker) {
        return isString(checker, "is incorrect");
    }

    public static JsonStringBuilder isString(StringChecker checker, String failFeedback) {
        return new JsonStringBuilder(checker, failFeedback);
    }

    public static JsonNumberBuilder isNumber() {
        return isNumber(v -> true);
    }

    public static JsonIntegerBuilder isNumber(int value) {
        return isInteger(value);
    }

    public static JsonDoubleBuilder isNumber(double value) {
        return isDouble(value);
    }

    public static JsonNumberBuilder isNumber(NumberChecker checker) {
        return isNumber(checker, "is incorrect");
    }

    public static JsonNumberBuilder isNumber(NumberChecker checker, String failFeedback) {
        return new JsonNumberBuilder(checker, failFeedback);
    }

    public static JsonIntegerBuilder isInteger() {
        return isInteger(v -> true);
    }

    public static JsonIntegerBuilder isInteger(int value) {
        return isInteger(v -> v == value, "should equal to " + value);
    }

    public static JsonIntegerBuilder isInteger(IntegerChecker checker) {
        return isInteger(checker, "is incorrect");
    }

    public static JsonIntegerBuilder isInteger(IntegerChecker checker, String failFeedback) {
        return new JsonIntegerBuilder(checker, failFeedback);
    }

    public static JsonDoubleBuilder isDouble() {
        return isDouble(v -> true);
    }

    public static JsonDoubleBuilder isDouble(double value) {
        return isDouble(v -> v - value < 1e-6, "should equal to " + value);
    }

    public static JsonDoubleBuilder isDouble(DoubleChecker checker) {
        return isDouble(checker, "is incorrect");
    }

    public static JsonDoubleBuilder isDouble(DoubleChecker checker, String failFeedback) {
        return new JsonDoubleBuilder(checker, failFeedback);
    }

    public static JsonBooleanBuilder isBoolean() {
        return isBoolean(v -> true);
    }

    public static JsonBooleanBuilder isBoolean(boolean value) {
        return isBoolean(v -> v == value, "should equal to " + value);
    }

    public static JsonBooleanBuilder isBoolean(BooleanChecker checker) {
        return isBoolean(checker, "is incorrect");
    }

    public static JsonBooleanBuilder isBoolean(BooleanChecker checker, String failFeedback) {
        return new JsonBooleanBuilder(checker, failFeedback);
    }

    public static JsonNullBuilder isNull() {
        return new JsonNullBuilder();
    }

}
