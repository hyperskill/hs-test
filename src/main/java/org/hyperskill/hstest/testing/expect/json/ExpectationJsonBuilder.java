package org.hyperskill.hstest.testing.expect.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.hyperskill.hstest.common.JsonUtils;
import org.hyperskill.hstest.exception.outcomes.PresentationError;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.testing.expect.Expectation;
import org.hyperskill.hstest.testing.expect.json.builder.JsonBaseBuilder;

import java.util.ArrayList;
import java.util.List;

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
                    + currPath + "\".\n\nFull JSON:\n" + JsonUtils.getPrettyJson(originalElem);
                throw new PresentationError(feedback.trim());
            }

            if (elem.isJsonArray()) {
                JsonArray array = elem.getAsJsonArray();

                if (!key.matches("[0-9]+")) {
                    feedback += "\n\nJSON should contain an object at path \""
                        + currPath + "\", found an array with " + array.size()
                        + " elements.\n\nFull JSON:\n" + JsonUtils.getPrettyJson(originalElem);
                    throw new PresentationError(feedback.trim());
                }

                int index = Integer.parseInt(key);

                if (array.size() <= index) {
                    feedback += "\n\nJSON should contain an array with at least "
                        + (index + 1) + " elements at path \"" + currPath + "\", found "
                        + array.size() + " elements.\n\nFull JSON:\n" + JsonUtils.getPrettyJson(originalElem);
                    throw new PresentationError(feedback.trim());
                }

                elem = array.get(index);

            } else {
                JsonObject obj = elem.getAsJsonObject();

                if (!obj.has(key)) {
                    feedback += "\n\nJSON should contain an object with key \"" + key + "\"";

                    if (key.matches("[0-9]+")) {
                        int index = Integer.parseInt(key);
                        feedback += " or an array with at least " + (index + 1) + " elements";
                    }

                    feedback += " at path \"" + currPath
                        + "\".\n\nFull JSON:\n" + JsonUtils.getPrettyJson(originalElem);

                    throw new PresentationError(feedback.trim());
                }

                elem = obj.get(key);
            }

            currPath.append("/");
        }
        return this;
    }

    public void check(JsonBaseBuilder schema) {
        ExpectationJsonFeedback feedback = new ExpectationJsonFeedback(path, originalElem);
        schema.check(elem, feedback);

        if (feedback.isFailed) {
            throw new WrongAnswer(feedback.feedback.trim());
        }
    }
}
