package org.hyperskill.hstest.testing.expect;

import org.hyperskill.hstest.common.FileUtils;
import org.hyperskill.hstest.exception.outcomes.PresentationError;
import org.hyperskill.hstest.testing.expect.text.ExpectationTextAmountBuilder;
import org.hyperskill.hstest.testing.expect.text.ExpectationTextSearcher;

import java.io.IOException;
import java.util.function.Function;

public class ExpectationBuilder<T> {
    final Expectation<T> expect;

    ExpectationBuilder(Expectation<T> expect) {
        this.expect = expect;
    }

    public ExpectationTextAmountBuilder<T> asText() {
        return new ExpectationTextAmountBuilder<>(expect);
    }

    public ExpectationBuilder<T> fromFile() {
        try {
            expect.text = FileUtils.readFile(expect.text);
            return this;
        } catch (IOException ex) {
            throw new PresentationError("File \"" + expect.error
                + "\" expected, but not found");
        }
    }

    public ExpectationTextSearcher<T> toContain() {
        return asText().toContain();
    }

    public ExpectationTextSearcher<T> toContain(int count) {
        return asText().toContain(count);
    }

    public ExpectationTextSearcher<T> toContainMoreThan(int count) {
        return asText().toContainMoreThan(count);
    }

    public ExpectationTextSearcher<T> toContainLessThan(int count) {
        return asText().toContainLessThan(count);
    }

    public ExpectationTextSearcher<T> toContainAtLeast(int count) {
        return asText().toContainAtLeast(count);
    }

    public ExpectationTextSearcher<T> toContainAtMost(int count) {
        return asText().toContainAtMost(count);
    }

    public ExpectationTextSearcher<T> toContainBetween(int lowerBound, int upperBound) {
        return asText().toContainBetween(lowerBound, upperBound);
    }

    public ExpectationTextSearcher<T> toContain(Function<Integer, Boolean> checkAmount) {
        return asText().toContain(checkAmount);
    }

    public ExpectationTextSearcher<T> toContain(Function<Integer, Boolean> checkAmount, String hint) {
        return asText().toContain(checkAmount, hint);
    }

    public ExpectationTextSearcher<T> toContain(Function<Integer, Boolean> checkAmount, Function<Integer, String> hint) {
        return asText().toContain(checkAmount, hint);
    }
}
