package web;

import org.hyperskill.hstest.common.ProcessUtils;
import org.hyperskill.hstest.common.Utils;
import org.hyperskill.hstest.mocks.web.WebPage;
import org.hyperskill.hstest.mocks.web.WebServerMock;
import org.hyperskill.hstest.testcase.Process;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.assertEquals;

public class WebServerMockTest {

    private ExecutorService service;
    private List<Process> processes;

    int port = 23456;
    String address = "127.0.0.1:" + port + '/';

    @Before
    public void setUp() {
        processes = Arrays.asList(
            new WebServerMock(port)
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
    public void testCorrectContent1() throws IOException {
        assertEquals("342\n678", Utils.getUrlPage(address + "page1"));
        assertEquals("0987\n5432", Utils.getUrlPage(address + "page2"));
        assertEquals("type1", Utils.getUrlPage(address + "type1"));
        assertEquals("type2", Utils.getUrlPage(address + "type2"));
    }

    @Test
    public void testCorrectContent2() throws IOException {
        assertEquals("123\n456", Utils.getUrlPage(address));
        assertEquals("page3", Utils.getUrlPage(address + "page3"));
        assertEquals("page4", Utils.getUrlPage(address + "page4"));
    }

    @Test
    public void testWithGetParams() throws IOException {
        assertEquals("page3", Utils.getUrlPage(address + "page3?type=123"));
        assertEquals("page4", Utils.getUrlPage(address + "page4?abc=def&1=2"));
    }
}
