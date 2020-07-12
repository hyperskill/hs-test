package org.hyperskill.hstest.testing.expect.json;

import org.hyperskill.hstest.testing.expect.json.builder.JsonAnyBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonArrayBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonBaseBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonBooleanBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonBooleanBuilder.BooleanChecker;
import org.hyperskill.hstest.testing.expect.json.builder.JsonDoubleBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonDoubleBuilder.DoubleChecker;
import org.hyperskill.hstest.testing.expect.json.builder.JsonFinishedArrayBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonIntegerBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonIntegerBuilder.IntegerChecker;
import org.hyperskill.hstest.testing.expect.json.builder.JsonNullBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonNumberBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonNumberBuilder.NumberChecker;
import org.hyperskill.hstest.testing.expect.json.builder.JsonObjectBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonStringBuilder;
import org.hyperskill.hstest.testing.expect.json.builder.JsonStringBuilder.StringChecker;

import java.util.regex.Pattern;

public class JsonChecker {
    private JsonChecker() { }

    public static JsonAnyBuilder any() {
        return new JsonAnyBuilder();
    }

    public static JsonObjectBuilder isObject() {
        return new JsonObjectBuilder();
    }

    public static JsonArrayBuilder isArray(int length) {
        return isArray().length(length);
    }

    public static JsonArrayBuilder isArray(JsonBaseBuilder itemsTemplate) {
        return isArray().everyItem(itemsTemplate);
    }

    public static JsonArrayBuilder isArray(int length, JsonBaseBuilder itemsTemplate) {
        return isArray().length(length).everyItem(itemsTemplate);
    }

    public static JsonFinishedArrayBuilder isArray(int... values) {
        return isArray().items(values);
    }

    public static JsonFinishedArrayBuilder isArray(double... values) {
        return isArray().items(values);
    }

    public static JsonFinishedArrayBuilder isArray(boolean... values) {
        return isArray().items(values);
    }

    public static JsonFinishedArrayBuilder isArray(String... values) {
        return isArray().items(values);
    }

    public static JsonArrayBuilder isArray() {
        return new JsonArrayBuilder();
    }

    public static JsonStringBuilder isString() {
        return isString(v -> true);
    }

    public static JsonStringBuilder isString(String value) {
        return isString(v -> v.equals(value));
    }

    public static JsonStringBuilder isString(Pattern regex) {
        return isString(v -> regex.matcher(v).matches());
    }

    public static JsonStringBuilder isString(StringChecker checker) {
        return new JsonStringBuilder(checker);
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
        return new JsonNumberBuilder(checker);
    }

    public static JsonIntegerBuilder isInteger() {
        return isInteger(v -> true);
    }

    public static JsonIntegerBuilder isInteger(int value) {
        return isInteger(v -> v == value);
    }

    public static JsonIntegerBuilder isInteger(IntegerChecker checker) {
        return new JsonIntegerBuilder(checker);
    }

    public static JsonDoubleBuilder isDouble() {
        return isDouble(v -> true);
    }

    public static JsonDoubleBuilder isDouble(double value) {
        return isDouble(v -> v - value < 1e-6);
    }

    public static JsonDoubleBuilder isDouble(DoubleChecker checker) {
        return new JsonDoubleBuilder(checker);
    }

    public static JsonBooleanBuilder isBoolean() {
        return isBoolean(v -> true);
    }

    public static JsonBooleanBuilder isBoolean(boolean value) {
        return isBoolean(v -> v == value);
    }

    public static JsonBooleanBuilder isBoolean(BooleanChecker checker) {
        return new JsonBooleanBuilder(checker);
    }

    public static JsonNullBuilder isNull() {
        return new JsonNullBuilder();
    }

}
