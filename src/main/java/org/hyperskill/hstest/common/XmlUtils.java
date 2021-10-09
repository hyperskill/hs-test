package org.hyperskill.hstest.common;

import org.hyperskill.hstest.exception.outcomes.PresentationError;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class XmlUtils {

    public static Element getXml(String content) {
        try {
            DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(SAXParseException e) { }

                @Override
                public void fatalError(SAXParseException e) throws SAXException {
                    throw e;
                }

                @Override
                public void error(SAXParseException e) throws SAXException {
                    throw e;
                }
            });

            ByteArrayInputStream input = new ByteArrayInputStream(
                content.getBytes(StandardCharsets.UTF_8));

            Document document = builder.parse(input);

            return document.getDocumentElement();
        } catch (SAXException ex) {
            throw new PresentationError("Expected XML, got something else.\n"
                + ex.getMessage() + "\n\n" + "Content:\n" + content);

        } catch (IOException | ParserConfigurationException e) {
            throw new UnexpectedError("Error parsing XML: " + e);
        }
    }

    /**
     * Thanks to https://stackoverflow.com/a/33541820/13160001
     */
    public static String getPrettyXml(Element xml) {
        try {
            xml.normalize();
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList) xPath
                .evaluate("//text()[normalize-space()='']", xml, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node node = nodeList.item(i);
                node.getParentNode().removeChild(node);
            }

            // Setup pretty print options
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", 4);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // Return pretty print xml string
            StringWriter stringWriter = new StringWriter();
            transformer.transform(new DOMSource(xml), new StreamResult(stringWriter));
            return Utils.cleanText(stringWriter.toString());
        } catch (Exception ex) {
            throw new UnexpectedError("Error while pretty-printing XML: " + ex);
        }
    }

}
