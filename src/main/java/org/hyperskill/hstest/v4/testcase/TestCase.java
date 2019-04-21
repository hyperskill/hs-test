package org.hyperskill.hstest.v4.testcase;

import java.util.*;

public class TestCase<AttachType> {

    private String input = "";
    private List<Object> args = new ArrayList<>();
    private AttachType attach = null;

    // files needed to be set up before test
    private Map<String, String> files = new HashMap<>();

    private Map<Class<? extends Exception>, String> ex;

    // runnables that should be run before test
    private List<Process> processes = new ArrayList<>();

    public TestCase() {
        // use methods to configure TestCase
    }

    public TestCase<AttachType> setInput(String input) {
        this.input = input;
        return this;
    }

    public TestCase<AttachType> setAttach(AttachType attach) {
        this.attach = attach;
        return this;
    }

    public TestCase<AttachType> addArgument(Object argument) {
        args.add(argument);
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

    public TestCase<AttachType> feedbackOnExcaption(Class<? extends Exception> clazz, String feedback) {
        ex.put(clazz, feedback);
        return this;
    }

    public String getInput() {
        return input;
    }

    public List<Object> getArgs() {
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
}
