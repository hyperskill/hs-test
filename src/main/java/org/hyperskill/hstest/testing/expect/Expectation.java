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
    Supplier<List<T>> findAllElemsFunc;
    Supplier<String> whatsWrongFunc;
    Function<Integer, String> hintFunc = i -> null;

    private Expectation(String output) {
        text = output.trim();
    }

    public static ExpectationBuilder<?> expect(String text) {
        return new ExpectationBuilder<>(new Expectation<>(text));
    }

    <X> Expectation<X> copy(Supplier<List<X>> findAllElemsFunc) {
        Expectation<X> result = new Expectation<>(text);
        result.checkAmount = checkAmount;
        result.findAllElemsFunc = findAllElemsFunc;
        result.whatsWrongFunc = whatsWrongFunc;
        result.hintFunc = hintFunc;
        return result;
    }

    List<T> check() {
        List<T> found = findAllElemsFunc.get();
        if (found == null || !checkAmount.apply(found.size())) {
            int size = -1;
            if (found != null) {
                size = found.size();
            }
            throw new PresentationError(constructFeedback(size));
        }
        return found;
    }

    private String constructFeedback(int foundSize) {
        String feedback;
        if (text.length() == 0) {
            feedback = "Since the last input no output was printed, but should.";
        } else {
            String whatsWrong = whatsWrongFunc.get();
            String hint = hintFunc.apply(foundSize);
            if (hint == null) {
                hint = "";
            }
            if (hint.length() != 0) {
                hint = " (" + hint + ")";
            }
            feedback = "The following output " + whatsWrong + hint + ":\n" + text;
        }
        return feedback;
    }
}
