package org.hyperskill.hstest.testcase;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
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

@Accessors(chain = true)
public class TestCase<AttachType> {
    public static final int DEFAULT_TIME_LIMIT = 15000;

    @Getter private final List<String> args = new ArrayList<>();

    @Getter @Setter String sourceName;
    @Getter @Setter private AttachType attach;
    @Getter @Setter private String feedback = "";
    @Getter @Setter private int timeLimit = DEFAULT_TIME_LIMIT;
    @Getter @Setter private BiFunction<String, AttachType, CheckResult> checkFunc;

    @Getter private final List<DynamicInputFunction> inputFuncs = new LinkedList<>();
    @Getter private String input;
    private DynamicTesting dynamicTesting;

    @Getter private final Map<String, String> files = new LinkedHashMap<>();
    @Getter private final Map<Class<? extends Throwable>, String> feedbackOnExceptions = new LinkedHashMap<>();
    @Getter private final List<Process> processes = new ArrayList<>();

    public TestCase() {
        // use methods to configure TestCase
    }

    public TestCase<AttachType> setInput(String input) {
        inputFuncs.clear();
        this.input = input;
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
            dynamicTesting = DynamicTesting.toDynamicTesting(sourceName, args, inputFuncs);
        }
        return dynamicTesting;
    }

    public TestCase<AttachType> addArguments(String... arguments) {
        args.addAll(Arrays.asList(arguments));
        return this;
    }

    public TestCase<AttachType> addFile(String filename, String content) {
        files.put(filename, content);
        return this;
    }

    public TestCase<AttachType> setFiles(Map<String, String> files) {
        if (files != null) {
            for (String filename : files.keySet()) {
                addFile(filename, files.get(filename));
            }
        }
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
}
