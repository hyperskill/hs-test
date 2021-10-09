package org.hyperskill.hstest.testing.expect.xml.builder;

import org.hyperskill.hstest.common.XmlUtils;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.testing.expect.base.checker.ArrayIndexChecker;
import org.hyperskill.hstest.testing.expect.base.checker.ArrayLengthChecker;
import org.hyperskill.hstest.testing.expect.base.checker.KeyValueChecker;
import org.hyperskill.hstest.testing.expect.base.checker.StringChecker;
import org.hyperskill.hstest.testing.expect.xml.ExpectationXmlFeedback;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class XmlFinishedNodeBuilder extends XmlBaseBuilder {

    // Check node tag name
    StringChecker tagChecker = null;
    String failTagFeedback = null;

    // Check node tag name
    StringChecker valueChecker = null;
    String failValueFeedback = null;

    // Check attributes
    final List<KeyValueChecker<StringChecker>> keyValueCheckers = new ArrayList<>();

    // Check children
    protected int calculatedArrayLength = 0;
    XmlBaseBuilder itemTemplate = null;
    ArrayLengthChecker requiredLength = null;
    final List<ArrayIndexChecker<XmlBaseBuilder>> arrayIndexCheckers = new ArrayList<>();

    @Override
    public boolean check(Element elem, ExpectationXmlFeedback feedback) {
        for (KeyValueChecker<StringChecker> checker : keyValueCheckers) {
            checker.matched = false;
        }


        // Checking node tag name

        String tagName = elem.getNodeName();

        if (tagChecker != null) {
            if (!tagChecker.check(tagName)) {

                String childMention = "";
                if (feedback.getPathSize() != 0) {
                    childMention = "child with ";
                }

                feedback.fail("should have "
                    + childMention + failTagFeedback + ", found \"" + tagName + "\"");

                return false;
            }
        }

        feedback.addPath(tagName);

        // Checking node attributes

        NamedNodeMap attributes = elem.getAttributes();

        entries:
        for (int i = 0; i < attributes.getLength(); i++) {
            Attr attr = (Attr) attributes.item(i);
            String key = attr.getName();
            String value = attr.getValue();

            for (KeyValueChecker<StringChecker> checker : keyValueCheckers) {
                if (checker.keyChecker.check(key)) {
                    boolean result = checker.valueChecker.check(value);

                    if (!result) {
                        feedback.fail(checker.failFeedback + ", found value \"" + value + "\"");
                        return false;
                    }

                    checker.matched = true;
                    continue entries;
                }
            }

            feedback.fail("shouldn't have the attribute with a key \"" + key + "\"");
            return false;
        }

        for (KeyValueChecker<StringChecker> checker : keyValueCheckers) {
            if (checker.requireMatch && !checker.matched) {
                feedback.fail(checker.failFeedback);
                return false;
            }
        }


        // parse Element, determine if it has children or not

        for (ArrayIndexChecker<XmlBaseBuilder> checker : arrayIndexCheckers) {
            checker.matched = false;
        }

        if (requiredLength == null && calculatedArrayLength >= 0 && this instanceof XmlNodeChildrenBuilder) {
            ((XmlNodeChildrenBuilder) this).length(calculatedArrayLength);
        }

        NodeList nodes = elem.getChildNodes();
        List<Element> children = new ArrayList<>();
        List<Text> texts = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node item = nodes.item(i);
            if (item instanceof Element) {
                children.add((Element) item);
            }
            if (item instanceof Text) {
                texts.add((Text) item);
            }
        }

        int childrenSize = children.size();
        int textsSize = texts.size();

        if (childrenSize == 0 && textsSize > 1) {
            throw new UnexpectedError("Cannot parse XML:\n\n" + XmlUtils.getPrettyXml(elem));
        }

        if (valueChecker != null && childrenSize > 0) {
            feedback.fail("should be node without children, found " + childrenSize + " children");
            return false;
        }


        // check value if Element has no children

        long childrenRequired = arrayIndexCheckers.stream().filter(c -> c.requireMatch).count();

        if (childrenSize == 0 && childrenRequired == 0) {
            if (valueChecker != null) {
                String xmlValue = "";
                if (textsSize == 1) {
                    xmlValue = texts.get(0).getWholeText();
                }

                if (!valueChecker.check(xmlValue)) {
                    feedback.fail("should have " + failValueFeedback + ", found \"" + xmlValue + "\"");
                    return false;
                }
            }

            feedback.removePath();
            return true;
        }


        // check children if Element has them

        if (requiredLength != null && !requiredLength.lengthChecker.check(childrenSize)) {
            String result = "has an incorrect children size";
            if (requiredLength.feedback != null) {
                result += ": " + requiredLength.feedback + ", found " + childrenSize;
            }
            feedback.fail(result);
            return false;
        }

        entries:
        for (int index = 0; index < childrenSize; index++) {
            Element value = children.get(index);

            if (itemTemplate != null) {
                boolean result = itemTemplate.check(value, feedback);

                if (!result) {
                    return false;
                }
            }

            for (ArrayIndexChecker<XmlBaseBuilder> checker : arrayIndexCheckers) {
                if (checker.indexChecker.check(index)) {
                    boolean result = checker.valueChecker.check(value, feedback);

                    if (!result) {
                        return false;
                    }

                    checker.matched = true;
                    continue entries;
                }
            }

            if (requiredLength != null || itemTemplate != null) {
                continue;
            }

            feedback.fail("shouldn't have the child with index " + index);
            return false;
        }

        for (ArrayIndexChecker<XmlBaseBuilder> checker : arrayIndexCheckers) {
            if (checker.requireMatch && !checker.matched) {
                String result = "is missing a child";
                if (checker.itemDescription != null) {
                    result += ": " + checker.itemDescription;
                }
                feedback.fail(result);
                return false;
            }
        }

        feedback.removePath();
        return true;
    }

}
