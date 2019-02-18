package org.hyperskill.hstest.v2.stage;

public class WithoutTests extends BaseStageTest {
    public WithoutTests(Class<?> testedClass) throws Exception {
        super(testedClass.getMethod("main", String[].class));
        isWithoutTests = true;
    }
}
