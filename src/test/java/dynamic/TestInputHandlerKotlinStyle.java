package dynamic;

import org.hyperskill.hstest.v7.dynamic.SystemHandler;
import org.hyperskill.hstest.v7.dynamic.input.SystemInHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hyperskill.hstest.v7.dynamic.input.KotlinInput.readLine;
import static org.junit.Assert.assertEquals;

public class TestInputHandlerKotlinStyle {

    @Before
    public void setUp() throws Exception {
        SystemHandler.setUpSystem();
    }

    @After
    public void tearDown() {
        SystemHandler.tearDownSystem();
    }

    @Test
    public void testLines() throws IOException {
        SystemInHandler.setInput("123\n456\n");
        assertEquals("123", readLine());
        assertEquals("456", readLine());
    }

    @Test
    public void testLinesWithoutNewLineAtEnd() throws IOException {
        SystemInHandler.setInput("qwerty\nsecond");
        assertEquals("qwerty", readLine());
        assertEquals("second", readLine());
    }

    @Test
    public void testLinesWithSpaces() throws IOException {
        SystemInHandler.setInput(" 123  \n   456    \n");
        assertEquals(" 123  ", readLine());
        assertEquals("   456    ", readLine());
    }

    @Test
    public void testSingleLine() throws IOException {
        SystemInHandler.setInput("qwerty\n");
        assertEquals("qwerty", readLine());
    }

    @Test
    public void testSingleLineWithoutNewLine() throws IOException {
        SystemInHandler.setInput("qwerty123");
        assertEquals("qwerty123", readLine());
    }

}
