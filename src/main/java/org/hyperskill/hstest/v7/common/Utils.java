package org.hyperskill.hstest.v7.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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
        return str
            .replaceAll("\r\n", "\n")
            .replaceAll("\r", "\n")
            .replaceAll("\u0412\u00a0", "\u00a0")
            .replaceAll("\u00a0", "\u0020");
    }
}
