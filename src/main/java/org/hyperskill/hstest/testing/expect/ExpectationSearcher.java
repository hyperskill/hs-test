package org.hyperskill.hstest.testing.expect;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpectationSearcher<T> {
    final Expectation<T> expect;

    private Scanner scanner;
    private Pattern doublePattern = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+(?:[eE][-+]?[0-9]+)?");
    private Pattern intPattern = Pattern.compile("[-+]?[0-9]+");

    ExpectationSearcher(Expectation<T> expect) {
        this.expect = expect;
        scanner = new Scanner(expect.text);
    }

    public List<String> lines() {
        return custom(this::findLines, "contains wrong number of lines");
    }

    public List<String> words() {
        return custom(this::findWords, "contains wrong number of words");
    }

    public List<String> words(String delim) {
        scanner.useDelimiter("\\s*" + delim + "\\s*");
        return custom(this::findWords, "contains wrong number of words separated by \"" + delim + "\"");
    }

    public List<String> wordsFrom(String... words) {
        return custom(() -> findAcrossText(patternForSpecificWords(words)),
            "contains wrong number of specific words");
    }

    public List<String> wordsOnlyFrom(String... words) {
        return custom(() -> findWords(patternForSpecificWords(words)),
            "contains wrong number of specific words");
    }

    public List<Integer> integers() {
        return custom(this::findIntegers, "contains wrong number of integers");
    }

    public List<Integer> integersOnly() {
        Expectation<Integer> exp = expect.copy(this::findIntegersOnly);
        exp.whatsWrongFunc = () -> {
            if (scanner.hasNext()) {
                exp.hintFunc = i -> "but also \"" + scanner.next() + "\"";
                return "contains not only integers";
            }
            return "contains wrong number of integers";
        };
        return exp.check();
    }

    public List<Double> doubles() {
        return custom(this::findDoubles, "contains wrong number of doubles");
    }

    public List<Double> doublesOnly() {
        Expectation<Double> exp = expect.copy(this::findDoublesOnly);
        exp.whatsWrongFunc = () -> {
            if (scanner.hasNext()) {
                exp.hintFunc = i -> "but also \"" + scanner.next() + "\"";
                return "contains not only doubles";
            }
            return "contains wrong number of doubles";
        };
        return exp.check();
    }

    public List<String> regex(String pattern) {
        return regex(pattern,"words with pattern " + pattern);
    }

    public List<String> regex(String pattern, String patternDescription) {
        return custom(() -> findAcrossText(pattern), "contains wrong number of " + patternDescription);
    }

    public <X> List<X> custom(Supplier<List<X>> findAllElemsFunc, String whatsWrong) {
        return custom(findAllElemsFunc, () -> whatsWrong);
    }

    public <X> List<X> custom(Supplier<List<X>> findAllElemsFunc, Supplier<String> whatsWrongFunc) {
        Expectation<X> exp = expect.copy(findAllElemsFunc);
        exp.whatsWrongFunc = whatsWrongFunc;
        return exp.check();
    }

    // ---------------------------------
    // below are just searching functions

    List<String> findLines() {
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        return lines;
    }

    List<String> findWords() {
        List<String> words = new ArrayList<>();
        while (scanner.hasNext()) {
            words.add(scanner.next());
        }
        return words;
    }

    List<String> findWords(String pattern) {
        List<String> words = new ArrayList<>();
        while (scanner.hasNext()) {
            words.add(scanner.next(pattern));
        }
        return words;
    }

    List<Integer> findIntegersOnly() {
        List<Integer> ints = new ArrayList<>();
        while (scanner.hasNextInt()) {
            ints.add(scanner.nextInt());
        }
        return ints;
    }

    List<Double> findDoublesOnly() {
        List<Double> doubles = new ArrayList<>();
        while (scanner.hasNextDouble()) {
            doubles.add(scanner.nextDouble());
        }
        return doubles;
    }

    List<Integer> findIntegers() {
        Matcher m = intPattern.matcher(expect.text);
        List<Integer> matches = new ArrayList<>();
        while (m.find()) {
            matches.add(Integer.parseInt(m.group()));
        }
        return matches;
    }

    List<Double> findDoubles() {
        Matcher m = doublePattern.matcher(expect.text);
        List<Double> matches = new ArrayList<>();
        while (m.find()) {
            matches.add(Double.parseDouble(m.group()));
        }
        return matches;
    }

    List<String> findAcrossText(String pattern) {
        Matcher m = Pattern.compile(pattern).matcher(expect.text);
        List<String> matches = new ArrayList<>();
        while (m.find()) {
            matches.add(m.group());
        }
        return matches;
    }

    String patternForSpecificWords(String... words) {
        StringBuilder patternBuilder = new StringBuilder();
        for (String word : words) {
            patternBuilder.append("(?:").append(word).append(")|");
        }
        // remove last "|"
        patternBuilder.setLength(Math.max(patternBuilder.length() - 1, 0));
        return patternBuilder.toString();
    }
}
