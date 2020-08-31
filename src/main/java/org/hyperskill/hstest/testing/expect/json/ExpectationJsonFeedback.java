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

            this.feedback = "The JSON";
            if (currPath.size() <= 1) {

                if (originalElem.isJsonArray()) {
                    this.feedback += " array";
                    if (currPath.size() == 1) {
                        this.feedback += " at index " + currPath.get(0);
                    }

                } else if (originalElem.isJsonObject()) {
                    this.feedback += " object";
                    if (currPath.size() == 1) {
                        this.feedback += " at key " + currPath.get(0);
                    }

                } else {
                    this.feedback += " element";
                }

            } else {
                String path = "/" + String.join("/", currPath.toArray(new String[0]));
                this.feedback += " element at path \"" + path + "\"";
            }

            this.feedback += " " + feedback
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
