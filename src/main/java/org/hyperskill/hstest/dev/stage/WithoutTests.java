package org.hyperskill.hstest.dev.stage;

public class WithoutTests extends BaseStageTest {
    public WithoutTests(Class<?> testedClass) throws Exception {
        super(testedClass.getMethod("main", String[].class));
        isWithoutTests = true;
    }
}
