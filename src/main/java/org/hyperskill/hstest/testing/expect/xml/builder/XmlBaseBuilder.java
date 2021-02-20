package org.hyperskill.hstest.testing.expect.xml.builder;

import org.hyperskill.hstest.testing.expect.base.SchemaBuilder;
import org.hyperskill.hstest.testing.expect.xml.ExpectationXmlFeedback;
import org.w3c.dom.Element;

public abstract class XmlBaseBuilder extends SchemaBuilder<Element, ExpectationXmlFeedback> {
    @Override
    public abstract boolean check(Element elem, ExpectationXmlFeedback feedback);
}
