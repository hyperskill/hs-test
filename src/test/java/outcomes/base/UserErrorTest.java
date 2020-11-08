package outcomes.base;

public abstract class UserErrorTest<T> extends ExpectedFailTest<T> {

    public UserErrorTest() {
        super();
    }

    public UserErrorTest(Class<?> testedClass) {
        super(testedClass);
    }

    @NotContainMessage(counts=false)
    public final String[] unexpectedErrorMessages = {
        "Unexpected error",
        "at org.hyperskill.hstest",
        "org.junit.",
        "at sun.reflect.",
        "at java.base/jdk.internal.reflect."
    };
}
