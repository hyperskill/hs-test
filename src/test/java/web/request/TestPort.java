package web.request;

import org.hyperskill.hstest.mocks.web.request.HttpRequest;
import org.junit.Assert;
import org.junit.Test;

import static org.hyperskill.hstest.mocks.web.constants.Methods.GET;

public class TestPort {

    @Test
    public void testPort() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost1:124/api/asd");
        Assert.assertEquals(req.getPort(), 124);
    }

    @Test
    public void testPortRoot() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost9:678/");
        Assert.assertEquals(req.getPort(), 678);
    }


    @Test
    public void testPortNoUrl() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost2:1234");
        Assert.assertEquals(req.getPort(), 1234);
    }

    @Test
    public void testPortNoPort() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost3");
        Assert.assertEquals(req.getPort(), -1);
    }

    @Test
    public void testPortNoPortRoot() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost8/");
        Assert.assertEquals(req.getPort(), -1);
    }

    @Test
    public void testPortWithParams() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost4:12343/api/asd?a=1&b=2");
        Assert.assertEquals(req.getPort(), 12343);
    }

    @Test
    public void testPortWithAnchor() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost5:34/api/asdf#anchor1");
        Assert.assertEquals(req.getPort(), 34);
    }

    @Test
    public void testPortWithParamsAndAnchor() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost6:12234/api/asdf?a=1&b=2#anchor1");
        Assert.assertEquals(req.getPort(), 12234);
    }

}
