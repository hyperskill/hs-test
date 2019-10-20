package dynamic;

import org.hyperskill.hstest.v7.dynamic.input.SystemInHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;


public class TestInputHandler {

    public static Scanner scanner;

    @Before
    public void setUp() {
        SystemInHandler.replaceSystemIn();
        scanner = new Scanner(System.in);
    }

    @After
    public void tearDown() {
        SystemInHandler.revertSystemIn();
    }

    @Test
    public void testNumbers() {
        SystemInHandler.setInput("123 234 345\n456 567 678");
        assertEquals(123, scanner.nextInt());
        assertEquals(234, scanner.nextInt());
        assertEquals(345, scanner.nextInt());
        assertEquals(456, scanner.nextInt());
        assertEquals(567, scanner.nextInt());
        assertEquals(678, scanner.nextInt());
    }

    @Test
    public void testLines() {
        SystemInHandler.setInput("123 234\n 345 456\n 567 678 \n");
        assertEquals("123 234", scanner.nextLine());
        assertEquals(" 345 456", scanner.nextLine());
        assertEquals(" 567 678 ", scanner.nextLine());
    }

    @Test
    public void testWords() {
        SystemInHandler.setInput("123 234\n w345 456\n w567 678e \n");
        assertEquals("123", scanner.next());
        assertEquals("234", scanner.next());
        assertEquals("w345", scanner.next());
        assertEquals("456", scanner.next());
        assertEquals("w567", scanner.next());
        assertEquals("678e", scanner.next());
    }

    @Test
    public void testCombo() {
        SystemInHandler.setInput("123 234\n w345 456\n w567 678 \n");
        assertEquals(123, scanner.nextInt());
        assertEquals("234", scanner.next());
        assertEquals("", scanner.nextLine());
        assertEquals(" w345 456", scanner.nextLine());
        assertEquals("w567", scanner.next());
        assertEquals(678, scanner.nextInt());
    }

}
