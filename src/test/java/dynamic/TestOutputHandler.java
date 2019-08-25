package dynamic;

import org.hyperskill.hstest.dev.dynamic.output.SystemOutHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestOutputHandler {

    @Before
    public void setUp() throws Exception {
        SystemOutHandler.replaceSystemOut();
    }

    @After
    public void tearDown() {
        SystemOutHandler.revertSystemOut();
    }

    @Test
    public void testNormalOutputHandler() {
        System.out.print("123");
        assertEquals("123", SystemOutHandler.getOutput());
        System.out.print("456");
        assertEquals("123456", SystemOutHandler.getOutput());
    }

    @Test
    public void testResetOutputHandler() {
        System.out.print("123");
        SystemOutHandler.resetOutput();
        assertEquals("", SystemOutHandler.getOutput());
        System.out.print("456");
        assertEquals("456", SystemOutHandler.getOutput());
    }

    @Test
    public void testRevertOutputHandler() {
        System.out.print("123");
        SystemOutHandler.revertSystemOut();
        assertEquals("", SystemOutHandler.getOutput());
        System.out.print("456");
        assertEquals("", SystemOutHandler.getOutput());
    }

}
