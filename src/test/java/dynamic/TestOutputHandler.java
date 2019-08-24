package dynamic;

import org.hyperskill.hstest.dev.dynamic.output.OutputStreamHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestOutputHandler {

    @Before
    public void setUp() throws Exception {
        OutputStreamHandler.replaceSystemOut();
    }

    @After
    public void tearDown() {
        OutputStreamHandler.revertSystemOut();
    }

    @Test
    public void testNormalOutputHandler() {
        System.out.print("123");
        assertEquals("123", OutputStreamHandler.getOutput());
        System.out.print("456");
        assertEquals("123456", OutputStreamHandler.getOutput());
    }

    @Test
    public void testResetOutputHandler() {
        System.out.print("123");
        OutputStreamHandler.resetOutput();
        assertEquals("", OutputStreamHandler.getOutput());
        System.out.print("456");
        assertEquals("456", OutputStreamHandler.getOutput());
    }

    @Test
    public void testRevertOutputHandler() {
        System.out.print("123");
        OutputStreamHandler.revertSystemOut();
        assertEquals("", OutputStreamHandler.getOutput());
        System.out.print("456");
        assertEquals("", OutputStreamHandler.getOutput());
    }

}
