package web.request;

import org.hyperskill.hstest.mocks.web.request.HttpRequest;
import org.junit.Assert;
import org.junit.Test;

import static org.hyperskill.hstest.mocks.web.constants.Methods.GET;

public class TestAnchor {

    @Test
    public void testAnchor() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost1:1234/api/asd");
        Assert.assertEquals(req.getAnchor(), "");
    }

    @Test
    public void testAnchorRoot() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost9:1234/");
        Assert.assertEquals(req.getAnchor(), "");
    }


    @Test
    public void testAnchorNoUrl() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost2:1234");
        Assert.assertEquals(req.getAnchor(), "");
    }

    @Test
    public void testAnchorNoPort() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost3");
        Assert.assertEquals(req.getAnchor(), "");
    }

    @Test
    public void testAnchorNoPortRoot() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost8/");
        Assert.assertEquals(req.getAnchor(), "");
    }

    @Test
    public void testAnchorWithParams() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost4:1234/api/asd?a=1&b=2");
        Assert.assertEquals(req.getAnchor(), "");
    }

    @Test
    public void testAnchorWithAnchor() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost5:1234/api/asdf#anchor1");
        Assert.assertEquals(req.getAnchor(), "anchor1");
    }

    @Test
    public void testAnchorWithParamsAndAnchor() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost6:1234/api/asdf?a=1&b=2#anchor2");
        Assert.assertEquals(req.getAnchor(), "anchor2");
    }
    
}
