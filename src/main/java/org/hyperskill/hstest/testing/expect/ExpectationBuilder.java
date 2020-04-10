package org.hyperskill.hstest.testing.expect;

import java.util.function.Function;

public class ExpectationBuilder<T> {
    final Expectation<T> expect;

    ExpectationBuilder(Expectation<T> expect) {
        this.expect = expect;
    }

    public ExpectationSearcher<T> toContain() {
        return toContain(amount -> amount > 0);
    }

    public ExpectationSearcher<T> toContain(int count) {
        return toContain(amount -> amount == count, "equal to " + count);
    }

    public ExpectationSearcher<T> toContainMoreThan(int count) {
        return toContain(amount -> amount > count, "more than " + count);
    }

    public ExpectationSearcher<T> toContainLessThan(int count) {
        return toContain(amount -> amount < count, "less than " + count);
    }

    public ExpectationSearcher<T> toContainAtLeast(int count) {
        return toContain(amount -> amount >= count, "at least " + count);
    }

    public ExpectationSearcher<T> toContainAtMost(int count) {
        return toContain(amount -> amount <= count, count + " at most");
    }

    public ExpectationSearcher<T> toContainBetween(int lowerBound, int upperBound) {
        return toContain(amount -> amount >= lowerBound && amount <= upperBound,
            "between " + lowerBound + " and " + upperBound);
    }

    public ExpectationSearcher<T> toContain(Function<Integer, Boolean> checkAmount) {
        return toContain(checkAmount, i -> null);
    }

    public ExpectationSearcher<T> toContain(Function<Integer, Boolean> checkAmount, String hint) {
        return toContain(checkAmount, realSize -> "expected to be " + hint + ", found " + realSize);
    }

    public ExpectationSearcher<T> toContain(Function<Integer, Boolean> checkAmount, Function<Integer, String> hint) {
        expect.checkAmount = checkAmount;
        expect.hintFunc = hint;
        return new ExpectationSearcher<>(expect);
    }
}
