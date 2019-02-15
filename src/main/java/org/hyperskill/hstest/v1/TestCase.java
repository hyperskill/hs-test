package org.hyperskill.hstest.v1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class TestCase<ClueType> {

    public String input;
    public ArrayList<Object> args;
    public ClueType clue;

    // files needed to be set up before test
    public HashMap<String, String> files = new HashMap<>();

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
