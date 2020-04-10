package presentation;

import org.hyperskill.hstest.exception.outcomes.PresentationError;
import org.junit.Test;

import java.util.List;

import static org.hyperskill.hstest.testing.expect.Expectation.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestIntegers {

    @Test
    public void checkIntegers() {
        List<Integer> ints = expect("123 234 345").toContain(3).integers();

        assertEquals(ints.size(), 3);
        assertEquals((int) ints.get(0), 123);
        assertEquals((int) ints.get(1), 234);
        assertEquals((int) ints.get(2), 345);
    }

    @Test
    public void checkIntegersFail() {
        try {
            expect("123 234 ").toContain(4).integers();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong number of integers (expected to be equal to 4, found 2):\n" +
                    "\n" +
                    "123 234");
            return;
        }
        fail();
    }

    @Test
    public void checkIntegersInText() {
        List<Integer> ints = expect("qweqw 123 dsjfdhasd234 345fgfgnfg").toContain(3).integers();

        assertEquals(ints.size(), 3);
        assertEquals((int) ints.get(0), 123);
        assertEquals((int) ints.get(1), 234);
        assertEquals((int) ints.get(2), 345);
    }

    @Test
    public void checkIntegersOnly() {
        List<Integer> ints = expect("123 234 345").toContain(3).integersOnly();

        assertEquals(ints.size(), 3);
        assertEquals((int) ints.get(0), 123);
        assertEquals((int) ints.get(1), 234);
        assertEquals((int) ints.get(2), 345);
    }

    @Test
    public void checkIntegersOnlyInText() {
        try {
            expect("qweqw 123 dsjfdhasd234 345fgfgnfg").toContain(3).integersOnly();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains not only integers (but also \"qweqw\"):\n" +
                    "\n" +
                    "qweqw 123 dsjfdhasd234 345fgfgnfg");
            return;
        }
        fail();
    }

    @Test
    public void checkIntegersOnlyInText2() {
        try {
            expect("1 2 3 qweqw 123 dsjfdhasd234 345fgfgnfg").toContain(3).integersOnly();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains not only integers (but also \"qweqw\"):\n" +
                    "\n" +
                    "1 2 3 qweqw 123 dsjfdhasd234 345fgfgnfg");
            return;
        }
        fail();
    }

    @Test
    public void checkIntegersOnlyWthDelim() {
        List<Integer> ints = expect("123  :234: 345").toContain(3).integersOnly(":");

        assertEquals(ints.size(), 3);
        assertEquals((int) ints.get(0), 123);
        assertEquals((int) ints.get(1), 234);
        assertEquals((int) ints.get(2), 345);
    }

    @Test
    public void checkIntegersOnlyWthDelimFail() {
        try {
            expect("123  :234: 345").toContain(4).integersOnly(":");
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong " +
                    "number of integers separated by \":\" (expected to be equal to 4, found 3):\n" +
                "\n" +
                "123  :234: 345");
            return;
        }
        fail();
    }
}
