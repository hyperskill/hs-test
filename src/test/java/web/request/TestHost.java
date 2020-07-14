package web.request;

import org.hyperskill.hstest.mocks.web.request.HttpRequest;
import org.junit.Assert;
import org.junit.Test;

import static org.hyperskill.hstest.mocks.web.constants.Methods.GET;

public class TestHost {

    @Test
    public void testHost() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost1:1234/api/asd");
        Assert.assertEquals(req.getHost(), "localhost1");
    }

    @Test
    public void testHostRoot() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost9:1234/");
        Assert.assertEquals(req.getHost(), "localhost9");
    }


    @Test
    public void testHostNoUrl() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost2:1234");
        Assert.assertEquals(req.getHost(), "localhost2");
    }

    @Test
    public void testHostNoPort() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost3");
        Assert.assertEquals(req.getHost(), "localhost3");
    }

    @Test
    public void testHostNoPortRoot() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost8/");
        Assert.assertEquals(req.getHost(), "localhost8");
    }

    @Test
    public void testHostWithParams() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost4:1234/api/asd?a=1&b=2");
        Assert.assertEquals(req.getHost(), "localhost4");
    }

    @Test
    public void testHostWithAnchor() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost5:1234/api/asdf#anchor1");
        Assert.assertEquals(req.getHost(), "localhost5");
    }

    @Test
    public void testHostWithParamsAndAnchor() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost6:1234/api/asdf?a=1&b=2#anchor1");
        Assert.assertEquals(req.getHost(), "localhost6");
    }

}
