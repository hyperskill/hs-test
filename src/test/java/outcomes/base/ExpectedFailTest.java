package outcomes.base;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hyperskill.hstest.common.ReflectionUtils;
import org.hyperskill.hstest.stage.StageTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Field;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

public abstract class ExpectedFailTest<T> extends StageTest<T> {

    public ExpectedFailTest() {
        super();
    }

    @Deprecated
    public ExpectedFailTest(Class<?> testedClass) {
        super(testedClass);
    }

    private static class FalseMatcher extends BaseMatcher {
        String text;

        public FalseMatcher(String text) {
            this.text = text;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(text);
        }

        @Override
        public boolean matches(Object item) {
            return false;
        }
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public final void determineRestrictions() {
        exception.expect(AssertionError.class);

        boolean hasFields = false;

        for (Field field : ReflectionUtils.getAllFields(this)) {
            boolean contains = field.isAnnotationPresent(ContainsMessage.class);
            boolean notContain = field.isAnnotationPresent(NotContainMessage.class);

            if (contains || notContain) {
                if (contains && notContain) {
                    exception.expect(new FalseMatcher("Cannot use both "
                        + "ContainsMessage and NotContainMessage annotations "
                        + "on field " + field.getName()));
                    continue;
                }

                List<String> values = ReflectionUtils
                    .getObjectsFromField(field, this, String.class);

                if (contains) {
                    if (field.getAnnotation(ContainsMessage.class).counts()) {
                        hasFields = true;
                    }
                    values.forEach(exception::expectMessage);
                }

                if (notContain) {
                    if (field.getAnnotation(NotContainMessage.class).counts()) {
                        hasFields = true;
                    }
                    values.forEach(value -> exception.expectMessage(not(containsString(value))));
                }
            }
        }

        if (!hasFields) {
            exception.expect(new FalseMatcher("Cannot find fields marked with " +
                "ContainsMessage or NotContainMessage annotations"));
        }
    }
}
