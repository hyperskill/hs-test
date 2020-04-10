package org.hyperskill.hstest.testing.expect;

import org.hyperskill.hstest.exception.outcomes.PresentationError;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Class that checks an output to meet certain requirements.
 * If it's not, then PresentationError is thrown.
 */
public class Expectation<T> {

    final String text;

    Function<Integer, Boolean> checkAmount;
    Supplier<List<T>> foundElemsFunc;
    Supplier<String> whatsWrongFunc;
    String hint;

    private Expectation(String output) {
        text = output;
    }

    public static ExpectAmountBuilder<?> expect(String text) {
        return new ExpectAmountBuilder<>(new Expectation<>(text));
    }

    <X> Expectation<X> copy(Supplier<List<X>> foundElemsFunc) {
        Expectation<X> result = new Expectation<>(text);
        result.checkAmount = checkAmount;
        result.foundElemsFunc = foundElemsFunc;
        result.whatsWrongFunc = whatsWrongFunc;
        result.hint = hint;
        return result;
    }

    List<T> check() {
        List<T> found = foundElemsFunc.get();
        if (!checkAmount.apply(found.size())) {
            throw new PresentationError(constructFeedback());
        }
        return found;
    }

    private String constructFeedback() {
        String feedback;
        if (text.length() == 0) {
            feedback = "Since the last input no output was printed, but should.";
        } else {
            String whatsWrong = whatsWrongFunc.get();
            if (hint == null) {
                hint = "";
            }
            if (hint.length() != 0) {
                hint = " (" + hint + ")";
            }
            feedback = "The following output " + whatsWrong + hint + ":\n\n" + text;
        }
        return feedback;
    }





    /**
     * Checks that condition is true.
     * If it's not then it throws PresentationError with feedback.
     * This method should be called from all other methods.
     */
    public static void expect(boolean condition, String feedback) throws PresentationError {
        expect(condition, () -> feedback);
    }

    public static void expect(boolean condition, Supplier<String> feedbackFunc) throws PresentationError {
        if (!condition) {
            throw new PresentationError(feedbackFunc.get());
        }
    }
}
