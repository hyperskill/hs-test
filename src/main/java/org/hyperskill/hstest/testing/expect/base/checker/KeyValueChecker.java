package org.hyperskill.hstest.testing.expect.base.checker;

public class KeyValueChecker<V> {

    public final StringChecker keyChecker;
    public final String failFeedback;
    public final V valueChecker;
    public final boolean requireMatch;
    public boolean matched = false;

    public KeyValueChecker(StringChecker keyChecker, V valueChecker,
                    boolean requireMatch, String failFeedback) {
        this.keyChecker = keyChecker;
        this.failFeedback = failFeedback;
        this.valueChecker = valueChecker;
        this.requireMatch = requireMatch;
    }

}
