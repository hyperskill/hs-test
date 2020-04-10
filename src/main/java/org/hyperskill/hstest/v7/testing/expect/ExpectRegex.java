package org.hyperskill.hstest.v7.testing.expect;

import org.hyperskill.hstest.v7.exception.outcomes.PresentationError;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpectRegex {

    /**
     * Checks that a string contains a particular number of lines.
     */
    public static List<String> expectRegex(int count, String regex, String output)
            throws PresentationError {
        return expectRegex(actual -> actual == count, output, "should be equal to " + count);
    }

    /**
     * Checks that a string contains a number of lines greater than a particular number.
     */
    public static List<String> expectRegexMoreThan(int count, String regex, String output)
            throws PresentationError {
        return expectRegex(actual -> actual > count, output, "should be more than " + count);
    }

    /**
     * Checks that a string contains a number of lines greater than or equal to a particular number.
     */
    public static List<String> expectRegexAtLeast(int count, String regex, String output)
            throws PresentationError {
        return expectRegex(actual -> actual >= count, output, "should be at least " + count);
    }

    /**
     * Checks that a string contains a number of lines less than a particular number.
     */
    public static List<String> expectRegexLessThan(int count, String regex, String output)
            throws PresentationError {
        return expectRegex(actual -> actual < count, output, "should be less than " + count);
    }

    /**
     * Checks that a string contains a number of lines less than ot equal to a particular number.
     */
    public static List<String> expectRegexAtMax(int count, String regex, String output)
            throws PresentationError {
        return expectRegex(actual -> actual <= count, output, "should be less than " + count);
    }

    /**
     * Checks that a string contains a number of lines that can be verified through a function.
     */
    public static List<String> expectRegex(Function<Integer, Boolean> verify, String regex, String output)
            throws PresentationError {
        return expectRegex(verify, output, "");
    }

    /**
     * Checks that a string (after trimming) contains a number of lines
     * that can be verified through a function. Additionally, displays a hint in parenthesis.
     */
    public static List<String> expectRegex(
            Function<Integer, Boolean> verify, String regex, String output, String hint) throws PresentationError {
        String outputToCheck = output.trim();
        Matcher m = Pattern.compile(regex).matcher(outputToCheck);

        List<String> matches = new ArrayList<>();
        while (m.find()) {
            matches.add(m.group());
        }
        return matches;
    }

    private static String constructFeedback(String output, String hint) {
        if (output.length() == 0) {
            return "Since the last input no output was printed, but should.";
        }
        if (hint.length() != 0) {
            hint = " (" + hint + ")";
        }
        return "The following output contains wrong number of words" + hint + ":\n\n" + output;
    }
}
