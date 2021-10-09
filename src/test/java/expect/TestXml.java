package expect;

import org.hyperskill.hstest.exception.outcomes.PresentationError;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.junit.Test;

import static java.util.regex.Pattern.compile;
import static org.hyperskill.hstest.testing.expect.Expectation.expect;
import static org.hyperskill.hstest.testing.expect.xml.XmlChecker.any;
import static org.hyperskill.hstest.testing.expect.xml.XmlChecker.isNode;
import static util.AssertUtils.assertThrows;

public class TestXml {

    @Test
    public void testXmlAnyElements() {
        expect("<block></block>").asXml().check(any());
    }

    @Test
    public void testWrongXml() {
        assertThrows(
            () -> expect("<block></block").asXml().check(any()),
            PresentationError.class,
            "Expected XML, got something else.\n" +
                "XML document structures must start and end within the same entity.\n" +
                "\n" +
                "Content:\n" +
                "<block></block");
    }

    @Test
    public void testXmlNodeElements() {
        expect("<block></block>").asXml().check(isNode());
    }

    @Test
    public void testXmlNodeTag() {
        expect("<block></block>").asXml().check(isNode("block"));
    }

    @Test
    public void testXmlNodeTag2() {
        expect("<block></block>").asXml().check(isNode().tag("block"));
    }

    @Test
    public void testXmlNodeTag3() {
        expect("<block></block>").asXml().check(isNode().tag(compile("b.o.k")));
    }

    @Test
    public void testXmlNodeTag4() {
        expect("<block></block>").asXml().check(isNode().tag(t -> t.startsWith("blo")));
    }

    @Test
    public void testXmlNodeElementWithValue() {
        expect("<block>123</block>").asXml().check(isNode());
    }

    @Test
    public void testXmlNodeElementWithValue2() {
        expect("<block>123</block>").asXml().check(isNode().value("123"));
    }

    @Test
    public void testXmlNodeElementWithValue3() {
        expect("<block>123</block>").asXml().check(isNode().value(compile("[0-9]+")));
    }

    @Test
    public void testXmlNodeElementWithValue4() {
        expect("<block>123</block>").asXml().check(isNode().value(v -> v.length() == 3));
    }

    @Test
    public void testXmlNodeElementWithChildren() {
        expect("<block><qwe>12</qwe><wer>234</wer></block>").asXml().check(isNode().anyOtherChild());
    }

    @Test
    public void testXmlNodeElementWithoutChildren() {
        expect("<block></block>").asXml().check(isNode().anyOtherChild());
    }

    @Test
    public void testXmlNodeElementWithChildren2() {
        expect("<block><qwe>12</qwe><wer>234</wer></block>").asXml().check(isNode(any()));
    }

    @Test
    public void testXmlNodeElementWithAttributes() {
        expect("<block qwe='wer'></block>").asXml().check(isNode().anyOtherAttributes());
    }

    @Test
    public void testXmlNodeElementWithoutAttributes() {
        expect("<block></block>").asXml().check(isNode().anyOtherAttributes());
    }

    @Test
    public void testXmlNodeElementWithAttributes2() {
        expect("<block qwe='wer'></block>").asXml().check(isNode(any()));
    }

    @Test
    public void testXmlCheckAttribute() {
        expect("<block qwe='wer'></block>").asXml().check(isNode().attr("qwe", "wer"));
    }

    @Test
    public void testXmlCheckAttribute2() {
        expect("<block qwe='wer' ert='rty'></block>").asXml().check(
            isNode("block")
                .attr("qwe", "wer")
                .attr("ert", "rty")
        );
    }

    @Test
    public void testXmlCheckChildren() {
        expect("<block><qwe>12</qwe><wer>234</wer></block>").asXml().check(
            isNode("block")
                .child(isNode("qwe").value("12"))
                .child(isNode("wer").value("234"))
        );
    }

    @Test
    public void testChildren() {
        expect("<person>\n" +
            "   <sex>female</sex>\n" +
            "   <firstname>Anna</firstname>\n" +
            "   <lastname>Smith</lastname>\n" +
            "</person>").asXml().check(

                isNode()
                    .tag("person")
                    .child(isNode("sex").value("female"))
                    .child(isNode("firstname").value("Anna"))
                    .child(isNode("lastname").value("Smith"))
        );
    }

    @Test
    public void testChildrenTemplate() {
        expect("<messages>\n" +
            "   <note id=\"501\">\n" +
            "      <to>Tove</to>\n" +
            "      <from>Jani</from>\n" +
            "      <heading>Notice</heading>\n" +
            "      <body>Don't forget about me!</body>\n" +
            "   </note>\n" +
            "   <note id=\"502\">\n" +
            "      <to>Jani</to>\n" +
            "      <from>Tove</from>\n" +
            "      <heading>Re: Notice</heading>\n" +
            "      <body>I wouldn't</body>\n" +
            "   </note>\n" +
            "</messages>").asXml().check(

                isNode("messages")
                    .length(2)
                    .everyChild(
                        isNode("note")
                            .attr("id", compile("[0-9]+"))
                            .child(isNode("to").value(any()))
                            .child(isNode("from").value(any()))
                            .child(isNode("heading").value(any()))
                            .child(isNode("body").value(any())))
        );
    }

    @Test
    public void testXmlWrongTag() {
        assertThrows(
            () -> expect("<block></block>").asXml().check(isNode("tag")),
            WrongAnswer.class,
            "The XML root node should have tag \"tag\", found \"block\"\n" +
                "\n" +
                "Full XML:\n" +
                "<block/>");
    }

    @Test
    public void testXmlWrongTag2() {
        assertThrows(
            () -> expect("<block></block>").asXml().check(isNode().tag("tag")),
            WrongAnswer.class,
            "The XML root node should have tag \"tag\", found \"block\"\n" +
                "\n" +
                "Full XML:\n" +
                "<block/>");
    }

    @Test
    public void testXmlWrongNodeTag3() {
        assertThrows(
            () -> expect("<block></block>").asXml().check(isNode().tag(compile("b.o."))),
            WrongAnswer.class,
            "The XML root node should have tag described by pattern \"b.o.\", found \"block\"\n" +
                "\n" +
                "Full XML:\n" +
                "<block/>");
    }

    @Test
    public void testXmlWrongNodeTag4() {
        assertThrows(
            () -> expect("<block></block>").asXml().check(isNode().tag(t -> t.startsWith("bla"))),
            WrongAnswer.class,
            "The XML root node should have specific tag, found \"block\"\n" +
                "\n" +
                "Full XML:\n" +
                "<block/>");
    }

    @Test
    public void testXmlWrongNodeTag5() {
        assertThrows(
            () -> expect("<block></block>").asXml().check(
                isNode().tag(t -> t.startsWith("bla"), "tag that starts with \"bla\"")),
            WrongAnswer.class,
            "The XML root node should have tag that starts with \"bla\", found \"block\"\n" +
                "\n" +
                "Full XML:\n" +
                "<block/>");
    }

    @Test
    public void testXmlWrongNodeElementWithValue2() {
        assertThrows(
            () -> expect("<block>123</block>").asXml().check(isNode().value("12")),
            WrongAnswer.class,
            "The XML node at path \"/block\" should have value \"12\", found \"123\"\n" +
                "\n" +
                "Full XML:\n" +
                "<block>123</block>");
    }

    @Test
    public void testXmlWrongNodeElementWithValue3() {
        assertThrows(
            () -> expect("<block>123x</block>").asXml().check(isNode().value(compile("[0-9]+"))),
            WrongAnswer.class,
            "The XML node at path \"/block\" should have value described by pattern \"[0-9]+\", found \"123x\"\n" +
                "\n" +
                "Full XML:\n" +
                "<block>123x</block>");
    }

    @Test
    public void testXmlWrongNodeElementWithValue4() {
        assertThrows(
            () -> expect("<block>123</block>").asXml().check(isNode().value(v -> v.length() == 2)),
            WrongAnswer.class,
            "The XML node at path \"/block\" should have specific value, found \"123\"\n" +
                "\n" +
                "Full XML:\n" +
                "<block>123</block>");
    }

    @Test
    public void testXmlWrongNodeElementWithValue5() {
        assertThrows(
            () -> expect("<block>123</block>").asXml().check(
                isNode().value(v -> v.length() == 2, "value with length 2")),
            WrongAnswer.class,
            "The XML node at path \"/block\" should have value with length 2, found \"123\"\n" +
                "\n" +
                "Full XML:\n" +
                "<block>123</block>");
    }

    @Test
    public void testXmlWrongNodeElementWithChildren() {
        assertThrows(
            () -> expect("<block><qwe>12</qwe></block>").asXml().check(
                isNode().child(isNode("qwer"))),
            WrongAnswer.class,
            "The XML node at path \"/block\" should have child with tag \"qwer\", found \"qwe\"\n" +
                "\n" +
                "Full XML:\n" +
                "<block>\n" +
                "    <qwe>12</qwe>\n" +
                "</block>");
    }

    @Test
    public void testXmlWrongNodeElementWithChildrenValue() {
        assertThrows(
            () -> expect("<block><qwe>12</qwe></block>").asXml().check(
                isNode().child(isNode("qwe").value("123"))),
            WrongAnswer.class,
            "The XML node at path \"/block/qwe\" should have value \"123\", found \"12\"\n" +
                "\n" +
                "Full XML:\n" +
                "<block>\n" +
                "    <qwe>12</qwe>\n" +
                "</block>");
    }

    @Test
    public void testXmlWrongNodeElementWithoutChildren() {
        assertThrows(
            () -> expect("<block></block>").asXml().check(isNode().child(isNode("child"))),
            WrongAnswer.class,
            "The XML node at path \"/block\" has an incorrect children size: should be equal to 1, found 0\n" +
                "\n" +
                "Full XML:\n" +
                "<block/>");
    }

    @Test
    public void testXmlWrongNodeElementWithChildren2() {
        assertThrows(
            () -> expect("<block><qwe>12</qwe><wer>234</wer></block>").asXml().
                check(isNode().child(i -> i == 1, isNode("ert")).anyOtherChild()),
            WrongAnswer.class,
            "The XML node at path \"/block\" should have child with tag \"ert\", found \"wer\"\n");
    }

    @Test
    public void testXmlWrongNodeElementWithAttributes() {
        assertThrows(
            () -> expect("<block qwe='wer'></block>").asXml().check(isNode().attr("we", "12")),
            WrongAnswer.class,
            "The XML node at path \"/block\" shouldn't have the attribute with a key \"qwe\"\n");
    }

    @Test
    public void testXmlWrongNodeElementWithAttributes2() {
        assertThrows(
            () -> expect("<block></block>").asXml().check(isNode().attr("we", "12")),
            WrongAnswer.class,
            "The XML node at path \"/block\" should contain " +
                "an attribute with a key \"we\" and a value \"12\"");
    }

    @Test
    public void testXmlWrongNodeElementWithAttributes3() {
        assertThrows(
            () -> expect("<block qwe='wer'></block>").asXml().check(
                isNode().attr("qwe", compile(".."))),
            WrongAnswer.class,
            "The XML node at path \"/block\" should contain " +
                "an attribute with a key \"qwe\" and a value described by pattern \"..\"");
    }

    @Test
    public void testWrongChildren() {
        assertThrows(
            () -> expect("<person>\n" +
                "   <sex>female</sex>\n" +
                "   <firstname>Anna</firstname>\n" +
                "   <lastname>Smith</lastname>\n" +
                "</person>").asXml().check(

                isNode()
                    .tag("person")
                    .child(isNode("sex").value("female"))
                    .child(isNode("firstname").value("Hannah"))
                    .child(isNode("lastname").value("Smith"))
            ),
            WrongAnswer.class,
            "The XML node at path \"/person/firstname\" " +
                "should have value \"Hannah\", found \"Anna\"");
    }

    @Test
    public void testWrongChildrenTemplateLength() {
        assertThrows(
            () -> expect("<messages>\n" +
                "   <note id=\"501\">\n" +
                "      <to>Tove</to>\n" +
                "      <from>Jani</from>\n" +
                "      <heading>Notice</heading>\n" +
                "      <body>Don't forget about me!</body>\n" +
                "   </note>\n" +
                "   <note id=\"502\">\n" +
                "      <to>Jani</to>\n" +
                "      <from>Tove</from>\n" +
                "      <heading>Re: Notice</heading>\n" +
                "      <body>I wouldn't</body>\n" +
                "   </note>\n" +
                "</messages>").asXml().check(

                isNode("messages")
                    .length(3)
                    .everyChild(
                        isNode("note")
                            .attr("id", compile("[0-9]+"))
                            .child(isNode("to").value(any()))
                            .child(isNode("from").value(any()))
                            .child(isNode("heading").value(any()))
                            .child(isNode("body").value(any())))
            ),
            WrongAnswer.class,
            "The XML node at path \"/messages\" has " +
                "an incorrect children size: should be equal to 3, found 2");
    }

    @Test
    public void testWrongChildrenTemplate2() {
        assertThrows(
            () -> expect("<messages>\n" +
                "   <note id=\"501\">\n" +
                "      <to>Tove</to>\n" +
                "      <from>Jani</from>\n" +
                "      <heading>Notice</heading>\n" +
                "      <body>Don't forget about me!</body>\n" +
                "   </note>\n" +
                "   <note id=\"502z\">\n" +
                "      <to>Jani</to>\n" +
                "      <from>Tove</from>\n" +
                "      <heading>Re: Notice</heading>\n" +
                "      <body>I wouldn't</body>\n" +
                "   </note>\n" +
                "</messages>").asXml().check(

                isNode("messages")
                    .everyChild(
                        isNode("note")
                            .attr("id", compile("[0-9]+"))
                            .child(isNode("to").value(any()))
                            .child(isNode("from").value(any()))
                            .child(isNode("heading").value(any()))
                            .child(isNode("body").value(any())))
            ),
            WrongAnswer.class,
            "The XML node at path \"/messages/note\" should contain " +
                "an attribute with a key \"id\" " +
                "and a value described by pattern \"[0-9]+\", found value \"502z\"");
    }

}
