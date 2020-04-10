package org.hyperskill.hstest.testing.expect;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExpectTypeBuilder<T> {
    final Expectation<T> expect;

    private Scanner scanner;

    ExpectTypeBuilder(Expectation<T> expect) {
        this.expect = expect;
    }

    public List<String> lines() {
        Expectation<String> exp = expect.copy(this::findLines);
        exp.whatsWrongFunc = () -> "contains wrong number of lines";
        return exp.check();
    }

    public List<String> words() {
        Expectation<String> exp = expect.copy(this::findWords);
        exp.whatsWrongFunc = () -> "contains wrong number of words";
        return exp.check();
    }

    public List<String> wordsFrom(String... words) {
        return null;
    }

    public List<String> wordsOnlyFrom(String... words) {
        return null;
    }

    public List<Integer> integers() {
        return null;
    }

    public List<Integer> integersOnly() {
        Expectation<Integer> exp = expect.copy(this::findIntegersOnly);
        exp.whatsWrongFunc = () -> {
            if (scanner.hasNext()) {
                exp.hint = "";
                return "contains not only numbers (\"" + scanner.next() + "\")";
            }
            return "contains wrong number of integers";
        };
        return exp.check();
    }

    public List<Double> doubles() {
        return null;
    }

    public List<Double> doublesOnly() {
        Expectation<Double> exp = expect.copy(this::findDoublesOnly);
        exp.whatsWrongFunc = () -> {
            if (scanner.hasNext()) {
                exp.hint = "";
                return "contains not only doubles (\"" + scanner.next() + "\")";
            }
            return "contains wrong number of doubles";
        };
        return exp.check();
    }

    public List<String> regex() {
        return null;
    }

    List<String> findLines() {
        String toCheck = expect.text.trim();
        scanner = new Scanner(toCheck);
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        return lines;
    }

    List<String> findWords() {
        String toCheck = expect.text.trim();
        scanner = new Scanner(toCheck);
        List<String> words = new ArrayList<>();
        while (scanner.hasNext()) {
            words.add(scanner.next());
        }
        return words;
    }

    List<Integer> findIntegersOnly() {
        String toCheck = expect.text.trim();
        scanner = new Scanner(toCheck);
        List<Integer> ints = new ArrayList<>();
        while (scanner.hasNextInt()) {
            ints.add(scanner.nextInt());
        }
        return ints;
    }

    List<Double> findDoublesOnly() {
        String toCheck = expect.text.trim();
        scanner = new Scanner(toCheck);
        List<Double> doubles = new ArrayList<>();
        while (scanner.hasNextDouble()) {
            doubles.add(scanner.nextDouble());
        }
        return doubles;
    }
}
