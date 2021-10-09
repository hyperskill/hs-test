package org.hyperskill.hstest.testing.expect.xml.builder;

import org.hyperskill.hstest.testing.expect.base.checker.ArrayIndexChecker;
import org.hyperskill.hstest.testing.expect.base.checker.ArrayLengthChecker;
import org.hyperskill.hstest.testing.expect.base.checker.IntegerChecker;

import static org.hyperskill.hstest.testing.expect.xml.XmlChecker.isNode;

public class XmlNodeChildrenBuilder extends XmlFinishedNodeBuilder {

    int currIndex = 0;
    private boolean arrayLengthChanged = false;

    public XmlNodeChildrenBuilder child(XmlBaseBuilder value) {
        return child(value, "child");
    }

    public XmlNodeChildrenBuilder child(XmlBaseBuilder value, String itemDescription) {
        return child(currIndex++, value, itemDescription);
    }

    public XmlNodeChildrenBuilder child(int index, XmlBaseBuilder value) {
        return child(index, value, "child");
    }

    public XmlNodeChildrenBuilder child(int index, XmlBaseBuilder value, String itemDescription) {
        if (calculatedArrayLength != -1) {
            calculatedArrayLength = index + 1;
            arrayLengthChanged = true;
        }
        return child(i -> i == index, value, itemDescription + " at index " + index);
    }

    public XmlNodeChildrenBuilder child(IntegerChecker index, XmlBaseBuilder value) {
        return child(index, value, null);
    }

    public XmlNodeChildrenBuilder child(IntegerChecker index, XmlBaseBuilder value, String itemDescription) {
        if (!arrayLengthChanged) {
            calculatedArrayLength = -1;
        } else {
            arrayLengthChanged = false;
        }
        arrayIndexCheckers.add(new ArrayIndexChecker<>(index, value, true, itemDescription));
        return this;
    }

    public XmlNodeChildrenBuilder everyChild(XmlBaseBuilder itemTemplate) {
        calculatedArrayLength = -1;
        this.itemTemplate = itemTemplate;
        return this;
    }

    public XmlFinishedNodeBuilder anyOtherChild() {
        calculatedArrayLength = -1;
        arrayIndexCheckers.add(new ArrayIndexChecker<>(i -> true, isNode(), false, null));
        return this;
    }

    public XmlNodeChildrenBuilder length(int length) {
        return length(length, "should be equal to " + length);
    }

    public XmlNodeChildrenBuilder length(int length, String feedback) {
        return length(len -> len == length, feedback);
    }

    public XmlNodeChildrenBuilder length(IntegerChecker lengthChecker) {
        return length(lengthChecker, null);
    }

    public XmlNodeChildrenBuilder length(IntegerChecker lengthChecker, String feedback) {
        calculatedArrayLength = -1;
        requiredLength = new ArrayLengthChecker(lengthChecker, feedback);
        return this;
    }

}
