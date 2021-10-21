package dynamic;

import org.hyperskill.hstest.dynamic.SystemHandler;
import org.hyperskill.hstest.dynamic.output.OutputHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestOutputHandler {

    @Before
    public void setUp() {
        SystemHandler.setUp();
        OutputHandler.installOutputHandler(null, () -> true);
    }

    @After
    public void tearDown() {
        OutputHandler.uninstallOutputHandler(null);
        SystemHandler.tearDownSystem();
    }

    @Test
    public void testNormalOutputHandler() {
        System.out.print("123");
        assertEquals("123", OutputHandler.getOutput());
        System.out.print("456");
        assertEquals("123456", OutputHandler.getOutput());
    }

    @Test
    public void testNormalCodepoints() {
        String a = "ǿ˿Ͽ";
        String b = "ꇿꋿꏿ";
        System.out.print(a);
        assertEquals(a, OutputHandler.getOutput());
        System.out.print(b);
        assertEquals(a + b, OutputHandler.getOutput());
    }

    @Test
    public void testNormalOutputHandlerWithNewLines() {
        System.out.println("123");
        assertEquals("123\n", OutputHandler.getOutput());
        System.out.println("456");
        assertEquals("123\n456\n", OutputHandler.getOutput());
    }

    @Test
    public void testCodepointsWithNewLines() {
        String a = "ǿ˿Ͽ";
        String b = "ꇿꋿꏿ";
        System.out.println(a);
        assertEquals(a + "\n", OutputHandler.getOutput());
        System.out.println(b);
        assertEquals(a + "\n" + b + "\n", OutputHandler.getOutput());
    }

    @Test
    public void testResetOutputHandler() {
        System.out.print("123");
        OutputHandler.resetOutput();
        assertEquals("", OutputHandler.getOutput());
        System.out.print("456");
        assertEquals("456", OutputHandler.getOutput());
    }

    @Test
    public void testRevertOutputHandler() {
        System.out.print("123");
        OutputHandler.revertOutput();
        System.out.print("123");
        assertEquals("", OutputHandler.getOutput());
    }
}
