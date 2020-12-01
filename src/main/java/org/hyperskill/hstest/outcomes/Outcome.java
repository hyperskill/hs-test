package org.hyperskill.hstest.outcomes;

import org.hyperskill.hstest.dynamic.output.SystemOutHandler;
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
        } else {
            whenErrorHappened = " in test #" + testNumber;
        }

        String result =
            getType() + whenErrorHappened;

        if (!errorText.isEmpty()) {
            result += "\n\n" + errorText.trim();
        }

        if (!stackTrace.isEmpty()) {
            result += "\n\n" + stackTrace.trim();
        }

        String fullLog = SystemOutHandler.getDynamicOutput();
        boolean worthToShowLog =
            fullLog.trim().length() != 0 && !result.contains(fullLog.trim());

        String arguments = "";
        TestRun testRun = StageTest.getCurrTestRun();
        if (testRun != null) {
            List<TestedProgram> testedPrograms = StageTest.getCurrTestRun().getTestedPrograms();
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

        if (worthToShowLog || arguments.length() > 0) {
            result += "\n\n";
            if (worthToShowLog) {
                result += "Please find below the output of your program during this failed test.\n";
                if (StageTest.getCurrTestRun().isInputUsed()) {
                    result += "Note that the '>' character indicates the beginning of the input line.\n";
                }
                result += "\n---\n\n";
            }

            if (arguments.length() > 0) {
                result += arguments + "\n\n";
            }

            if (worthToShowLog) {
                result += fullLog;
            }
        }

        return result.trim();
    }

    public static Outcome getOutcome(Throwable t, int currTest) {
        if (t instanceof WrongAnswer) {
            return new WrongAnswerOutcome(currTest, (WrongAnswer) t);

        } else if (t instanceof PresentationError) {
            return new PresentationErrorOutcome(currTest, (PresentationError) t);

        } else if (t instanceof ExceptionWithFeedback) {
            return new ExceptionOutcome(currTest, (ExceptionWithFeedback) t);

        } else if (t instanceof ErrorWithFeedback
            || t instanceof FileSystemException
            || t instanceof TimeLimitException
            || t instanceof NumberFormatException
            || t instanceof InfiniteLoopException) {

            return new ErrorOutcome(currTest, t);

        } else if (t instanceof UnexpectedError
            && t.getCause() instanceof NumberFormatException) {

            return new ErrorOutcome(currTest, t.getCause());

        } else {
            return new UnexpectedErrorOutcome(currTest, t);
        }
    }
}
