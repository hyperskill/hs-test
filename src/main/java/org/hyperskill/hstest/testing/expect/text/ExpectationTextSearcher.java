package org.hyperskill.hstest.testing.expect.text;

import org.hyperskill.hstest.testing.expect.Expectation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpectationTextSearcher<T> {
    final Expectation<T> expect;

    private final Scanner scanner;
    private final Pattern doublePattern = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+(?:[eE][-+]?[0-9]+)?");
    private final Pattern intPattern = Pattern.compile("[-+]?[0-9]+");

    public ExpectationTextSearcher(Expectation<T> expect) {
        this.expect = expect;
        scanner = new Scanner(expect.text);
    }

    public List<String> lines() {
        return custom(this::findLines, "lines");
    }

    public List<String> words() {
        return custom(this::findWords, "words");
    }

    public List<String> words(String delim) {
        scanner.useDelimiter("\\s*" + delim + "\\s*");
        return custom(this::findWords, "words separated by \"" + delim + "\"");
    }

    public List<String> wordsFrom(String... words) {
        return custom(() -> findAcrossText(patternForSpecificWords(words)), "specific words");
    }

    public List<String> wordsOnlyFrom(String... words) {
        Expectation<String> exp = expect.copy(() -> findWords(patternForSpecificWords(words)));
        exp.whatSearchedFor = feedbackWithScanner(exp, "specific words");
        return exp.check();
    }

    public List<Integer> integers() {
        return custom(this::findIntegers, "integers");
    }

    public List<Integer> integersOnly() {
        Expectation<Integer> exp = expect.copy(this::findIntegersOnly);
        exp.whatSearchedFor = feedbackWithScanner(exp, "integers");
        return exp.check();
    }

    public List<Integer> integersOnly(String delim) {
        scanner.useDelimiter("\\s*" + delim + "\\s*");
        Expectation<Integer> exp = expect.copy(this::findIntegersOnly);
        exp.whatSearchedFor = feedbackWithScanner(exp, "integers", delim);
        return exp.check();
    }

    public List<Double> doubles() {
        return custom(this::findDoubles, "doubles");
    }

    public List<Double> doublesOnly() {
        Expectation<Double> exp = expect.copy(this::findDoublesOnly);
        exp.whatSearchedFor = feedbackWithScanner(exp, "doubles");
        return exp.check();
    }

    public List<Double> doublesOnly(String delim) {
        scanner.useDelimiter("\\s*" + delim + "\\s*");
        Expectation<Double> exp = expect.copy(this::findDoublesOnly);
        exp.whatSearchedFor = feedbackWithScanner(exp, "doubles", delim);
        return exp.check();
    }

    public List<String> regex(String pattern) {
        return regex(pattern, "words with pattern " + pattern);
    }

    public List<String> regex(String pattern, String patternDescription) {
        return custom(() -> findAcrossText(pattern),  patternDescription);
    }

    public <X> List<X> custom(Supplier<List<X>> findAllElemsFunc, String whatSearchedFor) {
        return custom(findAllElemsFunc, () -> whatSearchedFor);
    }

    public <X> List<X> custom(Supplier<List<X>> findAllElemsFunc, Supplier<String> whatSearchedFor) {
        Expectation<X> exp = expect.copy(findAllElemsFunc);
        exp.whatSearchedFor = whatSearchedFor;
        return exp.check();
    }

    // ---------------------------------
    // searching methods

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
        while (scanner.hasNext(pattern)) {
            words.add(scanner.next(pattern));
        }
        if (scanner.hasNext()) {
            return null;
        }
        return words;
    }

    List<Integer> findIntegersOnly() {
        List<Integer> ints = new ArrayList<>();
        while (scanner.hasNextInt()) {
            ints.add(scanner.nextInt());
        }
        if (scanner.hasNext()) {
            return null;
        }
        return ints;
    }

    List<Double> findDoublesOnly() {
        List<Double> doubles = new ArrayList<>();
        while (scanner.hasNextDouble()) {
            doubles.add(scanner.nextDouble());
        }
        if (scanner.hasNext()) {
            return null;
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

    // ---------------------------------
    // helper methods

    String patternForSpecificWords(String... words) {
        StringBuilder patternBuilder = new StringBuilder();
        for (String word : words) {
            patternBuilder.append("(?:").append(word).append(")|");
        }
        // remove last "|"
        patternBuilder.setLength(Math.max(patternBuilder.length() - 1, 0));
        return patternBuilder.toString();
    }

    <X> Supplier<String> feedbackWithScanner(Expectation<X> expect, String type) {
        return feedbackWithScanner(expect, type, null);
    }

    <X> Supplier<String> feedbackWithScanner(Expectation<X> expect, String type, String delim) {
        return () -> {
            if (scanner.hasNext()) {
                expect.hint = i -> "but also \"" + scanner.next() + "\"";
                expect.whyFailed = "not only";
                return type;
            }
            String separated = "";
            if (delim != null) {
                separated = " separated by \"" + delim + "\"";
            }
            return type + separated;
        };
    }
}
