package org.hyperskill.hstest.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.hyperskill.hstest.common.Utils.cleanText;

public class NetworkUtils {

    private NetworkUtils() { }

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

    public static boolean isPortAvailable(int port) {
        if (OsUtils.isWindows()) {
            // faster way to check port availability, but works only on Windows
            try (ServerSocket serverSocket = new ServerSocket()) {
                serverSocket.setReuseAddress(false);
                serverSocket.bind(new InetSocketAddress(InetAddress.getByName("localhost"), port), 1);
                return true;
            } catch (Exception ex) {
                return false;
            }

        } else {
            try (Socket ignored = new Socket("localhost", port)) {
                return false;
            } catch (IOException ignored) {
                return true;
            }
        }
    }

}
