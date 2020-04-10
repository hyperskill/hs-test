package presentation;

import org.hyperskill.hstest.exception.outcomes.PresentationError;
import org.junit.Test;

import static org.hyperskill.hstest.testing.expect.Expectation.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestAmount {

    @Test
    public void checkAny1() {
        try {
            expect("").toContain().integers();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "Since the last input no output was printed, but should.");
            return;
        }
        fail();
    }

    @Test
    public void checkAny2() {
        expect("123").toContain().integers();
    }

    @Test
    public void checkExact1() {
        try {
            expect("123 987 765 654 987 675").toContain(5).integers();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains " +
                    "wrong number of integers (expected to be equal to 5, found 6):\n" +
                    "\n" +
                    "123 987 765 654 987 675");
            return;
        }
        fail();
    }

    @Test
    public void checkExact2() {
        expect("123 987 765 654 987 675").toContain(6).integers();
    }

    @Test
    public void checkExact3() {
        try {
            expect("123 987 765 654 987 675").toContain(7).integers();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains " +
                    "wrong number of integers (expected to be equal to 7, found 6):\n" +
                    "\n" +
                    "123 987 765 654 987 675");
            return;
        }
        fail();
    }

    @Test
    public void checkMore1() {
        expect("123 987 765 654 987 675").toContainMoreThan(5).integers();
    }

    @Test
    public void checkMore2() {
        try {
            expect("123 987 765 654 987 675").toContainMoreThan(6).integers();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong " +
                    "number of integers (expected to be more than 6, found 6):\n" +
                    "\n" +
                    "123 987 765 654 987 675");
            return;
        }
        fail();
    }

    @Test
    public void checkMore3() {
        try {
            expect("123 987 765 654 987 675").toContainMoreThan(7).integers();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong " +
                    "number of integers (expected to be more than 7, found 6):\n" +
                    "\n" +
                    "123 987 765 654 987 675");
            return;
        }
        fail();
    }

    @Test
    public void checkLess1() {
        try {
            expect("123 987 765 654 987 675").toContainLessThan(5).integers();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong " +
                    "number of integers (expected to be less than 5, found 6):\n" +
                    "\n" +
                    "123 987 765 654 987 675");
            return;
        }
        fail();
    }

    @Test
    public void checkLess2() {
        try {
            expect("123 987 765 654 987 675").toContainLessThan(6).integers();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong " +
                    "number of integers (expected to be less than 6, found 6):\n" +
                    "\n" +
                    "123 987 765 654 987 675");
            return;
        }
        fail();
    }

    @Test
    public void checkLess3() {
        expect("123 987 765 654 987 675").toContainLessThan(7).integers();
    }

    @Test
    public void checkAtLeast1() {
        expect("123 987 765 654 987 675").toContainAtLeast(5).integers();
    }

    @Test
    public void checkAtLeast2() {
        expect("123 987 765 654 987 675").toContainAtLeast(6).integers();
    }

    @Test
    public void checkAtLeast3() {
        try {
            expect("123 987 765 654 987 675").toContainAtLeast(7).integers();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong " +
                    "number of integers (expected to be at least 7, found 6):\n" +
                    "\n" +
                    "123 987 765 654 987 675");
            return;
        }
        fail();
    }

    @Test
    public void checkAtMost1() {
        try {
            expect("123 987 765 654 987 675").toContainAtMost(5).integers();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong " +
                    "number of integers (expected to be 5 at most, found 6):\n" +
                    "\n" +
                    "123 987 765 654 987 675");
            return;
        }
        fail();
    }

    @Test
    public void checkAtMost2() {
        expect("123 987 765 654 987 675").toContainAtMost(6).integers();
    }

    @Test
    public void checkAtMost3() {
        expect("123 987 765 654 987 675").toContainAtMost(7).integers();
    }

    @Test
    public void checkBetween1() {
        try {
            expect("123 987 765 654 987 675").toContainBetween(3, 5).integers();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong " +
                    "number of integers (expected to be between 3 and 5, found 6):\n" +
                    "\n" +
                    "123 987 765 654 987 675");
            return;
        }
        fail();
    }

    @Test
    public void checkBetween2() {
        expect("123 987 765 654 987 675").toContainBetween(3, 6).integers();
    }

    @Test
    public void checkBetween3() {
        expect("123 987 765 654 987 675").toContainBetween(3, 7).integers();
    }

    @Test
    public void checkBetween4() {
        expect("123 987 765 654 987 675").toContainBetween(5, 9).integers();
    }

    @Test
    public void checkBetween5() {
        expect("123 987 765 654 987 675").toContainBetween(6, 9).integers();
    }

    @Test
    public void checkBetween6() {
        try {
            expect("123 987 765 654 987 675").toContainBetween(7, 9).integers();
        } catch (PresentationError ex) {
            assertEquals(ex.getFeedbackText(),
                "The following output contains wrong " +
                    "number of integers (expected to be between 7 and 9, found 6):\n" +
                    "\n" +
                    "123 987 765 654 987 675");
            return;
        }
        fail();
    }
}
