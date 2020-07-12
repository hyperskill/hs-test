package org.hyperskill.hstest.testing.expect.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import org.hyperskill.hstest.common.Utils;
import org.hyperskill.hstest.exception.outcomes.PresentationError;
import org.hyperskill.hstest.testing.expect.Expectation;
import org.hyperskill.hstest.testing.expect.json.builder.JsonBaseBuilder;

import static java.util.regex.Pattern.compile;
import static org.hyperskill.hstest.testing.expect.Expectation.expect;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.any;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isArray;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isNumber;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isObject;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isString;

public class ExpectationJsonBuilder<T> {

    Expectation<T> expect;

    JsonElement elem;

    public ExpectationJsonBuilder(Expectation<T> expect) {
        this.expect = expect;
        try {
            elem = Utils.getJson(expect.text);
        } catch (JsonSyntaxException ex) {
            throw new PresentationError("Expected JSON, got something else.\n"
                + ex.getMessage() + "\n\n" + "Content:\n" + expect.text);
        }
    }

    public ExpectationJsonBuilder(Expectation<T> expect, JsonElement elem) {
        this.expect = expect;
        this.elem = elem;
    }

    public ExpectationJsonBuilder<T> check(JsonBaseBuilder schema) {
        expect("").asJson().check(
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

        schema.check(elem);

        return this;
    }



}
