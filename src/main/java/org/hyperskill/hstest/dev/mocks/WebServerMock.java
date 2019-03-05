package org.hyperskill.hstest.dev.mocks;

import java.util.HashMap;
import java.util.Map;

public class WebServerMock implements Runnable {

    private Map<String, String> pages = new HashMap<>();

    @Override
    public void run() {

    }

    public WebServerMock setPage(String url, String content) {
        pages.put(url, content);
        return this;
    }
}
