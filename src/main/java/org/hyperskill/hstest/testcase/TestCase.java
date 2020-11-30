package org.hyperskill.hstest.testcase;

import org.hyperskill.hstest.dynamic.input.DynamicInputFunction;
import org.hyperskill.hstest.dynamic.input.DynamicTesting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TestCase<AttachType> {

    private static final int DEFAULT_TIME_LIMIT = 15000;

    private Class<?> testedClass = null;
    private Object testedObject = null;

    private final List<String> args = new ArrayList<>();
    private AttachType attach = null;

    private int timeLimit = DEFAULT_TIME_LIMIT;

    private BiFunction<String, AttachType, CheckResult> checkFunction = null;
    private final List<DynamicInputFunction> inputFuncs = new LinkedList<>();
    private String staticInput = null;
    private DynamicTesting dynamicTesting = null;

    // files needed to be set up before test
    private final Map<String, String> files = new LinkedHashMap<>();

    private final Map<Class<? extends Throwable>, String>
        feedbackOnExceptions = new LinkedHashMap<>();

    // runnables that should be run before test
    private final List<Process> processes = new ArrayList<>();

    public TestCase() {
        // use methods to configure TestCase
    }

    public void setTestedClass(Class<?> testedClass) {
        this.testedClass = testedClass;
    }

    public Class<?> getTestedClass() {
        return testedClass;
    }

    public void setTestedObject(Object testedObject) {
        this.testedObject = testedObject;
    }

    public Object getTestedObject() {
        return testedObject;
    }

    public String getInput() {
        return staticInput;
    }

    public TestCase<AttachType> setInput(String input) {
        inputFuncs.clear();
        staticInput = input;
        addInput(1, out -> input);
        return this;
    }

    @Deprecated
    public TestCase<AttachType> addInput(String input) {
        addInput(1, input);
        return this;
    }

    @Deprecated
    public TestCase<AttachType> addInput(Function<String, Object> inputFunc) {
        addInput(1, inputFunc);
        return this;
    }

    @Deprecated
    public TestCase<AttachType> addInput(int triggerCount, String input) {
        addInput(triggerCount, out -> input);
        return this;
    }

    @Deprecated
    public TestCase<AttachType> addInput(int triggerCount, Function<String, Object> inputFunc) {
        inputFuncs.add(new DynamicInputFunction(triggerCount, inputFunc));
        return this;
    }

    @Deprecated
    public TestCase<AttachType> addInfInput(String input) {
        addInput(-1, input);
        return this;
    }

    @Deprecated
    public TestCase<AttachType> addInfInput(Function<String, Object> inputFunc) {
        addInput(-1, inputFunc);
        return this;
    }

    public TestCase<AttachType> setDynamicTesting(DynamicTesting dynamicTesting) {
        this.dynamicTesting = dynamicTesting;
        return this;
    }

    public DynamicTesting getDynamicTesting() {
        if (dynamicTesting == null) {
            dynamicTesting = DynamicTesting.toDynamicTesting(testedClass.getName(), args, inputFuncs);
        }
        return dynamicTesting;
    }

    public TestCase<AttachType> setAttach(AttachType attach) {
        this.attach = attach;
        return this;
    }

    public TestCase<AttachType> addArguments(String... arguments) {
        args.addAll(Arrays.asList(arguments));
        return this;
    }

    public TestCase<AttachType> addFile(String filename, String content) {
        files.put(filename, content);
        return this;
    }

    public TestCase<AttachType> setTimeLimit(int timeLimitMs) {
        timeLimit = timeLimitMs;
        return this;
    }

    public TestCase<AttachType> setTimeLimit(int timeLimit, TimeUnit timeUnit) {
        return setTimeLimit((int) TimeUnit.MILLISECONDS.convert(timeLimit, timeUnit));
    }

    public TestCase<AttachType> runWith(Process process) {
        processes.add(process);
        return this;
    }

    public TestCase<AttachType> feedbackOnException(Class<? extends Throwable> clazz, String feedback) {
        feedbackOnExceptions.put(clazz, feedback);
        return this;
    }

    public TestCase<AttachType> setCheckFunc(BiFunction<String, AttachType, CheckResult> func) {
        this.checkFunction = func;
        return this;
    }

    @Deprecated
    public List<DynamicInputFunction> getInputFuncs() {
        return inputFuncs;
    }

    public List<String> getArgs() {
        return args;
    }

    public AttachType getAttach() {
        return attach;
    }

    public Map<String, String> getFiles() {
        return files;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public BiFunction<String, AttachType, CheckResult> getCheckFunc() {
        return checkFunction;
    }

    public Map<Class<? extends Throwable>, String> getFeedbackOnExceptions() {
        return feedbackOnExceptions;
    }
}
