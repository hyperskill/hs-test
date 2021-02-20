package org.hyperskill.hstest.testing.expect.xml.builder;

import org.hyperskill.hstest.testing.expect.xml.ExpectationXmlFeedback;
import org.w3c.dom.Element;

public class XmlAnyBuilder extends XmlBaseBuilder {
    @Override
    public boolean check(Element elem, ExpectationXmlFeedback feedback) {
        return true;
    }
}
