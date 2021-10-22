package org.hyperskill.hstest.testing.execution.process;

import org.hyperskill.hstest.testing.execution.ProcessExecutor;
import org.hyperskill.hstest.testing.execution.runnable.RunnableFile;

import java.util.List;

public class GoExecutor extends ProcessExecutor {
    protected GoExecutor(RunnableFile source) {
        super(source);
    }

    @Override
    protected List<String> executionCommand(List<String> args) {
        return null;
    }
}
