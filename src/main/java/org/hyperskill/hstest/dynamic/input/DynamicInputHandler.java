package org.hyperskill.hstest.dynamic.input;

import org.hyperskill.hstest.dynamic.output.InfiniteLoopDetector;
import org.hyperskill.hstest.dynamic.output.OutputHandler;
import org.hyperskill.hstest.dynamic.security.ExitException;
import org.hyperskill.hstest.exception.outcomes.OutOfInputError;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testing.Settings;

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
        if (character == -1 && !Settings.allowOutOfInput) {
            StageTest.getCurrTestRun().setErrorInTest(new ErrorWithFeedback(
                "Program ran out of input. You tried to read more than expected."));
            throw new ExitException(0);
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
        InfiniteLoopDetector.inputRequested();
    }

    private void ejectNextInput() {
        String newInput = dynamicInputFunction.get();

        if (newInput == null) {
            return;
        }

        newInput = cleanText(newInput);

        if (newInput.endsWith("\n")) {
            newInput = newInput.substring(0, newInput.length() - 1);
        }

        inputLines.addAll(Arrays.asList(newInput.split("\n", Integer.MAX_VALUE)));
    }
}
