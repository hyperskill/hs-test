package org.hyperskill.hstest;

public abstract class MainMethodTest<ClueType> extends BaseStageTest<ClueType> {

    public MainMethodTest(Class<?> testedClass) throws Exception {
        super(testedClass.getMethod("main", String[].class));
        isTestingMain = true;
    }

}
