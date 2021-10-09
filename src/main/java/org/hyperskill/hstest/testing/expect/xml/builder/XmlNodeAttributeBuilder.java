package org.hyperskill.hstest.testing.expect.xml.builder;

import org.hyperskill.hstest.testing.expect.base.checker.KeyValueChecker;
import org.hyperskill.hstest.testing.expect.base.checker.StringChecker;

import java.util.regex.Pattern;

public class XmlNodeAttributeBuilder extends XmlNodeChildrenBuilder {

    public XmlNodeAttributeBuilder tag(String tag) {
        return tag(tag, "tag \"" + tag + "\"");
    }

    public XmlNodeAttributeBuilder tag(String tag, String tagDescription) {
        return tag(v -> v.equals(tag), tagDescription);
    }

    public XmlNodeAttributeBuilder tag(Pattern tag) {
        return tag(tag, "tag described by pattern \"" + tag + "\"");
    }

    public XmlNodeAttributeBuilder tag(Pattern tag, String tagDescription) {
        return tag(t -> tag.matcher(t).matches(), tagDescription);
    }

    public XmlNodeAttributeBuilder tag(StringChecker tag) {
        return tag(tag, "specific tag");
    }

    public XmlNodeAttributeBuilder tag(StringChecker tag, String tagDescription) {
        tagChecker = tag;
        failTagFeedback = tagDescription;
        return this;
    }

    public XmlNodeAttributeBuilder attr(String key, String value) {
        return attr(k -> k.equals(key), v -> v.equals(value),
            "should contain an attribute " +
                "with a key \"" + key + "\" " +
                "and a value \"" + value + "\"");
    }

    public XmlNodeAttributeBuilder attr(String key, Pattern value) {
        return attr(k -> k.equals(key), v -> value.matcher(v).matches(),
            "should contain an attribute " +
                "with a key \"" + key + "\" " +
                "and a value described by pattern \"" + value + "\"");
    }

    public XmlNodeAttributeBuilder attr(String key, StringChecker value) {
        return attr(k -> k.equals(key), value,
            "should contain an attribute " +
                "with a key \"" + key + "\" " +
                "and a correct value");
    }

    public XmlNodeAttributeBuilder attr(Pattern key, String value) {
        return attr(k -> key.matcher(k).matches(), v -> v.equals(value),
            "should contain an attribute " +
                "with a key described by pattern \"" + key + "\" " +
                "and a value \"" + value + "\"");
    }

    public XmlNodeAttributeBuilder attr(Pattern key, Pattern value) {
        return attr(k -> key.matcher(k).matches(), v -> value.matcher(v).matches(),
            "should contain an attribute " +
                "with a key described by pattern \"" + key + "\" " +
                "and a value described by pattern \"" + value + "\"");
    }

    public XmlNodeAttributeBuilder attr(Pattern key, StringChecker value) {
        return attr(k -> key.matcher(k).matches(), value,
            "should contain an attribute " +
                "with a key described by pattern \"" + key + "\" " +
                "and a correct value");
    }

    public XmlNodeAttributeBuilder attr(StringChecker key, String value) {
        return attr(key, v -> v.equals(value),
            "should contain an attribute " +
                "with a specific key " +
                "and a value \"" + value + "\"");
    }

    public XmlNodeAttributeBuilder attr(StringChecker key, Pattern value) {
        return attr(key, v -> value.matcher(v).matches(),
            "should contain an attribute " +
                "with a specific key " +
                "and a value described by pattern \"" + value + "\"");
    }

    public XmlNodeAttributeBuilder attr(StringChecker key, StringChecker value) {
        return attr(key, value, "is missing an attribute");
    }

    public XmlNodeAttributeBuilder attr(StringChecker key, StringChecker value, String failFeedback) {
        keyValueCheckers.add(new KeyValueChecker<>(key, value, true, failFeedback));
        return this;
    }

    public XmlNodeAttributeBuilder anyOtherAttributes() {
        keyValueCheckers.add(new KeyValueChecker<>(key -> true, s -> true, false, ""));
        return this;
    }

    public XmlFinishedNodeBuilder value(String value) {
        return value(value, "value \"" + value + "\"");
    }

    public XmlFinishedNodeBuilder value(XmlAnyBuilder unused) {
        return value(v -> true, "any value");
    }

    public XmlFinishedNodeBuilder value(String value, String valueDescription) {
        return value(v -> v.equals(value), valueDescription);
    }

    public XmlFinishedNodeBuilder value(Pattern value) {
        return value(value, "value described by pattern \"" + value + "\"");
    }

    public XmlFinishedNodeBuilder value(Pattern value, String valueDescription) {
        return value(v -> value.matcher(v).matches(), valueDescription);
    }

    public XmlFinishedNodeBuilder value(StringChecker value) {
        return value(value, "specific value");
    }

    public XmlFinishedNodeBuilder value(StringChecker value, String valueDescription) {
        valueChecker = value;
        failValueFeedback = valueDescription;
        return this;
    }
}
