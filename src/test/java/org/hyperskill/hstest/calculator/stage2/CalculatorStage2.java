package org.hyperskill.hstest.calculator.stage2;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * An example: this program just calculates the sum of N input numbers
 */
public class CalculatorStage2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        long sum = Arrays.stream(scanner.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .sum();

        System.out.println(sum);
    }
}
