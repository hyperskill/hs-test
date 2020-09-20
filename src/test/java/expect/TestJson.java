package expect;

import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;
import static org.hyperskill.hstest.testing.expect.Expectation.expect;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.any;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isArray;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isBoolean;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isDouble;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isInteger;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isNull;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isNumber;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isObject;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isString;

public class TestJson {

    @Test
    public void testJsonIterableCorrectElements() {
        expect("{}").asJson().check(isObject());
        expect("[]").asJson().check(isArray());
    }

    @Test
    public void testJsonNumericCorrectElements() {
        expect("12").asJson().check(isInteger());
        expect("12.23").asJson().check(isDouble());
        expect("12").asJson().check(isNumber());
        expect("12.23").asJson().check(isNumber());
    }

    @Test
    public void testJsonBooleanCorrectElements() {
        expect("true").asJson().check(isBoolean());
        expect("false").asJson().check(isBoolean());
    }

    @Test
    public void testJsonStringCorrectElements() {
        expect("qwe").asJson().check(isString());
        expect("\"qwe\"").asJson().check(isString());
    }

    @Test
    public void testJsonNullCorrectElement() {
        expect("null").asJson().check(isNull());
    }

    @Test
    public void testJsonObjectIncorrectElements() {
        try {
            expect("[]").asJson().check(isObject());
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON array should be object, found array"));
        }

        try {
            expect("12").asJson().check(isObject());
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON element should be object, found number"));
        }
    }

    @Test
    public void testJsonArrayIncorrectElements() {
        try {
            expect("{}").asJson().check(isArray());
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON object should be array, found object"));
        }

        try {
            expect("null").asJson().check(isArray());
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON element should be array, found null"));
        }
    }

    @Test
    public void testJsonNumberIncorrectElements() {
        try {
            expect("wer").asJson().check(isNumber());
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON element should be number, found string"));
        }

        try {
            expect("[]").asJson().check(isNumber());
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON array should be number, found array"));
        }
    }

    @Test
    public void testJsonIntegerIncorrectElements() {
        try {
            expect("null").asJson().check(isInteger());
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON element should be integer, found null"));
        }

        try {
            expect("{}").asJson().check(isInteger());
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON object should be integer, found object"));
        }
    }

    @Test
    public void testJsonDoubleParsedAsInteger() {
        try {
            expect("12.98").asJson().check(isInteger());
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON element should be integer, found double"));
        }
    }

    @Test
    public void testJsonDoubleIncorrectElements() {
        try {
            expect("ert").asJson().check(isDouble());
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON element should be double, found string"));
        }

        try {
            expect("{}").asJson().check(isDouble());
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON object should be double, found object"));
        }
    }

    @Test
    public void testJsonStringIncorrectElements() {
        try {
            expect("true").asJson().check(isString());
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON element should be string, found boolean"));
        }

        try {
            expect("[]").asJson().check(isString());
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON array should be string, found array"));
        }
    }

    @Test
    public void testJsonNullIncorrectElements() {
        try {
            expect("true").asJson().check(isNull());
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON element should be null, found boolean"));
        }

        try {
            expect("[]").asJson().check(isNull());
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON array should be null, found array"));
        }
    }

    @Test
    public void testJsonInnerString() {
        expect("{\"12\":\"23\"}").asJson().check(isObject().value("12", "23"));
        expect("{\"12\":\"23\"}").asJson().check(isObject().value("12", isString("23")));
        expect("{\"12\":\"23\"}").asJson().check(isObject().value("12", isString(s -> s.length() == 2)));
        expect("{\"12\":\"23\"}").asJson().check(isObject().value("12", compile(".3")));
    }

    @Test
    public void testJsonInnerStringRegexKeys() {
        expect("{\"12\":\"23\"}").asJson().check(isObject().value(compile("1."), "23"));
        expect("{\"12\":\"23\"}").asJson().check(isObject().value(compile("1."), isString("23")));
        expect("{\"12\":\"23\"}").asJson().check(isObject().value(compile("1."), isString(s -> s.length() == 2)));
        expect("{\"12\":\"23\"}").asJson().check(isObject().value(compile("1."), compile(".3")));
    }

    @Test
    public void testJsonInnerInteger() {
        expect("{\"12\":23}").asJson().check(isObject().value("12", 23));
        expect("{\"12\":23}").asJson().check(isObject().value("12", isNumber(23)));
        expect("{\"12\":23}").asJson().check(isObject().value("12", isInteger(23)));
        expect("{\"12\":23}").asJson().check(isObject().value("12", isInteger(i -> i > 22)));
    }

    @Test
    public void testJsonInnerIntegerRegexKeys() {
        expect("{\"12\":23}").asJson().check(isObject().value(compile("1."), 23));
        expect("{\"12\":23}").asJson().check(isObject().value(compile("1."), isNumber(23)));
        expect("{\"12\":23}").asJson().check(isObject().value(compile("1."), isInteger(23)));
        expect("{\"12\":23}").asJson().check(isObject().value(compile("1."), isInteger(i -> i > 22)));
    }

    @Test
    public void testJsonInnerDouble() {
        expect("{\"12\":2.3}").asJson().check(isObject().value("12", 2.3));
        expect("{\"12\":2.3}").asJson().check(isObject().value("12", isNumber(2.3)));
        expect("{\"12\":2.3}").asJson().check(isObject().value("12", isDouble(2.3)));
        expect("{\"12\":2.3}").asJson().check(isObject().value("12", isDouble(i -> i > 2.2)));
    }

    @Test
    public void testJsonInnerDoubleRegexKeys() {
        expect("{\"12\":2.3}").asJson().check(isObject().value(compile("1."), 2.3));
        expect("{\"12\":2.3}").asJson().check(isObject().value(compile("1."), isNumber(2.3)));
        expect("{\"12\":2.3}").asJson().check(isObject().value(compile("1."), isDouble(2.3)));
        expect("{\"12\":2.3}").asJson().check(isObject().value(compile("1."), isDouble(i -> i > 2.2)));
    }

    @Test
    public void testJsonInnerBoolean() {
        expect("{\"12\":false}").asJson().check(isObject().value("12", false));
        expect("{\"12\":false}").asJson().check(isObject().value("12", isBoolean(false)));
        expect("{\"12\":false}").asJson().check(isObject().value("12", isBoolean(b -> !b)));
    }

    @Test
    public void testJsonInnerBooleanRegexKeys() {
        expect("{\"12\":false}").asJson().check(isObject().value(compile("1."), false));
        expect("{\"12\":false}").asJson().check(isObject().value(compile("1."), isBoolean(false)));
        expect("{\"12\":false}").asJson().check(isObject().value(compile("1."), isBoolean(b -> !b)));
    }

    @Test
    public void testJsonInnerNull() {
        expect("{\"12\":null}").asJson().check(isObject().value("12", isNull()));
        expect("{\"12\":null}").asJson().check(isObject().value(compile("1."), isNull()));
    }

    @Test
    public void testJsonInnerIterable() {
        expect("{\"12\": {}}").asJson().check(isObject().value("12", isObject()));
        expect("{\"12\": []}").asJson().check(isObject().value("12", isArray()));
        expect("{\"12\": {}}").asJson().check(isObject().value(compile("1."), isObject()));
        expect("{\"12\": []}").asJson().check(isObject().value(compile("1."), isArray()));
    }

    @Test
    public void testJsonArrayFixedSize() {
        expect("[1,2,3,4]").asJson().check(isArray(1, 2, 3, 4));
        expect("[1,2,3,4]").asJson().check(isArray(1.0, 2.0, 3.0, 4.0));
        expect("[1.0,2.0,3.0,4.0]").asJson().check(isArray(1.0, 2.0, 3.0, 4.0));
        expect("[true,false,true]").asJson().check(isArray(true, false, true));
        expect("[\"1\",\"2\",\"3\"]").asJson().check(isArray("1", "2", "3"));
    }

    @Test
    public void testJsonArraySimpleTemplate() {
        expect("[1,2,3,4]").asJson().check(isArray(isInteger()));
        expect("[1,2,3,4]").asJson().check(isArray(isNumber()));
        expect("[1,2,3,4]").asJson().check(isArray(isDouble()));

        expect("[1.1,2.2,3.3,4.4]").asJson().check(isArray(isNumber()));
        expect("[1.1,2.2,3.3,4.4]").asJson().check(isArray(isDouble()));
    }

    @Test
    public void testJsonArrayAnyTemplateAndLength() {
        expect("[1, 2.2, true, \"6\", null, [], {}]").asJson().check(isArray(any()));
        expect("[1, 2.2, true, \"6\", null, [], {}]").asJson().check(isArray(7));
        expect("[1, 2.2, true, \"6\", null, [], {}]").asJson().check(isArray(7, any()));
    }

    @Test
    public void testJsonArrayEveryElement() {
        expect("[1, 2.2, true, \"6\", null, [], {}]").asJson().check(
            isArray()
                .item(1)
                .item(2.2)
                .item(true)
                .item("6")
                .item(isNull())
                .item(isArray())
                .item(isObject())
        );

        expect("[1, 2.2, true, \"6\", null, [], {}]").asJson().check(
            isArray(7)
                .item(1)
                .item(2.2)
                .item(true)
                .item("6")
                .item(isNull())
                .item(isArray())
                .item(isObject())
        );
    }

    @Test
    public void testJsonArrayAnyOtherElements() {
        expect("[1, 2.2, true, \"6\", null, [], {}]").asJson().check(
            isArray()
                .item(1)
                .item(2.2)
                .item(true)
                .item("6")
                .anyOtherItems()
        );
    }

    @Test
    public void testJsonArrayEveryItemTemplate() {
        expect("[1, 2.2, true, \"6\", null, [], {}]").asJson().check(
            isArray()
                .everyItem(any())
        );

        expect("[1, 2, 3, 4]").asJson().check(
            isArray()
                .everyItem(isInteger(i -> i < 5 && i > 0))
        );
    }

    @Test
    public void testJsonArrayLength() {
        expect("[1, 2, 3, 4]").asJson().check(
            isArray()
                .length(4)
        );

        expect("[1, 2, 3, 4]").asJson().check(
            isArray()
                .length(4)
                .everyItem(isInteger(i -> i < 5 && i > 0))
        );
    }

    @Test
    public void testJsonArrayEmptyIncorrect() {
        try {
            expect("[1, 2, 3, 4]").asJson().check(
                isArray()
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON array has an incorrect length: should be equal to 0, found 4"));
        }
    }

    @Test
    public void testJsonArrayAnyElements() {
        expect("[]").asJson().check(isArray(any()));
        expect("[1, 5]").asJson().check(isArray(any()));
        expect("[{}]").asJson().check(isArray(any()));
        expect("[1, 2, 3, 4, \"qwe\", [true, 1, null]]").asJson().check(isArray(any()));
    }

    @Test
    public void testJsonObjectAnyElements() {
        expect("{}").asJson().check(isObject(any()));
        expect("{\"1\": 2}").asJson().check(isObject(any()));
        expect("{2: {4: 5}, 3: [],6:null}").asJson().check(isObject(any()));
    }

    @Test
    public void testJsonArrayFirstIncorrect() {
        try {
            expect("[1, 2, 3, 4]").asJson().check(
                isArray()
                    .item(0)
                    .item(1)
                    .item(2)
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON array has an incorrect length: should be equal to 3, found 4"));
        }
    }

    @Test
    public void testJsonArrayNotEnoughElementsIncorrect() {
        try {
            expect("[1, 2, 3, 4]").asJson().check(
                isArray()
                    .item(1)
                    .item(2)
                    .item(3)
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON array has an incorrect length: should be equal to 3, found 4"));
        }
    }

    @Test
    public void testJsonArrayIncorrectType() {
        try {
            expect("[1, 2, true, 4]").asJson().check(
                isArray(isInteger(i -> i <= 2))
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON array at index 2 should be integer, found boolean"));
        }
    }

    @Test
    public void testJsonArrayIncorrectValue() {
        try {
            expect("[1, 2, 3, 4]").asJson().check(
                    isArray()
                        .item(1)
                        .item(2)
                        .item(4)
                        .item(5)
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                    "The JSON array at index 2 should equal to 4, found 3"));
        }
    }

    @Test
    public void testJsonArrayLengthUsingLambda() {
        try {
            expect("[1, 2, 3, 4]").asJson().check(
                    isArray()
                        .length(len -> len <= 3)
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                    "The JSON array has an incorrect length"));
        }
    }

    @Test
    public void testJsonArrayLengthUsingLambdaWithFeedback() {
        try {
            expect("[1, 2, 3, 4]").asJson().check(
                    isArray()
                        .length(len -> len < 4, "should be less than 4")
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                    "The JSON array has an incorrect length: should be less than 4, found 4"));
        }
    }

    @Test
    public void testJsonArrayWrongItem() {
        try {
            expect("[1, 2, 3, 4]").asJson().check(
                    isArray()
                        .length(len -> len == 4)
                        .item(2, isInteger(5))
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                    "The JSON array at index 2 should equal to 5, found 3"));
        }
    }

    @Test
    public void testJsonArrayMissingItem() {
        try {
            expect("[1, 2, 3, 4]").asJson().check(
                    isArray()
                            .length(len -> len > 2)
                            .item(6, isInteger(7))
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                    "The JSON array is missing an item: an item at index 6"));
        }
    }

    @Test
    public void testJsonArrayMissingIntegerAtIndex() {
        try {
            expect("[1, 2, 3, 4]").asJson().check(
                    isArray()
                        .length(len -> len > 2)
                        .item(1)
                        .item(2)
                        .item(3)
                        .item(4)
                        .item(5)
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                    "The JSON array is missing an item: an integer value 5 at index 4"));
        }
    }

    @Test
    public void testJsonArrayWrongArrayLength() {
        try {
            expect("[1, 2, 3, 4]").asJson().check(
                    isArray(len -> len < 4)
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                    "The JSON array has an incorrect length"));
        }
    }

    @Test
    public void testJsonArrayWrongTemplate() {
        try {
            expect("[1, 2, 3, 4]").asJson().check(
                    isArray(len -> len >= 4, isBoolean())
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                    "The JSON array at index 0 should be boolean, found number"));
        }
    }

    @Test
    public void testJsonArrayWrongTemplate2() {
        try {
            expect("[1, 2, 3, 4]").asJson().check(
                    isArray(isBoolean())
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                    "The JSON array at index 0 should be boolean, found number"));
        }
    }


    @Test
    public void testJsonArrayMissingDoubleAtIndex() {
        try {
            expect("[1, 2, 3, 4]").asJson().check(
                    isArray()
                            .length(len -> len > 2)
                            .item(1)
                            .item(2)
                            .item(3)
                            .item(4)
                            .item(5.5)
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                    "The JSON array is missing an item: a double value 5.5 at index 4"));
        }
    }

    @Test
    public void testJsonArrayMissingBooleanAtIndex() {
        try {
            expect("[1, 2, 3, 4]").asJson().check(
                    isArray()
                            .length(len -> len > 2)
                            .item(1)
                            .item(2)
                            .item(3)
                            .item(4)
                            .item(false)
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                    "The JSON array is missing an item: a boolean value false at index 4"));
        }
    }

    @Test
    public void testJsonArrayMissingStringAtIndex() {
        try {
            expect("[1, 2, 3, 4]").asJson().check(
                    isArray()
                            .length(len -> len > 2)
                            .item(1)
                            .item(2)
                            .item(3)
                            .item(4)
                            .item("123123")
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                    "The JSON array is missing an item: a string \"123123\" at index 4"));
        }
    }

    @Test
    public void testJsonArrayMissingPatternAtIndex() {
        try {
            expect("[1, 2, 3, 4]").asJson().check(
                    isArray()
                            .length(len -> len > 2)
                            .item(1)
                            .item(2)
                            .item(3)
                            .item(4)
                            .item(Pattern.compile("\\d+"))
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                    "The JSON array is missing an item: a string with pattern \"\\d+\" at index 4"));
        }
    }

    @Test
    public void testJsonArrayIncorrectTemplate() {
        try {
            expect("[1, 2, true, 4]").asJson().check(
                isArray(isInteger(1))
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON array at index 1 should equal to 1, found 2"));
        }
    }

    @Test
    public void testJsonObjectExcessKey() {
        try {
            expect("{\"1\" : 1 , \"2\" : 2}").asJson().check(
                isObject().value("1", 1)
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON object shouldn't have the key \"2\""));
        }
    }

    @Test
    public void testJsonObjectMissingKey() {
        try {
            expect("{\"1\" : 1 , \"2\" : 2}").asJson().check(
                isObject()
                    .value("1", 1)
                    .value("2", 2)
                    .value("3", 3)
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON object should contain a key \"3\""));
        }
    }

    @Test
    public void testJsonObjectMissingKeyPattern() {
        try {
            expect("{\"1\" : 1 , \"2\" : 2}").asJson().check(
                    isObject()
                            .value("1", 1)
                            .value("2", 2)
                            .value(Pattern.compile("[4-9]"), 3)
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                    "The JSON object should contain a key with pattern \"[4-9]\""));
        }
    }

    @Test
    public void testJsonObjectMissingKeyInner() {
        try {
            expect("{\"1\" : 1 , \"2\" : {\"3\" : {\"4\" : 5}}}").asJson().check(
                isObject()
                    .value("1", 1)
                    .value("2", isObject()
                        .value("3", isObject()))
            );
        } catch (WrongAnswer ex) {
            Assert.assertTrue(ex.getFeedbackText().startsWith(
                "The JSON element at path \"/2/3\" shouldn't have the key \"4\""));
        }
    }

    @Test
    public void testJsonComplexExample() {
        String text = "{\n" +
                "    \"name\": \"123\",\n" +
                "    \"car\": {\n" +
                "        \"model\": \"Tesla Roadster\",\n" +
                "        \"year\": 2018,\n" +
                "        \"repairs\": [\n" +
                "            {\n" +
                "                \"date\": \"2019-10-11\",\n" +
                "                \"time\": \"20:11\",\n" +
                "                \"cost\": 12.32,\n" +
                "                \"pic\": \"qwe.png\",\n" +
                "                \"all_years\": [2011, 2013, 2014, 2020]\n" +
                "            },\n" +
                "            {\n" +
                "                \"date\": \"2019-11-12\",\n" +
                "                \"time\": \"21:12\",\n" +
                "                \"cost\": 34,\n" +
                "                \"img\": \"wer.png\",\n" +
                "                \"all_years\": [2011, 2013, 2014, 2020]\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"rocket\": {\n" +
                "        \"name\": \"Falcon 9\",\n" +
                "        \"launches\": \"89+\",\n" +
                "        \"mentioned\": [\n" +
                "            12, 34, 56\n" +
                "        ],\n" +
                "        \"memorable\": [\n" +
                "            {\n" +
                "                \"num\": 12,\n" +
                "                \"why\": \"flew high\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"number\": 23,\n" +
                "                \"reason\": \"flew fast\"\n" +
                "            },\n" +
                "            45,\n" +
                "            null,\n" +
                "            false\n" +
                "        ]\n" +
                "    },\n" +
                "    \"options\": {\n" +
                "        \"12\": null\n" +
                "    }\n" +
                "}";

            expect(text).asJson().check(
                isObject()
                    .value("name", isString())
                    .value("car", isObject()
                        .value("model", "Tesla Roadster")
                        .value("year", 2018)
                        .value("repairs", isArray(2,
                            isObject()
                                .value("date", compile("\\d{4}-\\d{2}-\\d{2}"))
                                .value("time", compile("\\d{2}:\\d{2}"))
                                .value("cost", isNumber())
                                .value(compile("pic|img"), isString(s -> s.endsWith(".png")))
                                .value(key -> key.endsWith("years"), 2011, 2013, 2014, 2020))))
                    .value("rocket", isObject()
                        .value("name", "Falcon 9")
                        .value("launches", compile("[0-9]+\\+"))
                        .value("mentioned", isArray(12, 34, 56))
                        .value("memorable", isArray()
                            .item(isObject()
                                .value("num", isInteger(i -> i == 12 || i == 13))
                                .value(compile("..y"), "flew high"))
                            .item(isObject()
                                .value("number", 23)
                                .value("reason", "flew fast"))
                            .item(45)
                            .item(isNull())
                            .anyOtherItems()))
                    .value("options", any())
            );
    }

}
