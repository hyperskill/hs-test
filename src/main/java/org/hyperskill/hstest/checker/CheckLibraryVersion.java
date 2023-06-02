package org.hyperskill.hstest.checker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

/**
* Checks if the current version of the library is the latest one on GitHub releases page of the library.
* If not, throws an exception.
 */
public class CheckLibraryVersion {

    private String VERSION_FILE = "src/main/java/org/hyperskill/hstest/resources/version.txt";
    private String LAST_CHECKED_FILE = "lastCheckedHSTestLibrary.txt";
    private String GITHUB_API = "https://api.github.com/repos/hyperskill/hs-test/releases/latest";
    private String currentVersion;
    private String latestVersion;
    public boolean isLatestVersion = true;

    public CheckLibraryVersion() {
    }

    /**
     * Checks if the current version of the library is the latest one on GitHub releases page of the library.
     * If not, throws an exception.
     */
    public void checkVersion() throws IOException {
        LocalDate lastChecked = null;
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        File lastCheckedFile = new File(tempDirectoryPath + File.separator + LAST_CHECKED_FILE);
        if (lastCheckedFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(lastCheckedFile))) {
                lastChecked = LocalDate.parse(reader.readLine());
            }
        }
        if (LocalDate.now().equals(lastChecked)) {
            return;
        }
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(VERSION_FILE);
        if (inputStream != null) {
            currentVersion = new BufferedReader(new InputStreamReader(inputStream)).readLine();
        } else return;
        latestVersion = getLatestHsTestVersionFromGitHub();
        if (!currentVersion.equals(latestVersion)) {
            isLatestVersion = false;
        }
        lastChecked = LocalDate.now();
        try (FileWriter writer = new FileWriter(lastCheckedFile)) {
            writer.write(lastChecked.toString());
        }
    }

    /**
     * Returns latest version of the library from GitHub releases page of the library.
     * @return String latest version of the library
     */
    private String getLatestHsTestVersionFromGitHub() {
        HttpURLConnection connection = null;
        int responseCode = -1;
        try {
            URL url = new URL(GITHUB_API);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/vnd.github+json");
            connection.setConnectTimeout(100);
            connection.setReadTimeout(500);
            responseCode = connection.getResponseCode();
        } catch (IOException e) {
            return currentVersion;
        }
        if (responseCode != 200) {
            return currentVersion;
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String response = in.lines().collect(Collectors.joining());
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String,Object> map = gson.fromJson(response, type);
            return map.get("tag_name").toString().replace("v", "");
        } catch (IOException e) {
            return currentVersion;
        }
    }

    /**
     * Returns feedback for the user if the current version of the library is not the latest one.
     * @return String feedback for the user
     */
    public String getFeedback() {
        return "\nThe installed hs-test version (" + currentVersion + ") is not the latest version (" + latestVersion + "). " +
                "Update the library by following the instructions below:\n\n" +
                "1. Open your project's dependency file build.gradle.\n" +
                "2. Find the hs-test dependency and change its version to the latest one (" + latestVersion + ").\n" +
                "3. Sync the dependencies in your development environment or run the following commands in the terminal:\n" +
                "   For Gradle:\n" +
                "   gradle clean build --refresh-dependencies\n\n" +
                "4. Restart the tests.\n\n";
    }
}
