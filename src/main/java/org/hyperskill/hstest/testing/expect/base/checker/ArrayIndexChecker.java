package org.hyperskill.hstest.testing.expect.base.checker;

import org.hyperskill.hstest.testing.expect.base.SchemaBuilder;

public class ArrayIndexChecker {
    public IntegerChecker indexChecker;
    public SchemaBuilder valueChecker;
    public boolean requireMatch;
    public boolean matched = false;
    public String itemDescription;

    public ArrayIndexChecker(IntegerChecker indexChecker,
                             SchemaBuilder valueChecker,
                             boolean requireMatch,
                             String itemDescription) {
        this.indexChecker = indexChecker;
        this.valueChecker = valueChecker;
        this.requireMatch = requireMatch;
        this.itemDescription = itemDescription;
    }
}
