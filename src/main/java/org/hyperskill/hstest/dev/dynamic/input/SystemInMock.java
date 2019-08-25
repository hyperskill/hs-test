package org.hyperskill.hstest.dev.dynamic.input;

import org.hyperskill.hstest.dev.dynamic.output.SystemOutHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static org.hyperskill.hstest.dev.common.Utils.normalizeLineEndings;

public class SystemInMock extends InputStream {
    private StringReader currentReader;
    private List<String> inputLines = new LinkedList<>();
    private List<Function<String, String>> inputTextFuncs = new LinkedList<>();

    private boolean firstTime = false;
    private boolean needInject = false;
    private String injectionString;

    void provideText(String text) {
        currentReader = new StringReader(text);
    }

    void setTexts(List<Function<String, String>> texts) {
        inputTextFuncs = texts;
        inputLines.clear();
        firstTime = true;
        needInject = false;
        currentReader = new StringReader("");
    }

    @Override
    public int read() throws IOException {
        int character = currentReader.read();

        if (needInject) {
            needInject = false;
            SystemOutHandler.injectInput(">" + injectionString);
        }

        if (character == -1) {

            if (!inputLines.isEmpty()) {
                String nextLine = inputLines.remove(0) + "\n";
                currentReader = new StringReader(nextLine);
                injectionString = nextLine;
                needInject = true;
                if (firstTime) {
                    firstTime = false;
                    return read();
                }
                return -1;
            }

            if (!inputTextFuncs.isEmpty()) {
                String currOutput = SystemOutHandler.getDynamicOutput();
                Function<String, String> nextFunc = inputTextFuncs.remove(0);
                String newInput = nextFunc.apply(currOutput);
                newInput = normalizeLineEndings(newInput).trim();
                inputLines.addAll(Arrays.asList(newInput.split("\n")));
                return read();
            }

            return -1;
        }
        return character;
    }

}
