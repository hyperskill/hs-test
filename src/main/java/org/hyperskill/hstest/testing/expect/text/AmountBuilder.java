package org.hyperskill.hstest.testing.expect.text;

import org.hyperskill.hstest.testing.expect.Expectation;

import java.util.function.Function;

public class AmountBuilder<T> {
    final Expectation<T> expect;

    public AmountBuilder(Expectation<T> expect) {
        this.expect = expect;
    }

    public TextSearcher<T> toContain() {
        return toContain(amount -> amount > 0);
    }

    public TextSearcher<T> toContain(int count) {
        return toContain(amount -> amount == count, "equal to " + count);
    }

    public TextSearcher<T> toContainMoreThan(int count) {
        return toContain(amount -> amount > count, "more than " + count);
    }

    public TextSearcher<T> toContainLessThan(int count) {
        return toContain(amount -> amount < count, "less than " + count);
    }

    public TextSearcher<T> toContainAtLeast(int count) {
        return toContain(amount -> amount >= count, "at least " + count);
    }

    public TextSearcher<T> toContainAtMost(int count) {
        return toContain(amount -> amount <= count, count + " at most");
    }

    public TextSearcher<T> toContainBetween(int lowerBound, int upperBound) {
        return toContain(amount -> amount >= lowerBound && amount <= upperBound,
            "between " + lowerBound + " and " + upperBound);
    }

    public TextSearcher<T> toContain(Function<Integer, Boolean> checkAmount) {
        return toContain(checkAmount, i -> null);
    }

    public TextSearcher<T> toContain(Function<Integer, Boolean> checkAmount, String hint) {
        return toContain(checkAmount, realSize -> "expected to be " + hint + ", found " + realSize);
    }

    public TextSearcher<T> toContain(Function<Integer, Boolean> checkAmount, Function<Integer, String> hint) {
        expect.checkAmount = checkAmount;
        expect.hintFunc = hint;
        return new TextSearcher<>(expect);
    }

}
