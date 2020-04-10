package org.hyperskill.hstest.v7.testing.expect;

import java.util.function.Function;

public class ExpectAmountBuilder<T> {
    final Expectation<T> expect;

    ExpectAmountBuilder(Expectation<T> expect) {
        this.expect = expect;
    }

    public ExpectTypeBuilder<T> toContain() {
        return toContain(amount -> amount > 0);
    }

    public ExpectTypeBuilder<T> toContainOnly(int count) {
        return toContain(amount -> amount == count, "should be equal to " + count);
    }

    public ExpectTypeBuilder<T> toContainMoreThan(int count) {
        return toContain(amount -> amount > count, "should be more than " + count);
    }

    public ExpectTypeBuilder<T> toContainLessThan(int count) {
        return toContain(amount -> amount < count, "should be less than " + count);
    }

    public ExpectTypeBuilder<T> toContainAtLeast(int count) {
        return toContain(amount -> amount >= count, "should be at least " + count);
    }

    public ExpectTypeBuilder<T> toContainAtMost(int count) {
        return toContain(amount -> amount <= count, "should be " + count + " at most");
    }

    public ExpectTypeBuilder<T> toContainBetween(int lowerBound, int upperBound) {
        return toContain(amount -> amount >= lowerBound && amount <= upperBound,
            "should be between " + lowerBound + " and " + upperBound);
    }

    public ExpectTypeBuilder<T> toContain(Function<Integer, Boolean> checkAmount) {
        return toContain(checkAmount, "");
    }

    private ExpectTypeBuilder<T> toContain(Function<Integer, Boolean> checkAmount, String hint) {
        expect.checkAmount = checkAmount;
        expect.hint = hint;
        return new ExpectTypeBuilder<>(expect);
    }
}
