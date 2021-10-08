package web.request;

import org.hyperskill.hstest.mocks.web.request.HttpRequest;
import org.junit.Assert;
import org.junit.Test;

import static org.hyperskill.hstest.mocks.web.constants.Methods.GET;
import static org.hyperskill.hstest.mocks.web.constants.Schemas.HTTP;

public class TestUri {

    @Test
    public void testDefaultUri() {
        HttpRequest req = new HttpRequest();

        Assert.assertEquals(req.getSchema(), "http");
        Assert.assertEquals(req.getEndpoint(), "/");
        Assert.assertEquals(req.getUri(), "http://localhost/");
    }

    @Test
    public void testGetUri() {
        HttpRequest req = new HttpRequest(GET, "/api/test");
        Assert.assertEquals(req.getUri(), "http://localhost/api/test");
    }

    @Test
    public void testGetParamsUri() {
        HttpRequest req = new HttpRequest(GET, "/api/test").addParam("12", "23");
        Assert.assertEquals(req.getUri(), "http://localhost/api/test?12=23");
    }

    @Test
    public void testGetParamsUri2() {
        HttpRequest req = new HttpRequest(GET, "/api/test")
            .addParam("12", "23")
            .addParam("34", "56");

        Assert.assertEquals(req.getUri(), "http://localhost/api/test?12=23&34=56");
    }

    @Test
    public void testAnchorUri() {
        HttpRequest req = new HttpRequest( "/api/test#link");
        Assert.assertEquals(req.getUri(), "http://localhost/api/test#link");
    }

    @Test
    public void testAnchorAndParamsUri() {
        HttpRequest req = new HttpRequest(GET, "/api/test#link")
            .addParam("1", "2")
            .addParam("3", "4");

        Assert.assertEquals(req.getUri(), "http://localhost/api/test?1=2&3=4#link");
    }

    @Test
    public void testPortUri() {
        HttpRequest req = new HttpRequest("/api/test").setPort(80);
        Assert.assertEquals(req.getUri(), "http://localhost:80/api/test");
    }

    @Test
    public void testHostUri() {
        HttpRequest req = new HttpRequest("/api/test").setHost("127.0.0.1");
        Assert.assertEquals(req.getUri(), "http://127.0.0.1/api/test");
    }

    @Test
    public void testEndpointUri() {
        HttpRequest req = new HttpRequest("/api/test").setEndpoint("/api/new");
        Assert.assertEquals(req.getUri(), "http://localhost/api/new");
    }

    @Test
    public void testEndpointUri2() {
        HttpRequest req = new HttpRequest("/api/test").setEndpoint("api/new");
        Assert.assertEquals(req.getUri(), "http://localhost/api/new");
    }

    @Test
    public void testEndpointUri3() {
        HttpRequest req = new HttpRequest("/api/test").setEndpoint("api/new/");
        Assert.assertEquals(req.getUri(), "http://localhost/api/new/");
    }

    @Test
    public void testEndpointUri4() {
        HttpRequest req = new HttpRequest("api/test").setEndpoint("new");
        Assert.assertEquals(req.getUri(), "http://api/new");
    }

    @Test
    public void testAnchorUri2() {
        HttpRequest req = new HttpRequest("/api/test").setAnchor("ank");
        Assert.assertEquals(req.getUri(), "http://localhost/api/test#ank");
    }

    @Test
    public void testComplexParsing() {
        HttpRequest req = new HttpRequest("https://long.api:99/test/endpoint?12=23&45=56#qwe");
        Assert.assertEquals(req.getUri(), "https://long.api:99/test/endpoint?12=23&45=56#qwe");

        Assert.assertEquals(req.getSchema(), "https");
        Assert.assertEquals(req.getHost(), "long.api");
        Assert.assertEquals(req.getPort(), 99);
        Assert.assertEquals(req.getEndpoint(), "/test/endpoint");
        Assert.assertEquals(req.getParams().size(), 2);
        Assert.assertEquals(req.getParams().get("12"), "23");
        Assert.assertEquals(req.getParams().get("45"), "56");
        Assert.assertEquals(req.getAnchor(), "qwe");
    }

    @Test
    public void testComplexChanging() {
        HttpRequest req = new HttpRequest("https://long.api:99/test/endpoint?12=23&45=56#qwe")
            .setSchema(HTTP)
            .setPort(80)
            .setEndpoint("another")
            .setAnchor("link")
            .setHost("host")
            .addParam("67", "78");

        Assert.assertEquals(req.getUri(), "http://host:80/another?12=23&45=56&67=78#link");
    }

}
