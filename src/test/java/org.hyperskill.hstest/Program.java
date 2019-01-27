package org.hyperskill.hstest;

import java.util.Scanner;

/**
 * A program to be tested
 */
public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int a = scanner.nextInt();
        int b = scanner.nextInt();

        System.out.println(a + b);
    }
}
