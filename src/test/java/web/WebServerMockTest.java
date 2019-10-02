package web;

import org.hyperskill.hstest.v7.common.ProcessUtils;
import org.hyperskill.hstest.v7.common.Utils;
import org.hyperskill.hstest.v7.mocks.web.WebPage;
import org.hyperskill.hstest.v7.mocks.web.WebServerMock;
import org.hyperskill.hstest.v7.testcase.Process;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.assertEquals;

public class WebServerMockTest {

    private ExecutorService service;
    private List<Process> processes;

    @Before
    public void setUp() {
        processes = Arrays.asList(
            new WebServerMock(12345)
                .setPage("/", "123\n456")
                .setPage("/page1", "342\n678")
                .setPage("/page2", "0987\n5432")

                .setPage("/type1", new WebPage()
                    .setContent("type1")
                    .setContentType("text/html"))

                .setPage("/type2", new WebPage()
                    .setContent("type2")
                    .setContentType("text/json"))

                .setPage("/page3", new WebPage()
                    .setContent("page3")
                    .setContentType("text/html"))

                .setPage("/page4", new WebPage()
                    .setContent("page4")
                    .setContentType("text/json"))
        );
        service = ProcessUtils.startThreads(processes);
    }

    @After
    public void tearDown() {
        ProcessUtils.stopThreads(processes, service);
    }

    @Test
    public void testCorrectContent1() {
        assertEquals("342\n678", Utils.getUrlPage("127.0.0.1:12345/page1"));
        assertEquals("0987\n5432", Utils.getUrlPage("127.0.0.1:12345/page2"));
        assertEquals("type1", Utils.getUrlPage("127.0.0.1:12345/type1"));
        assertEquals("type2", Utils.getUrlPage("127.0.0.1:12345/type2"));
    }

    @Test
    public void testCorrectContent2() {
        assertEquals("123\n456", Utils.getUrlPage("127.0.0.1:12345/"));
        assertEquals("page3", Utils.getUrlPage("127.0.0.1:12345/page3"));
        assertEquals("page4", Utils.getUrlPage("127.0.0.1:12345/page4"));
    }

    @Test
    public void testWithGetParams() {
        assertEquals("page3", Utils.getUrlPage("127.0.0.1:12345/page3?type=123"));
        assertEquals("page4", Utils.getUrlPage("127.0.0.1:12345/page4?abc=def&1=2"));
    }
}
