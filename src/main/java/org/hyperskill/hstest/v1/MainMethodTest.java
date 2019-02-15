package org.hyperskill.hstest.v1;

public abstract class MainMethodTest<ClueType> extends StageTest<ClueType> {

    public MainMethodTest(Class<?> testedClass) throws Exception {
        super(testedClass.getMethod("main", String[].class));
        isTestingMain = true;
    }

}
