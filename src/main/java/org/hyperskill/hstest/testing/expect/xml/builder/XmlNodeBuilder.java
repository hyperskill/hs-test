package org.hyperskill.hstest.testing.expect.xml.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlNodeBuilder extends XmlFinishedNodeBuilder {

    Map<String, String> attributes = new HashMap<>();
    List<XmlBaseBuilder> children = new ArrayList<>();

    public XmlNodeBuilder attr(String key, String value) {
        attributes.put(key, value);
        return this;
    }

    public XmlNodeBuilder node(XmlBaseBuilder builder) {
        children.add(builder);
        return this;
    }



}
