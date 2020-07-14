package expect;

import org.hyperskill.hstest.exception.outcomes.PresentationError;
import org.junit.Test;

import java.util.List;

import static org.hyperskill.hstest.testing.expect.Expectation.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestWords {

    @Test
    public void checkWords() {
        List<String> words = expect("qee 234 ert").toContain(3).words();

        assertEquals(words.size(), 3);
        assertEquals(words.get(0), "qee");
        assertEquals(words.get(1), "234");
        assertEquals(words.get(2), "ert");
    }

    @Test
    public void checkWordsAsText() {
        List<String> words = expect("qee 234 ert").asText().toContain(3).words();

        assertEquals(words.size(), 3);
        assertEquals(words.get(0), "qee");
        assertEquals(words.get(1), "234");
        assertEquals(words.get(2), "ert");
    }

    @Test
    public void checkWordsFail() {
        try {
            expect("123 234 345").toContain(4).words();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong " +
                    "number of words (expected to be equal to 4, found 3):\n" +
                    "123 234 345");
            return;
        }
        fail();
    }

    @Test
    public void checkWordsFailAsText() {
        try {
            expect("123 234 345").asText().toContain(4).words();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong " +
                    "number of words (expected to be equal to 4, found 3):\n" +
                    "123 234 345");
            return;
        }
        fail();
    }

    @Test
    public void checkWordsWithDelim() {
        List<String> words = expect("qweqw,  123 , dsjfdhasd234 ,345fgfgnfg")
            .toContain(4).words(",");

        assertEquals(words.size(), 4);
        assertEquals(words.get(0), "qweqw");
        assertEquals(words.get(1), "123");
        assertEquals(words.get(2), "dsjfdhasd234");
        assertEquals(words.get(3), "345fgfgnfg");
    }

    @Test
    public void checkWordsWithDelimAsText() {
        List<String> words = expect("qweqw,  123 , dsjfdhasd234 ,345fgfgnfg")
            .asText().toContain(4).words(",");

        assertEquals(words.size(), 4);
        assertEquals(words.get(0), "qweqw");
        assertEquals(words.get(1), "123");
        assertEquals(words.get(2), "dsjfdhasd234");
        assertEquals(words.get(3), "345fgfgnfg");
    }

    @Test
    public void checkWordsWithDelimFail() {
        try {
            expect("qweqw,  123 , dsjfdhasd234 ,345fgfgnfg")
                .toContain(1).words(",");
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong number " +
                    "of words separated by \",\" (expected to be equal to 1, found 4):\n" +
                    "qweqw,  123 , dsjfdhasd234 ,345fgfgnfg");
            return;
        }
        fail();
    }

    @Test
    public void checkWordsWithDelimFailAsText() {
        try {
            expect("qweqw,  123 , dsjfdhasd234 ,345fgfgnfg")
                .asText().toContain(1).words(",");
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong number " +
                    "of words separated by \",\" (expected to be equal to 1, found 4):\n" +
                    "qweqw,  123 , dsjfdhasd234 ,345fgfgnfg");
            return;
        }
        fail();
    }

    @Test
    public void checkWordsFrom() {
        List<String> words = expect("yes 4324 23 123 324 2no 564yes 345")
            .toContain(3).wordsFrom("yes", "no");

        assertEquals(words.size(), 3);
        assertEquals(words.get(0), "yes");
        assertEquals(words.get(1), "no");
        assertEquals(words.get(2), "yes");
    }

    @Test
    public void checkWordsFromAsText() {
        List<String> words = expect("yes 4324 23 123 324 2no 564yes 345")
            .asText().toContain(3).wordsFrom("yes", "no");

        assertEquals(words.size(), 3);
        assertEquals(words.get(0), "yes");
        assertEquals(words.get(1), "no");
        assertEquals(words.get(2), "yes");
    }

    @Test
    public void checkWordsFromFail() {
        try {
            expect("yes 4324 23 123 324 2no 564yes 345")
                .toContain(6).wordsFrom("yes", "no");
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong " +
                    "number of specific words (expected to be equal to 6, found 3):\n" +
                    "yes 4324 23 123 324 2no 564yes 345");
            return;
        }
        fail();
    }

    @Test
    public void checkWordsFromFailAsText() {
        try {
            expect("yes 4324 23 123 324 2no 564yes 345")
                .asText().toContain(6).wordsFrom("yes", "no");
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong " +
                    "number of specific words (expected to be equal to 6, found 3):\n" +
                    "yes 4324 23 123 324 2no 564yes 345");
            return;
        }
        fail();
    }

    @Test
    public void checkWordsOnlyFrom() {
        List<String> words = expect("yes no yes")
            .toContain(3).wordsOnlyFrom("yes", "no");

        assertEquals(words.size(), 3);
        assertEquals(words.get(0), "yes");
        assertEquals(words.get(1), "no");
        assertEquals(words.get(2), "yes");
    }

    @Test
    public void checkWordsOnlyFromAsText() {
        List<String> words = expect("yes no yes")
            .asText().toContain(3).wordsOnlyFrom("yes", "no");

        assertEquals(words.size(), 3);
        assertEquals(words.get(0), "yes");
        assertEquals(words.get(1), "no");
        assertEquals(words.get(2), "yes");
    }

    @Test
    public void checkWordsOnlyFromFail() {
        try {
            expect("yes 43 no 24 yes")
                .toContain(3).wordsOnlyFrom("yes", "no");
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains not only specific words (but also \"43\"):\n" +
                    "yes 43 no 24 yes");
            return;
        }
        fail();
    }

    @Test
    public void checkWordsOnlyFromFailAsText() {
        try {
            expect("yes 43 no 24 yes")
                .asText().toContain(3).wordsOnlyFrom("yes", "no");
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains not only specific words (but also \"43\"):\n" +
                    "yes 43 no 24 yes");
            return;
        }
        fail();
    }

}
