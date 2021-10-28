package org.hyperskill.hstest.testing.execution.process;

import org.hyperskill.hstest.testing.execution.ProcessExecutor;
import org.hyperskill.hstest.testing.execution.runnable.RunnableFile;

import java.util.List;

public class JavaExecutor extends ProcessExecutor {

    public JavaExecutor(RunnableFile runnable) {
        super(runnable);
    }

    @Override
    protected List<String> executionCommand(List<String> args) {
        return null;
    }
}
