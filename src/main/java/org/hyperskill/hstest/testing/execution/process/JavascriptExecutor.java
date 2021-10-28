package org.hyperskill.hstest.testing.execution.process;

import org.hyperskill.hstest.testing.execution.ProcessExecutor;
import org.hyperskill.hstest.testing.execution.runnable.RunnableFile;
import org.hyperskill.hstest.testing.execution.searcher.JavascriptSearcher;

import java.util.ArrayList;
import java.util.List;

public class JavascriptExecutor extends ProcessExecutor {
    public JavascriptExecutor(String sourceName) {
        super(new JavascriptSearcher().find(sourceName));
    }

    @Override
    protected List<String> executionCommand(List<String> args) {
        List<String> fullArgs = new ArrayList<>();
        fullArgs.addAll(List.of("node", runnable.getFile().getAbsolutePath()));
        fullArgs.addAll(args);

        return fullArgs;
    }
}
