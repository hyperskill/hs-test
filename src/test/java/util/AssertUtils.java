package util;

import org.hyperskill.hstest.exception.outcomes.OutcomeError;
import org.hyperskill.hstest.exception.outcomes.PresentationError;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.junit.Assert;

import static org.junit.Assert.fail;

public class AssertUtils {

    public static void assertThrows(Runnable r, String feedback) {
        assertThrows(r, WrongAnswer.class, feedback);
    }

    public static <T extends Class<? extends OutcomeError>>
    void assertThrows(Runnable r, T outcomeClass, String feedback) {
        try {
            r.run();
            fail("An exception " + outcomeClass.getSimpleName() + " should be thrown");
        } catch (OutcomeError ex) {
            if (ex.getClass() == outcomeClass) {
                if (ex instanceof WrongAnswer) {
                    Assert.assertTrue(
                        ((WrongAnswer) ex).getFeedbackText().startsWith(feedback)
                    );
                } else if (ex instanceof PresentationError) {
                    Assert.assertTrue(
                        ((PresentationError) ex).getFeedbackText().startsWith(feedback)
                    );
                } else {
                    fail("Unknown genetic");
                }
            } else {
                throw ex;
            }
        }
    }

}
