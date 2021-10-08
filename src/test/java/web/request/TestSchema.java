package web.request;

import org.hyperskill.hstest.mocks.web.request.HttpRequest;
import org.junit.Assert;
import org.junit.Test;

import static org.hyperskill.hstest.mocks.web.constants.Methods.GET;
import static org.hyperskill.hstest.mocks.web.constants.Schemas.HTTP;
import static org.hyperskill.hstest.mocks.web.constants.Schemas.HTTPS;

public class TestSchema {

    @Test
    public void testHttpSchema() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost1:1234/api/asd");

        Assert.assertEquals(req.getSchema(), "http");
    }

    @Test
    public void testHttpsSchema() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("https://localhost1:1234/api/asd");

        Assert.assertEquals(req.getSchema(), "https");
    }

    @Test
    public void testHttpChangedSchema() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost1:1234/api/asd")
            .setSchema("https");

        Assert.assertEquals(req.getSchema(), "https");
    }

    @Test
    public void testHttpChangedSchema2() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost1:1234/api/asd")
            .setSchema(HTTPS);

        Assert.assertEquals(req.getSchema(), "https");
    }

    @Test
    public void testHttpsChangedSchema() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("https://localhost1:1234/api/asd")
            .setSchema("http");

        Assert.assertEquals(req.getSchema(), "http");
    }

    @Test
    public void testHttpsChangedSchema2() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("https://localhost1:1234/api/asd")
            .setSchema(HTTP);

        Assert.assertEquals(req.getSchema(), "http");
    }

}
