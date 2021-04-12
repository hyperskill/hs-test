package org.hyperskill.hstest.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class Utils {

    private Utils() { }

    /**
     * Sleep function that doesn't require catching exception
     * @param ms milliseconds to sleep
     */
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) { }
    }

    /**
     * Try some action many times, but not infinitely
     * @param timesToTry maximum number of tries
     * @param sleepTime sleeping time between every check
     * @param exitFunc function that checks for exit condition
     * @return true, if exitFunc yielded true once, otherwise false
     */
    public static boolean tryManyTimes(int timesToTry, int sleepTime, Supplier<Boolean> exitFunc) {
        while (timesToTry-- > 0) {
            if (exitFunc.get()) {
                return true;
            }
            sleep(sleepTime);
        }
        return false;
    }

    public static String getUrlPage(String url) throws IOException {
        return NetworkUtils.getUrlPage(url);
    }

    public static String cleanText(String str) {
        String nbsp = "\u00a0";
        String space = "\u0020";
        return str
            .replace("\r\n", "\n")
            .replace("\r", "\n")
            .replace(nbsp, space);
    }

    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase()
            + (str.length() > 1 ? str.substring(1) : "");
    }

    public static int smartCompare(String s1, String s2) {

        class NamePair implements Comparable<NamePair> {
            final String text;
            final int num;

            public NamePair(String text, String num) {
                this.text = text;
                this.num = Integer.parseInt(num);
            }

            @Override
            public int compareTo(NamePair o) {
                int textCompare = text.compareTo(o.text);
                if (textCompare != 0) {
                    return textCompare;
                }
                return num - o.num;
            }
        }

        class NameTokenizer implements Comparable<NameTokenizer> {
            final List<NamePair> tokens = new ArrayList<>();

            NameTokenizer(String name) {
                StringBuilder partialText = new StringBuilder();
                StringBuilder partialNum = new StringBuilder();
                boolean parsingName = true;

                for (char c : name.toCharArray()) {
                    if (parsingName && c >= '0' && c <= '9') {
                        parsingName = false;
                    } else if (!parsingName && (c < '0' || c > '9')) {
                        parsingName = true;
                        tokens.add(new NamePair(partialText.toString(), partialNum.toString()));
                        partialText = new StringBuilder();
                        partialNum = new StringBuilder();
                    }

                    if (parsingName) {
                        partialText.append(c);
                    } else {
                        partialNum.append(c);
                    }
                }

                if (parsingName) {
                    partialNum = new StringBuilder("-1");
                }

                tokens.add(new NamePair(partialText.toString(), partialNum.toString()));
            }

            @Override
            public int compareTo(NameTokenizer o) {
                for (int i = 0; true; i++) {
                    if (tokens.size() == i && o.tokens.size() == i) {
                        return 0;
                    } else if (tokens.size() == i) {
                        return -1;
                    } else if (o.tokens.size() == i) {
                        return 1;
                    }

                    NamePair thisPair = tokens.get(i);
                    NamePair otherPair = o.tokens.get(i);
                    int tokenCompare = thisPair.compareTo(otherPair);

                    if (tokenCompare != 0) {
                        return tokenCompare;
                    }
                }
            }
        }

        NameTokenizer method1 = new NameTokenizer(s1);
        NameTokenizer method2 = new NameTokenizer(s2);

        return method1.compareTo(method2);
    }
}
