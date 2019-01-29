package org.hyperskill.hstest.orders;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * An example: this application stores a history of orders and print to a user.
 */
public class OrderHistoryApp {
    private static final String HELLO_STRING = "Welcome in the personal order list!";

    private static final String EXIT_COMMAND = "/exit";
    private static final String ORDERS_COMMAND = "/orders";

    private static final List<Order> history = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(HELLO_STRING);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase(EXIT_COMMAND)) {
                break;
            } else if (input.equalsIgnoreCase(ORDERS_COMMAND)) {
                printOrderHistory();
            } else {
                String[] order = input.split("\\s+");
                if (order.length < 2) {
                    System.out.println("INVALID ORDER");
                } else {
                    history.add(new Order(order[0], Integer.parseInt(order[1])));
                }
            }
        }
    }

    private static void printOrderHistory() {
        history.forEach(order ->
                System.out.printf("order: %s, cost: %d\n",
                        order.getDescription(),
                        order.getCost()));
    }
}
