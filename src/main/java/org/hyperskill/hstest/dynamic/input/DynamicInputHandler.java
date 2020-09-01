package org.hyperskill.hstest.dynamic.input;

import org.hyperskill.hstest.dynamic.output.SystemOutHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static org.hyperskill.hstest.common.Utils.cleanText;

public class DynamicInputHandler {
    private byte[] currentReader = null;
    private int pos = 0;
    private final List<String> inputLines = new ArrayList<>();
    private final Supplier<String> dynamicInputFunction;

    DynamicInputHandler(Supplier<String> func) {
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

    private int nextByte() {
        if (currentReader == null || pos >= currentReader.length) {
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
        SystemOutHandler.injectInput("> " + nextLine);
    }

    private void ejectNextInput() {
        String newInput = dynamicInputFunction.get();

        if (newInput == null) {
            return;
        }

        newInput = cleanText(newInput);
        inputLines.addAll(Arrays.asList(newInput.split("\n")));
    }
}
