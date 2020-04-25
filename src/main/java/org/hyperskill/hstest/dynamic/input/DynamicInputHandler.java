package org.hyperskill.hstest.dynamic.input;

import org.hyperskill.hstest.dynamic.output.SystemOutHandler;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.hyperskill.hstest.common.Utils.cleanText;

public class DynamicInputHandler {
    private final ThreadGroup group;

    private StringReader currentReader = new StringReader("");
    private final List<String> inputLines = new ArrayList<>();
    private final Function<String, String> dynamicInputFunction;

    DynamicInputHandler(ThreadGroup group, Function<String, String> func) {
        this.group = group;
        dynamicInputFunction = func;
    }

    int ejectChar() throws IOException {
        int character = currentReader.read();
        if (character == -1) {
            ejectNextLine();
            character = currentReader.read();
        }
        return character;
    }

    private void ejectNextLine() {
        if (inputLines.isEmpty()) {
            ejectNextInput();
            if (inputLines.isEmpty()) {
                return;
            }
        }
        String nextLine = inputLines.remove(0) + "\n";
        currentReader = new StringReader(nextLine);
        SystemOutHandler.injectInput("> " + nextLine);
    }

    private void ejectNextInput() {
        String currOutput = SystemOutHandler.getPartialOutput(group);
        String newInput = dynamicInputFunction.apply(currOutput);

        if (newInput == null) {
            return;
        }

        newInput = cleanText(newInput);
        inputLines.addAll(Arrays.asList(newInput.split("\n")));
    }
}
