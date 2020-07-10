package org.hyperskill.hstest.testing.expect;

import org.hyperskill.hstest.testing.expect.text.AmountBuilder;
import org.hyperskill.hstest.testing.expect.text.TextSearcher;

import java.util.function.Function;

public class ExpectationBuilder<T> {
    final Expectation<T> expect;

    ExpectationBuilder(Expectation<T> expect) {
        this.expect = expect;
    }

    public AmountBuilder<T> asText() {
        return new AmountBuilder<>(expect);
    }

    public TextSearcher<T> toContain() {
        return asText().toContain();
    }

    public TextSearcher<T> toContain(int count) {
        return asText().toContain(count);
    }

    public TextSearcher<T> toContainMoreThan(int count) {
        return asText().toContainMoreThan(count);
    }

    public TextSearcher<T> toContainLessThan(int count) {
        return asText().toContainLessThan(count);
    }

    public TextSearcher<T> toContainAtLeast(int count) {
        return asText().toContainAtLeast(count);
    }

    public TextSearcher<T> toContainAtMost(int count) {
        return asText().toContainAtMost(count);
    }

    public TextSearcher<T> toContainBetween(int lowerBound, int upperBound) {
        return asText().toContainBetween(lowerBound, upperBound);
    }

    public TextSearcher<T> toContain(Function<Integer, Boolean> checkAmount) {
        return asText().toContain(checkAmount);
    }

    public TextSearcher<T> toContain(Function<Integer, Boolean> checkAmount, String hint) {
        return asText().toContain(checkAmount, hint);
    }

    public TextSearcher<T> toContain(Function<Integer, Boolean> checkAmount, Function<Integer, String> hint) {
        return asText().toContain(checkAmount, hint);
    }
}
