package org.hyperskill.hstest.testing.expect.base;

public abstract class SchemaBuilder<E, F> {
    public abstract boolean check(E elem, F feedback);
}
