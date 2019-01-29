package org.hyperskill.hstest.calculator.stage1;

import java.util.Scanner;

/**
 * An example: this program just calculates the sum of two numbers
 */
public class CalculatorStage1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int a = scanner.nextInt();
        int b = scanner.nextInt();

        System.out.println(a + b);
    }
}
