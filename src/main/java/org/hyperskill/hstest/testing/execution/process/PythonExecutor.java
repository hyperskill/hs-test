package org.hyperskill.hstest.testing.execution.process;

import org.hyperskill.hstest.testing.execution.ProcessExecutor;
import org.hyperskill.hstest.testing.execution.runnable.RunnableFile;
import org.hyperskill.hstest.testing.execution.searcher.PythonSearcher;

import java.util.ArrayList;
import java.util.List;

public class PythonExecutor extends ProcessExecutor {
    public PythonExecutor(String sourceName) {
        super(new PythonSearcher().find(sourceName));
    }

    @Override
    protected List<String> executionCommand(List<String> args) {
        List<String> fullArgs = new ArrayList<>();
        fullArgs.addAll(List.of("python", "-u", runnable.getFile().getAbsolutePath()));
        fullArgs.addAll(args);

        return fullArgs;
    }
}
