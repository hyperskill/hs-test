package org.hyperskill.hstest.testing.expect.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.hyperskill.hstest.common.JsonUtils;
import org.hyperskill.hstest.exception.outcomes.PresentationError;
import org.hyperskill.hstest.testing.expect.Expectation;
import org.hyperskill.hstest.testing.expect.json.builder.JsonBaseBuilder;

import java.util.ArrayList;
import java.util.List;

import static java.util.regex.Pattern.compile;
import static org.hyperskill.hstest.testing.expect.Expectation.expect;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.any;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isArray;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isInteger;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isNumber;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isObject;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isString;

public class ExpectationJsonBuilder<T> {

    Expectation<T> expect;

    JsonElement originalElem;
    JsonElement elem;

    List<String> path = new ArrayList<>();
    String feedback = "";

    public ExpectationJsonBuilder(Expectation<T> expect) {
        this.expect = expect;
        try {
            elem = JsonUtils.getJson(expect.text);
            originalElem = elem;
        } catch (JsonSyntaxException ex) {
            throw new PresentationError("Expected JSON, got something else.\n"
                + ex.getMessage() + "\n\n" + "Content:\n" + expect.text);
        }
    }

    public ExpectationJsonBuilder(Expectation<T> expect, JsonElement elem) {
        this.expect = expect;
        this.elem = elem;
    }

    public ExpectationJsonBuilder<T> withFeedback(String feedback) {
        this.feedback = feedback;
        return this;
    }

    public ExpectationJsonBuilder<T> atPath(String... path) {
        StringBuilder currPath = new StringBuilder("/");
        for (String key : path) {
            currPath.append(key);
            this.path.add(key);

            if (!elem.isJsonArray() && !elem.isJsonObject()) {
                feedback += "\n\nJSON should contain an object or an array at path \""
                    + currPath + "\". Full JSON:\n\n" + JsonUtils.getPrettyJson(originalElem);
                throw new PresentationError(feedback.trim());
            }

            if (elem.isJsonArray()) {
                JsonArray array = elem.getAsJsonArray();

                if (!key.matches("[0-9]+")) {
                    feedback += "\n\nJSON should contain an object at path \""
                        + currPath + "\", found an array with " + array.size()
                        + " elements. Full JSON:\n\n" + JsonUtils.getPrettyJson(originalElem);
                    throw new PresentationError(feedback.trim());
                }

                int index = Integer.parseInt(key);

                if (array.size() <= index) {
                    feedback += "\n\nJSON should contain an array with at least "
                        + (index + 1) + " elements at path \"" + currPath + "\", found "
                        + array.size() + " elements. Full JSON:\n\n" + JsonUtils.getPrettyJson(originalElem);
                    throw new PresentationError(feedback.trim());
                }

                elem = array.get(index);

            } else {
                JsonObject obj = elem.getAsJsonObject();

                if (!obj.has(key)) {
                    feedback += "\n\nJSON should contain an object with key \""+ key + "\"";

                    if (key.matches("[0-9]+")) {
                        int index = Integer.parseInt(key);
                        feedback += " or an array with at least " + (index + 1) + " elements";
                    }

                    feedback += " at path \"" + currPath
                        + "\". Full JSON:\n\n" + JsonUtils.getPrettyJson(originalElem);

                    throw new PresentationError(feedback.trim());
                }

                elem = obj.get(key);
            }

            currPath.append("/");
        }
        return this;
    }

    public void check(JsonBaseBuilder schema) {
        expect("").asJson().atPath("data", "3", "name").check(
            isObject()
                .value("name", isString())
                .value("car", isObject()
                    .value("model", "Tesla Roadster")
                    .value("year", 2018)
                    .value("repairs", isArray(6,
                        isObject()
                            .value("date", compile("\\d{4}-\\d{2}-\\d{2}"))
                            .value("time", compile("\\d{2}-\\d{2}"))
                            .value("cost", isNumber())
                            .value("pic", isString(s -> s.endsWith(".png"))))))
                .value("rocket", isObject()
                    .value("name", "Falcon 9")
                    .value("launches", compile("[0-9]+\\+")))
                .value("options", any())
        );

        expect("").asJson().check(
            isObject()
                .value("workers", isObject()
                    .value(compile("[A-Z][a-z]+ [A-Z][a-z]+"), isObject()
                        .value("name", isString())
                        .value("age", isInteger(i -> i >= 18))
                        .value("works", isString())
                    )
                )
        );

        schema.check(elem, null);
    }



}
