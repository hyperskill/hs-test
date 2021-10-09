package org.hyperskill.hstest.testing.expect.xml;

import org.hyperskill.hstest.common.XmlUtils;
import org.w3c.dom.Element;

import java.util.List;

public class ExpectationXmlFeedback {
    final List<String> currPath;
    final Element originalElem;
    boolean isFailed = false;
    String feedback;

    ExpectationXmlFeedback(List<String> currPath, Element originalElem) {
        this.currPath = currPath;
        this.originalElem = originalElem;
    }

    public void addPath(String key) {
        currPath.add(key);
    }

    public void removePath() {
        currPath.remove(currPath.size() - 1);
    }

    public int getPathSize() {
        return currPath.size();
    }

    public void fail(String feedback) {
        if (!isFailed) {
            isFailed = true;

            this.feedback = "The XML";

            if (currPath.size() == 0) {
                this.feedback += " root node";
            } else {
                String path = "/" + String.join("/", currPath.toArray(new String[0]));
                this.feedback += " node at path \"" + path + "\"";
            }

            this.feedback += " " + feedback
                + "\n\nFull XML:\n" + XmlUtils.getPrettyXml(originalElem);
        }
    }

    public boolean isFailed() {
        return isFailed;
    }

    public String getFeedback() {
        return feedback;
    }

}
