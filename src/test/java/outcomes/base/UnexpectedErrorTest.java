package outcomes.base;

public abstract class UnexpectedErrorTest<T> extends ExpectedFailTest<T> {

    public UnexpectedErrorTest() {
        super();
    }

    @Deprecated
    public UnexpectedErrorTest(Class<?> testedClass) {
        super(testedClass);
    }

    @ContainsMessage(counts=false)
    public final String unexpectedErrorMessage = "Unexpected error";
}
