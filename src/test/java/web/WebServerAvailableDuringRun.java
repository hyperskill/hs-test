package web;

import org.hyperskill.hstest.mocks.web.WebPage;
import org.hyperskill.hstest.mocks.web.WebServerMock;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

class UrlDownloader {
    public static String normalizeLineEndings(String str) {
        return str
            .replaceAll("\r\n", "\n")
            .replaceAll("\r", "\n");
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
}

class WebServerAvailableDuringRunMain {
    public static void main(String[] args) {
        System.out.println(UrlDownloader.getUrlPage("http://127.0.0.1:45678/123"));
    }
}

public class WebServerAvailableDuringRun extends StageTest<String> {

    public WebServerAvailableDuringRun() {
        super(WebServerAvailableDuringRunMain.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .setAttach("test web server")
                .runWith(new WebServerMock(45678)
                    .setPage("/123", new WebPage().setContent("test web server")))
        );
    }

    @Override
    public CheckResult check(String reply, String clue) {
        return new CheckResult(reply.trim().equals(clue.trim()), "");
    }

}
