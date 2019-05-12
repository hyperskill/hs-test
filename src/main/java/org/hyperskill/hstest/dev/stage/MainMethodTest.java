package org.hyperskill.hstest.dev.stage;

public abstract class MainMethodTest<AttachType> extends BaseStageTest<AttachType> {

    public MainMethodTest(Class<?> testedClass) throws Exception {
        super(testedClass.getMethod("main", String[].class), true);
    }
}
