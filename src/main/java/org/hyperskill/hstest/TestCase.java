package org.hyperskill.hstest;

import java.util.*;

public class TestCase<ClueType> {

    public String input;
    public List<Object> args;
    public ClueType clue;

    // files needed to be set up before test
    public Map<String, String> files = new HashMap<>();

    public TestCase() {
        this(null);
    }

    public TestCase(ClueType clue) {
        this(clue, "");
    }

    public TestCase(ClueType clue, String consoleInput, Object... methodArguments) {
        this.input = consoleInput;
        this.args = new ArrayList<>();
        this.clue = clue;
        Collections.addAll(this.args, methodArguments);
    }

    public TestCase<ClueType> setInput(String input) {
        this.input = input;
        return this;
    }

    public TestCase<ClueType> setClue(ClueType clue) {
        this.clue = clue;
        return this;
    }

    public TestCase<ClueType> addArgument(Object argument) {
        args.add(argument);
        return this;
    }

    public TestCase<ClueType> addFile(String filename, String content) {
        files.put(filename, content);
        return this;
    }
}
