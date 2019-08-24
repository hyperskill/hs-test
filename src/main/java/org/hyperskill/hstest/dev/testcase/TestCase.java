package org.hyperskill.hstest.dev.testcase;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TestCase<AttachType> {

    private List<String> args = new ArrayList<>();
    private AttachType attach = null;

    private BiFunction<String, AttachType, CheckResult> checkFunction = null;
    private List<Function<String, String>> inputFuncs = new LinkedList<>();

    // files needed to be set up before test
    private Map<String, String> files = new HashMap<>();

    private Map<Class<? extends Exception>, String> ex;

    // runnables that should be run before test
    private List<Process> processes = new ArrayList<>();

    public TestCase() {
        // use methods to configure TestCase
    }

    public TestCase<AttachType> setInput(String input) {
        inputFuncs.clear();
        inputFuncs.add(o -> input);
        return this;
    }

    public TestCase<AttachType> addInput(Function<String, String> inputFunc) {
        inputFuncs.add(inputFunc);
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

    public TestCase<AttachType> runWith(Process process) {
        processes.add(process);
        return this;
    }

    public TestCase<AttachType> feedbackOnException(Class<? extends Exception> clazz, String feedback) {
        ex.put(clazz, feedback);
        return this;
    }

    public TestCase<AttachType> setCheckFunc(BiFunction<String, AttachType, CheckResult> func) {
        this.checkFunction = func;
        return this;
    }

    public List<Function<String, String>> getInputFuncs() {
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

    public List<Process> getProcesses() {
        return processes;
    }

    public BiFunction<String, AttachType, CheckResult> getCheckFunc() {
        return checkFunction;
    }
}
