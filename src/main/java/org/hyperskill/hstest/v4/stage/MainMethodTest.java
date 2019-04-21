package org.hyperskill.hstest.v4.stage;

public abstract class MainMethodTest<AttachType> extends BaseStageTest<AttachType> {

    public MainMethodTest(Class<?> testedClass) throws Exception {
        super(testedClass.getMethod("main", String[].class));
        isTestingMain = true;
    }
}
