package dynamic;

import org.hyperskill.hstest.v6.dynamic.SystemHandler;
import org.hyperskill.hstest.v6.dynamic.input.SystemInHandler;
import org.hyperskill.hstest.v6.dynamic.output.SystemOutHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.assertEquals;


public class TestDynamicOutput {

    Scanner scanner;

    @Before
    public void setUp() throws Exception {
        SystemHandler.setUpSystem();
        scanner = new Scanner(System.in);
    }

    @After
    public void tearDown() {
        SystemHandler.tearDownSystem();
    }

    @Test
    public void testNormalOutputHandler() {
        System.out.print("123");
        assertEquals("123", SystemOutHandler.getDynamicOutput());
        System.out.print("456");
        assertEquals("456", SystemOutHandler.getDynamicOutput());
    }

    @Test
    public void testNormalOutputHandlerWithNewLines() {
        System.out.println("123");
        assertEquals("123\n", SystemOutHandler.getDynamicOutput());
        System.out.println("456");
        assertEquals("456\n", SystemOutHandler.getDynamicOutput());
    }

    @Test
    public void testResetOutputHandler() {
        System.out.print("123");
        SystemOutHandler.resetOutput();
        assertEquals("", SystemOutHandler.getDynamicOutput());
        System.out.print("456");
        assertEquals("456", SystemOutHandler.getDynamicOutput());
    }

    @Test
    public void testDynamicOutputWithLines() {
        SystemInHandler.setInput("line1\nline2\nline3");
        System.out.println("123");
        assertEquals("123\n",
            SystemOutHandler.getOutputWithInputInjected());

        scanner.nextLine();
        assertEquals("123\n>line1\n",
            SystemOutHandler.getOutputWithInputInjected());

        System.out.print("print:");
        assertEquals("123\n>line1\nprint:",
            SystemOutHandler.getOutputWithInputInjected());

        scanner.nextLine();
        assertEquals("123\n>line1\nprint:>line2\n",
            SystemOutHandler.getOutputWithInputInjected());

        scanner.nextLine();
        assertEquals("123\n>line1\nprint:>line2\n>line3\n",
            SystemOutHandler.getOutputWithInputInjected());
    }

    @Test
    public void testDynamicOutputWithNumbers() {
        SystemInHandler.setInput("123 456\n678\n248");
        System.out.print("Print x and y: ");
        assertEquals("Print x and y: ",
            SystemOutHandler.getOutputWithInputInjected());

        scanner.nextInt();
        assertEquals("Print x and y: >123 456\n",
            SystemOutHandler.getOutputWithInputInjected());

        scanner.nextInt();
        assertEquals("Print x and y: >123 456\n",
            SystemOutHandler.getOutputWithInputInjected());

        scanner.nextInt();
        assertEquals("Print x and y: >123 456\n>678\n",
            SystemOutHandler.getOutputWithInputInjected());

        System.out.println("Another num:");
        assertEquals("Print x and y: >123 456\n>678\nAnother num:\n",
            SystemOutHandler.getOutputWithInputInjected());

        scanner.nextInt();
        assertEquals("Print x and y: >123 456\n>678\nAnother num:\n>248\n",
            SystemOutHandler.getOutputWithInputInjected());
    }

}
