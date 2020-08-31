package org.hyperskill.hstest.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Utils {

    private Utils() { }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) { }
    }

    public static String getUrlPage(String url) throws IOException {
        if (!url.matches("^https?://.*")) {
            url = "http://" + url;
        }
        InputStream inputStream = new URL(url).openStream();
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        String nextLine;
        String newLine = System.getProperty("line.separator");
        while ((nextLine = reader.readLine()) != null) {
            stringBuilder.append(nextLine);
            stringBuilder.append(newLine);
        }
        return cleanText(stringBuilder.toString()).trim();
    }

    public static String cleanText(String str) {
        String nbsp = "\u00a0";
        String space = "\u0020";
        return str
            .replaceAll("\r\n", "\n")
            .replaceAll("\r", "\n")
            .replaceAll(nbsp, space);
    }

    private static final Pattern methodSorter = Pattern.compile("^.*[^0-9]([0-9]+)$");

    public static int smartCompare(String s1, String s2) {
        Matcher m1 = methodSorter.matcher(s1);
        Matcher m2 = methodSorter.matcher(s2);

        if (m1.matches() && m2.matches()) {
            String num1 = m1.group(1);
            String num2 = m2.group(1);
            String name1 = s1.substring(0, s1.length() - num1.length());
            String name2 = s2.substring(0, s2.length() - num2.length());
            if (name1.equals(name2)) {
                return Integer.parseInt(num1) - Integer.parseInt(num2);
            }
        }

        return s1.compareTo(s2);
    }
}
