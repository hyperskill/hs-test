package org.hyperskill.hstest.testing.expect.json;

import com.google.gson.JsonElement;
import org.hyperskill.hstest.common.JsonUtils;

import java.util.List;

public class ExpectationJsonFeedback {
    final List<String> currPath;
    final JsonElement originalElem;
    boolean isFailed = false;
    String feedback;

    ExpectationJsonFeedback(List<String> currPath, JsonElement originalElem) {
        this.currPath = currPath;
        this.originalElem = originalElem;
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
            this.feedback = "The element at path \"" + path + "\" " + feedback
                + "\n\nFull JSON:\n" + JsonUtils.getPrettyJson(originalElem);
        }
    }

    public boolean isFailed() {
        return isFailed;
    }

    public String getFeedback() {
        return feedback;
    }

}
