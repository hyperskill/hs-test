package org.hyperskill.hstest.testing.expect.xml;

import org.hyperskill.hstest.common.XmlUtils;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.testing.expect.Expectation;
import org.hyperskill.hstest.testing.expect.xml.builder.XmlBaseBuilder;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class ExpectationXmlBuilder<T> {

    Expectation<T> expect;

    Element originalElem;
    Element elem;

    List<String> path = new ArrayList<>();
    String feedback = "";

    public ExpectationXmlBuilder(Expectation<T> expect) {
        this.expect = expect;
        elem = XmlUtils.getXml(expect.text);
        originalElem = elem;
    }

    public void check(XmlBaseBuilder schema) {
        ExpectationXmlFeedback feedback = new ExpectationXmlFeedback(path, originalElem);
        schema.check(elem, feedback);

        if (feedback.isFailed) {
            throw new WrongAnswer(feedback.feedback.trim());
        }
    }

}
