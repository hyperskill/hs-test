package org.hyperskill.hstest.testing.expect.json;

import java.util.List;

public class ExpectationJsonFeedback {
    final List<String> currPath;
    boolean isFailed = false;
    String feedback;

    ExpectationJsonFeedback(List<String> currPath) {
        this.currPath = currPath;
    }

    public void addPath(String key) {
        currPath.add(key);
    }

    public void removePath() {
        currPath.remove(currPath.size() - 1);
    }

    public void fail(String feedback) {
        if (!isFailed) {
            isFailed = true;
            String path = "/" + String.join("/", currPath.toArray(new String[0]));
            this.feedback = "The element at path \"" + path + "\" " + feedback;
        }
    }

    public boolean isFailed() {
        return isFailed;
    }

    public String getFeedback() {
        return feedback;
    }

}
