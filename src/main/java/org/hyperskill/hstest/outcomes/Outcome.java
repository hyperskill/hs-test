package org.hyperskill.hstest.outcomes;

import org.hyperskill.hstest.common.Utils;
import org.hyperskill.hstest.dynamic.output.OutputHandler;
import org.hyperskill.hstest.exception.outcomes.CompilationError;
import org.hyperskill.hstest.exception.outcomes.ErrorWithFeedback;
import org.hyperskill.hstest.exception.outcomes.ExceptionWithFeedback;
import org.hyperskill.hstest.exception.outcomes.PresentationError;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.exception.testing.InfiniteLoopException;
import org.hyperskill.hstest.exception.testing.TimeLimitException;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testing.TestRun;
import org.hyperskill.hstest.testing.TestedProgram;

import java.nio.file.FileSystemException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Outcome {

    int testNumber;
    String errorText;
    String stackTrace;

    public Outcome() {
        this(0, "", "");
    }

    public Outcome(int testNumber, String errorText, String stackTrace) {
        this.testNumber = testNumber;
        this.errorText = errorText;
        this.stackTrace = stackTrace;
    }

    protected abstract String getType();

    @Override
    public final String toString() {

        String whenErrorHappened;
        if (testNumber == 0) {
            whenErrorHappened = " during testing";
        } else if (testNumber > 0) {
            whenErrorHappened = " in test #" + testNumber;
        } else {
            whenErrorHappened = "";
        }

        String result = getType() + whenErrorHappened;

        if (!errorText.isEmpty()) {
            result += "\n\n" + Utils.cleanText(errorText.trim());
        }

        if (!stackTrace.isEmpty()) {
            result += "\n\n" + Utils.cleanText(stackTrace.trim());
        }

        String fullOut = OutputHandler.getDynamicOutput();
        String fullErr = OutputHandler.getErr();
        String arguments = getArguments();
        String trimmedOut = trimLines(fullOut);
        String trimmedErr = trimLines(fullErr);

        boolean worthShowingErr = fullErr.length() > 0 && !result.contains(fullErr.trim());
        boolean worthShowingArguments = arguments.length() > 0;
        boolean worthShowingOut =
            fullOut.trim().length() != 0 && !result.contains(fullOut.trim());

        TestRun testRun = StageTest.getCurrTestRun();

        if (worthShowingOut || worthShowingErr || worthShowingArguments) {
            result += "\n\n";
            if (worthShowingOut || worthShowingErr) {
                result += "Please find below the output of your program during this failed test.\n";
                if (testRun != null && testRun.isInputUsed()) {
                    result += "Note that the '>' character indicates the beginning of the input line.\n";
                }
                result += "\n---\n\n";
            }

            if (worthShowingArguments) {
                result += arguments + "\n\n";
            }

            if (worthShowingOut) {
                if (worthShowingErr) {
                    result += "stdout:\n";
                }
                result += trimmedOut + "\n\n";
            }

            if (worthShowingErr) {
                result += "stderr:\n" + trimmedErr;
            }
        }

        return result.trim();
    }

    private String getArguments() {
        String arguments = "";
        TestRun testRun = StageTest.getCurrTestRun();
        if (testRun != null) {
            List<TestedProgram> testedPrograms = testRun.getTestedPrograms();
            List<TestedProgram> programsWithArgs = testedPrograms
                .stream().filter(pr -> pr.getRunArgs().size() > 0).collect(Collectors.toList());

            StringBuilder argumentsBuilder = new StringBuilder();
            for (TestedProgram pr : programsWithArgs) {
                argumentsBuilder.append("Arguments");
                if (testedPrograms.size() > 1) {
                    argumentsBuilder
                        .append(" for ")
                        .append(pr.toString());
                }
                List<String> prArgs = pr.getRunArgs().stream()
                    .map(arg -> arg.contains(" ") ? "\"" + arg + "\"" : arg)
                    .collect(Collectors.toList());
                argumentsBuilder
                    .append(": ")
                    .append(String.join(" ", prArgs))
                    .append("\n");
            }
            arguments = argumentsBuilder.toString().trim();
        }
        return arguments;
    }

    private String trimLines(String output) {
        String result = "";

        int MAX_LINES_IN_OUTPUT = 250;
        String[] lines = output.split("\n");
        boolean isOutputTooLong = lines.length > MAX_LINES_IN_OUTPUT;

        if (isOutputTooLong) {
            result += "[last " + MAX_LINES_IN_OUTPUT + " lines of output are shown, "
                + (lines.length - MAX_LINES_IN_OUTPUT) + " skipped]\n";
            String[] lastLines =
                Arrays.copyOfRange(lines, lines.length - MAX_LINES_IN_OUTPUT, lines.length);
            result += String.join("\n", lastLines);
        } else {
            result += output;
        }

        return result.trim();
    }

    public static Outcome getOutcome(Throwable ex, int currTest) {
        if (ex instanceof WrongAnswer) {
            return new WrongAnswerOutcome(currTest, (WrongAnswer) ex);

        } else if (ex instanceof PresentationError) {
            return new PresentationErrorOutcome(currTest, (PresentationError) ex);

        } else if (ex instanceof ExceptionWithFeedback) {
            return new ExceptionOutcome(currTest, (ExceptionWithFeedback) ex);

        } else if (ex instanceof CompilationError) {
            return new CompilationErrorOutcome((CompilationError) ex);

        } else if (ex instanceof ErrorWithFeedback
            || ex instanceof FileSystemException
            || ex instanceof TimeLimitException
            || ex instanceof NumberFormatException
            || ex instanceof InfiniteLoopException) {

            return new ErrorOutcome(currTest, ex);

        } else if (ex instanceof UnexpectedError
            && ex.getCause() instanceof NumberFormatException) {

            return new ErrorOutcome(currTest, ex.getCause());

        } else {
            return new UnexpectedErrorOutcome(currTest, ex);
        }
    }
}
