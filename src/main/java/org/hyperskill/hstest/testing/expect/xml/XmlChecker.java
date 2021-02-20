package org.hyperskill.hstest.testing.expect.xml;

import org.hyperskill.hstest.testing.expect.xml.builder.XmlAnyBuilder;

public class XmlChecker {
    public static XmlAnyBuilder any() {
        return new XmlAnyBuilder();
    }
}
