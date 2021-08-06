package org.hyperskill.hstest.dynamic.output;

import lombok.Getter;
import lombok.Setter;
import org.hyperskill.hstest.dynamic.security.ExitException;
import org.hyperskill.hstest.exception.testing.InfiniteLoopException;
import org.hyperskill.hstest.stage.StageTest;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class InfiniteLoopDetector {
    @Getter @Setter private static boolean working = true;

    @Getter @Setter private static boolean checkSameInputBetweenRequests = true;
    @Getter @Setter private static boolean checkNoInputRequestsForLong = false;
    @Getter @Setter private static boolean checkRepeatableOutput = true;

    private static final ByteArrayOutputStream currLine = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream sinceLastInput = new ByteArrayOutputStream();

    private static final List<String> betweenInputRequests = new ArrayList<>();
    private static final int BETWEEN_INPUT_SAVED_SIZE = 20;

    private static final int REPEATABLE_LINES_MAX = 20;

    private static final List<String> everyLine = new ArrayList<>();
    private static final int EVERY_LINE_SAVED_SIZE = 100;

    private static int charsSinceLastInput = 0;
    private static int linesSinceLastInput = 0;

    private static final int CHARS_SINCE_LAST_INPUT_MAX = 5000;
    private static final int LINES_SINCE_LAST_INPUT_MAX = 500;

    private static int charsSinceLastCheck = 0;
    private static final int CHARS_SINCE_LAST_CHECK_MAX = 100;

    static void write(int b) {
        if (!working) return;

        currLine.write(b);
        sinceLastInput.write(b);

        charsSinceLastInput++;
        charsSinceLastCheck++;

        if (b == '\n') {
            linesSinceLastInput++;
            everyLine.add(currLine.toString());
            currLine.reset();
            if (everyLine.size() > EVERY_LINE_SAVED_SIZE) {
                everyLine.remove(0);
            }
            checkInfLoopLines();

        }

        if (charsSinceLastCheck >= CHARS_SINCE_LAST_CHECK_MAX) {
            checkInfLoopChars();
            charsSinceLastCheck = 0;
        }
    }

    static void reset() {
        currLine.reset();
        charsSinceLastInput = 0;
        charsSinceLastCheck = 0;
        linesSinceLastInput = 0;
        betweenInputRequests.clear();
        everyLine.clear();
    }

    public static void inputRequested() {
        if (!working) return;

        betweenInputRequests.add(sinceLastInput.toString());
        if (betweenInputRequests.size() > BETWEEN_INPUT_SAVED_SIZE) {
            betweenInputRequests.remove(0);
            checkInfLoopInputRequests();
        }

        sinceLastInput.reset();
        everyLine.clear();
        charsSinceLastInput = 0;
        linesSinceLastInput = 0;
    }

    private static void checkInfLoopChars() {
        if (checkNoInputRequestsForLong && charsSinceLastInput >= CHARS_SINCE_LAST_INPUT_MAX) {
            fail("No input request for the last " + charsSinceLastInput + " characters being printed.");
        }
    }

    private static void checkInfLoopLines() {
        if (checkNoInputRequestsForLong && linesSinceLastInput >= LINES_SINCE_LAST_INPUT_MAX) {
            fail("No input request for the last " + linesSinceLastInput + " lines being printed.");
        }

        if (!checkRepeatableOutput) {
            return;
        }

        if (everyLine.size() != EVERY_LINE_SAVED_SIZE) {
            return;
        }

        nextRepetitionSize:
        for (int linesRepeated = 1; linesRepeated <= REPEATABLE_LINES_MAX; linesRepeated++) {
            int howManyRepetitions = everyLine.size() / linesRepeated;
            int linesToCheck = linesRepeated * howManyRepetitions;
            int startingFromIndex = everyLine.size() - linesToCheck;

            for (int shift = 0; shift < linesRepeated; shift++) {
                int initialIndex = startingFromIndex + shift;
                String toCompare = everyLine.get(initialIndex);

                for (int rep = 1; rep < howManyRepetitions; rep++) {
                    int currIndex = initialIndex + rep * linesRepeated;
                    String curr = everyLine.get(currIndex);
                    if (!toCompare.equals(curr)) {
                        continue nextRepetitionSize;
                    }
                }
            }

            if (linesRepeated == 1) {
                fail("Last " + linesToCheck + " lines your program printed are the same.");
            } else {
                fail("Last " + linesToCheck + " lines your program printed have "
                    + howManyRepetitions + " blocks of " + linesRepeated + " lines of the same text.");
            }
        }
    }

    private static void checkInfLoopInputRequests() {
        if (!checkSameInputBetweenRequests) {
            return;
        }

        String firstElem = betweenInputRequests.get(0);
        for (String curr : betweenInputRequests) {
            if (!curr.equals(firstElem)) {
                return;
            }
        }

        fail("Between the last " + BETWEEN_INPUT_SAVED_SIZE
            + " input requests the texts being printed are identical.");
    }

    private static void fail(String reason) {
        StageTest.getCurrTestRun().setErrorInTest(new InfiniteLoopException(reason));
        throw new ExitException(0);
    }
}
