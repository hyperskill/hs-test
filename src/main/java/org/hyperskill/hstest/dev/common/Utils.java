package org.hyperskill.hstest.dev.common;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class Utils {

    private Utils() {}

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }

    public static String getUrlPage(String url) {
        try {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
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
            return normalizeLineEndings(stringBuilder.toString()).trim();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static String normalizeLineEndings(String str) {
        return str
            .replaceAll("\r\n", "\n")
            .replaceAll("\r", "\n");
    }
}
