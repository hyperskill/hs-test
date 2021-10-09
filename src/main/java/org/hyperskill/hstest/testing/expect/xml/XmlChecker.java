package org.hyperskill.hstest.testing.expect.xml;

import org.hyperskill.hstest.testing.expect.base.checker.StringChecker;
import org.hyperskill.hstest.testing.expect.xml.builder.XmlAnyBuilder;
import org.hyperskill.hstest.testing.expect.xml.builder.XmlFinishedNodeBuilder;
import org.hyperskill.hstest.testing.expect.xml.builder.XmlNodeAttributeBuilder;

import java.util.regex.Pattern;

public class XmlChecker {
    public static XmlAnyBuilder any() {
        return new XmlAnyBuilder();
    }

    public static XmlNodeAttributeBuilder isNode() {
        return new XmlNodeAttributeBuilder();
    }

    public static XmlNodeAttributeBuilder isNode(String tag) {
        return new XmlNodeAttributeBuilder().tag(tag);
    }

    public static XmlFinishedNodeBuilder isNode(String tag, String value) {
        return new XmlNodeAttributeBuilder().tag(tag).value(value);
    }

    public static XmlFinishedNodeBuilder isNode(String tag, Pattern value) {
        return new XmlNodeAttributeBuilder().tag(tag).value(value);
    }

    public static XmlFinishedNodeBuilder isNode(String tag, StringChecker value) {
        return new XmlNodeAttributeBuilder().tag(tag).value(value);
    }

    public static XmlFinishedNodeBuilder isNode(String tag, XmlAnyBuilder value) {
        return new XmlNodeAttributeBuilder().tag(tag).anyOtherAttributes().anyOtherChild();
    }

    public static XmlFinishedNodeBuilder isNode(XmlAnyBuilder unused) {
        return new XmlNodeAttributeBuilder().anyOtherAttributes().anyOtherChild();
    }
}
