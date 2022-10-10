package org.hyperskill.hstest.testing.execution.process;

import org.hyperskill.hstest.testing.execution.ProcessExecutor;
import org.hyperskill.hstest.testing.execution.searcher.ShellSearcher;

import java.util.ArrayList;
import java.util.List;

public class ShellExecutor extends ProcessExecutor {

    public ShellExecutor(String sourceName) {
        super(new ShellSearcher().find(sourceName));
    }

    @Override
    protected List<String> executionCommand(List<String> args) {
        List<String> fullArgs = new ArrayList<>();
        fullArgs.addAll(List.of("bash", runnable.getFile().getName()));
        fullArgs.addAll(args);

        return fullArgs;
    }

}
