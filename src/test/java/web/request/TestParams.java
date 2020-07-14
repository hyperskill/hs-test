package web.request;

import org.hyperskill.hstest.mocks.web.request.HttpRequest;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hyperskill.hstest.mocks.web.constants.Methods.GET;

public class TestParams {

    @Test
    public void testParams() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost1:1234/api/asd");
        Assert.assertEquals(req.getParams().size(), 0);
    }

    @Test
    public void testParamsRoot() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost9:1234/");
        Assert.assertEquals(req.getParams().size(), 0);
    }


    @Test
    public void testParamsNoUrl() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost2:1234");
        Assert.assertEquals(req.getParams().size(), 0);
    }

    @Test
    public void testParamsNoPort() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost3");
        Assert.assertEquals(req.getParams().size(), 0);
    }

    @Test
    public void testParamsNoPortRoot() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost8/")
            .addParam("a", "2");
        Assert.assertEquals(req.getParams().size(), 1);
        Assert.assertEquals(req.getParams().get("a"), "2");
    }

    @Test
    public void testParamsWithParams() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost4:1234/api/asd?a=1&b=2")
            .addParam("c", "3");

        Assert.assertEquals(req.getParams().size(), 3);
        Assert.assertEquals(req.getParams().get("a"), "1");
        Assert.assertEquals(req.getParams().get("b"), "2");
        Assert.assertEquals(req.getParams().get("c"), "3");
    }

    @Test
    public void testParamsWithAnchor() {
        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost5:1234/api/asdf#anchor1");
        Assert.assertEquals(req.getParams().size(), 0);
    }

    @Test
    public void testParamsWithParamsAndAnchor() {
        Map<String, String> params = new HashMap<>();
        params.put("a", "2");
        params.put("c", "3");

        HttpRequest req = new HttpRequest(GET)
            .setUri("http://localhost6:1234/api/asdf?a=1&b=2#anchor2")
            .addParams(params);

        Assert.assertEquals(req.getParams().size(), 3);
        Assert.assertEquals(req.getParams().get("a"), "2");
        Assert.assertEquals(req.getParams().get("b"), "2");
        Assert.assertEquals(req.getParams().get("c"), "3");
    }
    
}
