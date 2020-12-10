package org.hyperskill.hstest.dynamic.input;

import org.hyperskill.hstest.dynamic.output.InfiniteLoopDetector;
import org.hyperskill.hstest.dynamic.output.OutputHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hyperskill.hstest.common.Utils.cleanText;

public class DynamicInputHandler {
    private byte[] currentReader = null;
    private int pos = 0;
    private final List<String> inputLines = new ArrayList<>();
    private final DynamicTestFunction dynamicInputFunction;

    DynamicInputHandler(DynamicTestFunction func) {
        dynamicInputFunction = func;
    }

    int ejectChar() {
        int character = nextByte();
        if (character == -1) {
            ejectNextLine();
            character = nextByte();
        }
        return character;
    }

    String ejectLine() {
        if (noInputPrepared()) {
            ejectNextLine();
        }
        pos = currentReader.length;
        return new String(currentReader, 0, currentReader.length - 1);
    }

    private boolean noInputPrepared() {
        return currentReader == null || pos >= currentReader.length;
    }

    private int nextByte() {
        if (noInputPrepared()) {
            return -1;
        }
        return currentReader[pos++];
    }

    private void ejectNextLine() {
        if (inputLines.isEmpty()) {
            ejectNextInput();
            if (inputLines.isEmpty()) {
                return;
            }
        }
        String nextLine = inputLines.remove(0) + "\n";
        currentReader = nextLine.getBytes();
        pos = 0;
        OutputHandler.injectInput("> " + nextLine);
    }

    private void ejectNextInput() {
        String newInput = dynamicInputFunction.get();
        InfiniteLoopDetector.inputRequested();

        if (newInput == null) {
            return;
        }

        newInput = cleanText(newInput);
        inputLines.addAll(Arrays.asList(newInput.split("\n")));
    }
}
