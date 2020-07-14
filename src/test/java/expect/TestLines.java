package expect;

import org.hyperskill.hstest.exception.outcomes.PresentationError;
import org.junit.Test;

import java.util.List;

import static org.hyperskill.hstest.testing.expect.Expectation.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestLines {
    @Test
    public void checkLines1() {
        List<String> words = expect("qee\n234\nert").toContain(3).lines();

        assertEquals(words.size(), 3);
        assertEquals(words.get(0), "qee");
        assertEquals(words.get(1), "234");
        assertEquals(words.get(2), "ert");
    }

    @Test
    public void checkLines1asText() {
        List<String> words = expect("qee\n234\nert").asText().toContain(3).lines();

        assertEquals(words.size(), 3);
        assertEquals(words.get(0), "qee");
        assertEquals(words.get(1), "234");
        assertEquals(words.get(2), "ert");
    }

    @Test
    public void checkLines2() {
        List<String> words = expect("\nqee\n234\nert\n").toContain(3).lines();

        assertEquals(words.size(), 3);
        assertEquals(words.get(0), "qee");
        assertEquals(words.get(1), "234");
        assertEquals(words.get(2), "ert");
    }

    @Test
    public void checkLines2asText() {
        List<String> words = expect("\nqee\n234\nert\n").asText().toContain(3).lines();

        assertEquals(words.size(), 3);
        assertEquals(words.get(0), "qee");
        assertEquals(words.get(1), "234");
        assertEquals(words.get(2), "ert");
    }

    @Test
    public void checkLinesFail() {
        try {
            expect("123 234 345").toContain(4).lines();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong " +
                    "number of lines (expected to be equal to 4, found 1):\n" +
                    "123 234 345");
            return;
        }
        fail();
    }

    @Test
    public void checkLinesFailAsText() {
        try {
            expect("123 234 345").asText().toContain(4).lines();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong " +
                    "number of lines (expected to be equal to 4, found 1):\n" +
                    "123 234 345");
            return;
        }
        fail();
    }
}
