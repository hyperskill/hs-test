package org.hyperskill.hstest.testing.expect.base.checker;

public class ArrayIndexChecker<V> {
    public IntegerChecker indexChecker;
    public V valueChecker;
    public boolean requireMatch;
    public boolean matched = false;
    public String itemDescription;

    public ArrayIndexChecker(IntegerChecker indexChecker,
                             V valueChecker,
                             boolean requireMatch,
                             String itemDescription) {
        this.indexChecker = indexChecker;
        this.valueChecker = valueChecker;
        this.requireMatch = requireMatch;
        this.itemDescription = itemDescription;
    }
}
