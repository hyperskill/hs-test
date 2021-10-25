package org.hyperskill.hstest.testing.execution.process;

import org.hyperskill.hstest.testing.execution.ProcessExecutor;
import org.hyperskill.hstest.testing.execution.searcher.GoSearcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hyperskill.hstest.common.FileUtils.abspath;
import static org.hyperskill.hstest.common.OsUtils.isWindows;

public class GoExecutor extends ProcessExecutor {
    private final String executable;
    private final String filename;

    protected GoExecutor(String sourceName) {
        super(new GoSearcher().find(sourceName));

        var fileName = runnable.getFile().getName();

        var withoutGo = fileName
            .substring(0, fileName.length() - new GoSearcher().extension().length());

        if (isWindows()) {
            executable = withoutGo;
            filename = executable + ".exe";
        } else {
            executable = "./" + withoutGo;
            filename = withoutGo;
        }
    }

    @Override
    protected List<String> compilationCommand() {
        return List.of("go", "build", runnable.getFile().getAbsolutePath());
    }

    @Override
    protected String filterCompilationError(String error) {
        return error.lines().filter(line -> !line.startsWith("#")).collect(Collectors.joining("\n"));
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
