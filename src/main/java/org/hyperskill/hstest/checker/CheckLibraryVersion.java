package org.hyperskill.hstest.checker;

import org.hyperskill.hstest.exception.outcomes.UnexpectedError;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
* Checks if the current version of the library is the latest one on GitHub releases page of the library.
* If not, throws an exception.
 */
public class CheckLibraryVersion {

    private String VERSION_FILE = "src/main/java/org/hyperskill/hstest/resources/version.txt";

    private String currentVersion;
    private String latestVersion;
    private boolean isLatestVersion = true;

    public CheckLibraryVersion() {
    }

    /**
     * Checks if the current version of the library is the latest one on GitHub releases page of the library.
     * If not, throws an exception.
     */
    public void checkVersion() {
        LocalDate lastChecked = null;
        File lastCheckedFile = new File("LastChecked.txt");
        if (lastCheckedFile.exists()) {
            try {
                FileReader fileReader = new FileReader(lastCheckedFile);
                BufferedReader reader = new BufferedReader(fileReader);
                lastChecked = LocalDate.parse(reader.readLine());
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (lastChecked != null && lastChecked.equals(LocalDate.now())) {
            return;
        }

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(VERSION_FILE);
        if (inputStream == null) {
            throw new RuntimeException("Unable to find hs-test library version file");
        }

        try {
            currentVersion = new BufferedReader(new InputStreamReader(inputStream)).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        latestVersion = getLatestHsTestVersionFromGitHub();

        if (!currentVersion.equals(latestVersion)) {
            if (currentVersion.split("\\.")[0] != latestVersion.split("\\.")[0]) {
                throw new RuntimeException(getFeedback());
            } else {
                isLatestVersion = false;
            }
        }

        lastChecked = LocalDate.now();
        try (FileWriter writer = new FileWriter(lastCheckedFile)) {
            writer.write(lastChecked.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns latest version of the library from GitHub releases page of the library.
     * @return String latest version of the library
     */
    private String getLatestHsTestVersionFromGitHub() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://api.github.com/repos/hyperskill/hs-test/releases/latest");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/vnd.github+json");

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new UnexpectedError("Error getting latest version of hs-test library from GitHub");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String response = in.lines().collect(Collectors.joining());
            JsonObject jsonObject = Json.createReader(new StringReader(response)).readObject();
            return jsonObject.getString("tag_name");
        } catch (IOException e) {
            throw new UnexpectedError("Error getting latest version of hs-test library from GitHub");
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

    /**
     * Returns true if the current version of the library is the latest one.
     * @return Boolean true if the current version of the library is the latest one
     */
    public Boolean getLatestVersion() {
        return isLatestVersion;
    }
}
