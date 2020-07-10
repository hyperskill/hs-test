package presentation;

import org.hyperskill.hstest.exception.outcomes.PresentationError;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Locale;

import static org.hyperskill.hstest.testing.expect.Expectation.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestDoubles {

    @BeforeClass
    public static void setUp() {
        Locale.setDefault(Locale.US);
    }

    @Test
    public void checkDoubles() {
        List<Double> doubles = expect("123 234.234 345.567")
            .toContain(3).doubles();

        assertEquals(doubles.size(), 3);
        assertEquals(doubles.get(0), 123, 1e-4);
        assertEquals(doubles.get(1), 234.234, 1e-4);
        assertEquals(doubles.get(2), 345.567, 1e-4);
    }

    @Test
    public void checkDoublesAsText() {
        List<Double> doubles = expect("123 234.234 345.567").asText()
            .toContain(3).doubles();

        assertEquals(doubles.size(), 3);
        assertEquals(doubles.get(0), 123, 1e-4);
        assertEquals(doubles.get(1), 234.234, 1e-4);
        assertEquals(doubles.get(2), 345.567, 1e-4);
    }

    @Test
    public void checkDoublesFail() {
        try {
            expect("123.65 234.99 ").toContain(4).doubles();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong " +
                    "number of doubles (expected to be equal to 4, found 2):\n" +
                    "123.65 234.99");
            return;
        }
        fail();
    }

    @Test
    public void checkDoublesFailAsText() {
        try {
            expect("123.65 234.99 ").asText().toContain(4).doubles();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong " +
                    "number of doubles (expected to be equal to 4, found 2):\n" +
                    "123.65 234.99");
            return;
        }
        fail();
    }

    @Test
    public void checkDoublesInText() {
        List<Double> doubles = expect("qweqw 123.76 dsjfdhasd234.09 345.98fgfgnfg")
            .toContain(3).doubles();

        assertEquals(doubles.size(), 3);
        assertEquals(doubles.get(0), 123.76, 1e-4);
        assertEquals(doubles.get(1), 234.09, 1e-4);
        assertEquals(doubles.get(2), 345.98, 1e-4);
    }

    @Test
    public void checkDoublesInTextAsText() {
        List<Double> doubles = expect("qweqw 123.76 dsjfdhasd234.09 345.98fgfgnfg")
            .asText().toContain(3).doubles();

        assertEquals(doubles.size(), 3);
        assertEquals(doubles.get(0), 123.76, 1e-4);
        assertEquals(doubles.get(1), 234.09, 1e-4);
        assertEquals(doubles.get(2), 345.98, 1e-4);
    }

    @Test
    public void checkDoublesOnly() {
        List<Double> doubles = expect("123 234.234 345.567")
            .toContain(3).doublesOnly();

        assertEquals(doubles.size(), 3);
        assertEquals(doubles.get(0), 123, 1e-4);
        assertEquals(doubles.get(1), 234.234, 1e-4);
        assertEquals(doubles.get(2), 345.567, 1e-4);
    }

    @Test
    public void checkDoublesOnlyAsText() {
        List<Double> doubles = expect("123 234.234 345.567")
            .asText().toContain(3).doublesOnly();

        assertEquals(doubles.size(), 3);
        assertEquals(doubles.get(0), 123, 1e-4);
        assertEquals(doubles.get(1), 234.234, 1e-4);
        assertEquals(doubles.get(2), 345.567, 1e-4);
    }

    @Test
    public void checkDoublesOnlyInTextFail() {
        try {
            expect("qweqw 123.76 dsjfdhasd234.09 345.98fgfgnfg")
                .toContain(3).doublesOnly();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains not only doubles (but also \"qweqw\"):\n" +
                    "qweqw 123.76 dsjfdhasd234.09 345.98fgfgnfg");
            return;
        }
        fail();
    }

    @Test
    public void checkDoublesOnlyInTextFailAsText() {
        try {
            expect("qweqw 123.76 dsjfdhasd234.09 345.98fgfgnfg")
                .asText().toContain(3).doublesOnly();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains not only doubles (but also \"qweqw\"):\n" +
                    "qweqw 123.76 dsjfdhasd234.09 345.98fgfgnfg");
            return;
        }
        fail();
    }

    @Test
    public void checkDoublesOnlyInTextFail2() {
        try {
            expect("1.0 2e3 -3 qweqw 123 dsjfdhasd234 345fgfgnfg")
                .toContain(3).doublesOnly();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains not only doubles (but also \"qweqw\"):\n" +
                    "1.0 2e3 -3 qweqw 123 dsjfdhasd234 345fgfgnfg");
            return;
        }
        fail();
    }

    @Test
    public void checkDoublesOnlyInTextFail2asText() {
        try {
            expect("1.0 2e3 -3 qweqw 123 dsjfdhasd234 345fgfgnfg")
                .asText().toContain(3).doublesOnly();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains not only doubles (but also \"qweqw\"):\n" +
                    "1.0 2e3 -3 qweqw 123 dsjfdhasd234 345fgfgnfg");
            return;
        }
        fail();
    }

    @Test
    public void checkDoublesOnlyWthDelim() {
        List<Double> doubles = expect("123.456  =234e3= 345e-1")
            .toContain(3).doublesOnly("=");

        assertEquals(doubles.size(), 3);
        assertEquals(doubles.get(0), 123.456, 1e-4);
        assertEquals(doubles.get(1), 234e3, 1e-4);
        assertEquals(doubles.get(2), 345e-1, 1e-4);
    }

    @Test
    public void checkDoublesOnlyWthDelimAsText() {
        List<Double> doubles = expect("123.456  =234e3= 345e-1")
            .asText().toContain(3).doublesOnly("=");

        assertEquals(doubles.size(), 3);
        assertEquals(doubles.get(0), 123.456, 1e-4);
        assertEquals(doubles.get(1), 234e3, 1e-4);
        assertEquals(doubles.get(2), 345e-1, 1e-4);
    }

    @Test
    public void checkDoublesOnlyWthDelimFail() {
        try {
            expect("123.456  =234e3= 345e-1")
                .toContain(1).doublesOnly("=");
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong " +
                    "number of doubles separated by \"=\" (expected to be equal to 1, found 3):\n" +
                    "123.456  =234e3= 345e-1");
            return;
        }
        fail();
    }

    @Test
    public void checkDoublesOnlyWthDelimFailAsText() {
        try {
            expect("123.456  =234e3= 345e-1").asText()
                .toContain(1).doublesOnly("=");
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong " +
                    "number of doubles separated by \"=\" (expected to be equal to 1, found 3):\n" +
                    "123.456  =234e3= 345e-1");
            return;
        }
        fail();
    }
}
