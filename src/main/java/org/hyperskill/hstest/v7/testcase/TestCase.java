package org.hyperskill.hstest.v7.testcase;

import org.hyperskill.hstest.v7.dynamic.input.DynamicInputFunction;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;


public class TestCase<AttachType> {

    private List<String> args = new ArrayList<>();
    private AttachType attach = null;

    private int timeLimit = 15000;

    private BiFunction<String, AttachType, CheckResult> checkFunction = null;
    private List<DynamicInputFunction> inputFuncs = new LinkedList<>();
    private String staticInput = null;

    // files needed to be set up before test
    private Map<String, String> files = new LinkedHashMap<>();

    private Map<Class<? extends Throwable>, String>
        feedbackOnExceptions = new LinkedHashMap<>();

    // runnables that should be run before test
    private List<Process> processes = new ArrayList<>();

    public TestCase() {
        // use methods to configure TestCase
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

    public TestCase<AttachType> addInput(String input) {
        addInput(1, input);
        return this;
    }

    public TestCase<AttachType> addInput(int triggerCount, String input) {
        addInput(triggerCount, out -> input);
        return this;
    }

    public TestCase<AttachType> addInfInput(String input) {
        addInput(-1, input);
        return this;
    }

    public TestCase<AttachType> addInput(Function<String, Object> inputFunc) {
        addInput(1, inputFunc);
        return this;
    }

    public TestCase<AttachType> addInput(int triggerCount, Function<String, Object> inputFunc) {
        inputFuncs.add(new DynamicInputFunction(triggerCount, inputFunc));
        return this;
    }

    public TestCase<AttachType> addInfInput(Function<String, Object> inputFunc) {
        addInput(-1, inputFunc);
        return this;
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
