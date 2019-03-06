package org.hyperskill.hstest;

import org.hyperskill.hstest.dev.common.Utils;
import org.hyperskill.hstest.dev.mocks.WebServerMock;
import org.hyperskill.hstest.dev.testcase.Process;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.assertEquals;

public class WebServerMockTest {

    private ExecutorService service;
    private List<Process> processes;

    @Before
    public void setUp() {
        processes = List.of(
            new WebServerMock(12345)
            .setPage("/", "123\n456")
            .setPage("/12", "342\n678")
            .setPage("/13", "0987\n5432")
        );
        service = Utils.startThreads(processes);
    }

    @After
    public void tearDown() {
        Utils.stopThreads(processes, service);
    }

    @Test
    public void testCorrectURL1() {
        assertEquals("0987\n5432", Utils.getUrlPage("127.0.0.1:12345/13"));
        assertEquals("342\n678", Utils.getUrlPage("127.0.0.1:12345/12"));
    }

    @Test
    public void testCorrectURL2() {
        assertEquals("123\n456", Utils.getUrlPage("127.0.0.1:12345/"));
    }
}
