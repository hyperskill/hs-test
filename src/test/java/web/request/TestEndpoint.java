package web.request;

import org.hyperskill.hstest.mocks.web.request.HttpRequest;
import org.junit.Assert;
import org.junit.Test;

import static org.hyperskill.hstest.mocks.web.constants.Methods.GET;

public class TestEndpoint {

    @Test
    public void testEndpoint() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost:1234/api/asd");
        Assert.assertEquals(req.getEndpoint(), "/api/asd");
    }

    @Test
    public void testEndpointRoot() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost:1234/");
        Assert.assertEquals(req.getEndpoint(), "/");
    }

    @Test
    public void testEndpointNoUrl() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost:1234");
        Assert.assertEquals(req.getEndpoint(), "/");
    }

    @Test
    public void testEndpointNoPort() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost");
        Assert.assertEquals(req.getEndpoint(), "/");
    }

    @Test
    public void testEndpointNoPortRoot() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost8/");
        Assert.assertEquals(req.getEndpoint(), "/");
    }

    @Test
    public void testEndpointWithParams() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost:1234/api/asd?a=1&b=2");
        Assert.assertEquals(req.getEndpoint(), "/api/asd");
    }

    @Test
    public void testEndpointWithAnchor() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost:1234/api/asdf#anchor1");
        Assert.assertEquals(req.getEndpoint(), "/api/asdf");
    }

    @Test
    public void testEndpointWithParamsAndAnchor() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost:1234/api/asdf?a=1&b=2#anchor1");
        Assert.assertEquals(req.getEndpoint(), "/api/asdf");
    }

}
