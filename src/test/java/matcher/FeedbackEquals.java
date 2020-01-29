package matcher;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.SubstringMatcher;


public class FeedbackEquals extends SubstringMatcher {
    public FeedbackEquals(String substring) {
        super(substring);
    }

    @Override
    protected boolean evalSubstringOf(String s) {
        return s.equals(substring);
    }

    @Override
    protected String relationship() {
        return "equals";
    }

    @Factory
    public static Matcher<String> feedbackEquals(String substring) {
        return new FeedbackEquals(substring);
    }
}
