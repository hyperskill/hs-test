package org.hyperskill.hstest.testing.expect.text;

import org.hyperskill.hstest.testing.expect.Expectation;

import java.util.function.Function;

public class ExpectationTextAmountBuilder<T> {
    final Expectation<T> expect;

    public ExpectationTextAmountBuilder(Expectation<T> expect) {
        this.expect = expect;
    }

    public ExpectationTextSearcher<T> toContain() {
        return toContain(amount -> amount > 0);
    }

    public ExpectationTextSearcher<T> toContain(int count) {
        return toContain(amount -> amount == count, "equal to " + count);
    }

    public ExpectationTextSearcher<T> toContainMoreThan(int count) {
        return toContain(amount -> amount > count, "more than " + count);
    }

    public ExpectationTextSearcher<T> toContainLessThan(int count) {
        return toContain(amount -> amount < count, "less than " + count);
    }

    public ExpectationTextSearcher<T> toContainAtLeast(int count) {
        return toContain(amount -> amount >= count, "at least " + count);
    }

    public ExpectationTextSearcher<T> toContainAtMost(int count) {
        return toContain(amount -> amount <= count, count + " at most");
    }

    public ExpectationTextSearcher<T> toContainBetween(int lowerBound, int upperBound) {
        return toContain(amount -> amount >= lowerBound && amount <= upperBound,
            "between " + lowerBound + " and " + upperBound);
    }

    public ExpectationTextSearcher<T> toContain(Function<Integer, Boolean> checkAmount) {
        return toContain(checkAmount, i -> null);
    }

    public ExpectationTextSearcher<T> toContain(Function<Integer, Boolean> checkAmount, String hint) {
        return toContain(checkAmount, realSize -> "expected to be " + hint + ", found " + realSize);
    }

    public ExpectationTextSearcher<T> toContain(Function<Integer, Boolean> checkAmount, Function<Integer, String> hint) {
        expect.checkAmount = checkAmount;
        expect.hintFunc = hint;
        return new ExpectationTextSearcher<>(expect);
    }

}
