package org.hyperskill.hstest.testing.execution.process;

import org.hyperskill.hstest.testing.execution.ProcessExecutor;
import org.hyperskill.hstest.testing.execution.searcher.CppSearcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hyperskill.hstest.common.FileUtils.abspath;
import static org.hyperskill.hstest.common.OsUtils.isWindows;

/**
 * Executes C++ runnable files
 * (files with main function)
 * in the given directory.
 *
 */
public class CppExecutor extends ProcessExecutor {
    private final String executable;
    private final String filename;

    public CppExecutor(String sourceName) {
        super(new CppSearcher().find(sourceName));

        var fileName = runnable.getFile().getName();

        var withoutCpp = fileName
                .substring(0, fileName.length() - new CppSearcher().extension().length());

        if (isWindows()) {
            executable = withoutCpp;
            filename = executable + ".exe";
        } else {
            executable = "./" + withoutCpp;
            filename = withoutCpp;
        }
    }

    @Override
    protected List<String> compilationCommand() {
        return List.of("g++", "-o", filename, runnable.getFile().getName());
    }

    @Override
    protected String filterCompilationError(String error) {
        // Adapt error filtering if needed
        return error;
    }

    @Override
    protected List<String> executionCommand(List<String> args) {
        List<String> fullArgs = new ArrayList<>();
        fullArgs.add(executable);
        fullArgs.addAll(args);

        return fullArgs;
    }

    @Override
    protected void cleanup() {
        try {
            Files.deleteIfExists(Paths.get(abspath(filename)));
        } catch (IOException ignored) { }
    }
}

