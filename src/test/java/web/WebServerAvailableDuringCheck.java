package web;

import org.hyperskill.hstest.v7.common.Utils;
import org.hyperskill.hstest.v7.mocks.web.WebPage;
import org.hyperskill.hstest.v7.mocks.web.WebServerMock;
import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Arrays;
import java.util.List;


class WebServerAvailableDuringCheckMain {
    public static void main(String[] args) {

    }
}

public class WebServerAvailableDuringCheck extends StageTest {

    public WebServerAvailableDuringCheck() {
        super(WebServerAvailableDuringCheckMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
            .runWith(new WebServerMock(45678)
                .setPage("/123", new WebPage().setContent("234")))
        );
    }

    @Override
    public CheckResult check(String reply, Object clue) {
        return new CheckResult("234".equals(Utils.getUrlPage("http://127.0.0.1:45678/123")));
    }

}
