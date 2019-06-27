package org.hyperskill.hstest.dev.stage;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hyperskill.hstest.dev.common.Utils;
import org.hyperskill.hstest.dev.mocks.web.response.HttpResponse;
import org.junit.After;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Map;

import static org.hyperskill.hstest.dev.common.ReflectionUtils.getMainMethod;

public abstract class SpringTest extends BaseStageTest {

    private static boolean springRunning = false;
    private static Class<?> springClass;
    private int port;

    public static void main(String[] args) throws Exception {
        if (!springRunning) {
            Method mainMethod = getMainMethod(springClass);
            mainMethod.invoke(null, new Object[] { args });
            springRunning = true;
        }
    }

    public SpringTest(Class<?> clazz, int port) {
        super(SpringTest.class);
        springClass = clazz;
        needResetStaticFields = false;
        this.port = port;
    }

    @After
    public void stopSpring() {
        POST("/actuator/shutdown", "");
    }

    private String constructUrl(String address) {
        if (!address.startsWith("/")) {
            address = "/" + address;
        }
        return "http://localhost:" + port + address;
    }

    private HttpResponse executeRequest(HttpRequestBase request) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            org.apache.http.HttpResponse httpResponse = client.execute(request);

            int statusCode = httpResponse.getStatusLine().getStatusCode();

            BufferedReader rd = new BufferedReader(
                new InputStreamReader(httpResponse.getEntity().getContent()));

            StringBuilder contentBuilder = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                contentBuilder.append(line);
            }

            String content = Utils.normalizeLineEndings(contentBuilder.toString());
            request.releaseConnection();

            return new HttpResponse(statusCode, content);
        } catch (IOException e) {
            return null;
        }
    }

    private String packUrlParams(Map<String, String> getParams) {
        if (getParams == null || getParams.isEmpty()) {
            return "";
        }
        StringBuilder paramsBuilder = new StringBuilder();
        for (String key : getParams.keySet()) {
            paramsBuilder.append(key);
            paramsBuilder.append("=");
            paramsBuilder.append(getParams.get(key));
            paramsBuilder.append("&");
        }
        String params = paramsBuilder.toString();
        params = params.substring(0, params.length() - 1);
        return params;
    }

    public HttpResponse GET(String address) {
        HttpGet get = new HttpGet(constructUrl(address));
        return executeRequest(get);
    }

    public HttpResponse GET(String address, Map<String, String> getParams) {
        if (getParams == null || getParams.size() == 0) {
            return GET(address);
        }
        return GET(address + "?" + packUrlParams(getParams));
    }

    public HttpResponse POST(String address, String content, ContentType type) {
        HttpPost post = new HttpPost(constructUrl(address));
        post.setEntity(new StringEntity(content, type));
        return executeRequest(post);
    }

    public HttpResponse POST(String address, String content) {
        return POST(address, content, ContentType.APPLICATION_JSON);
    }

    public HttpResponse POST(String address, Map<String, String> formParams) {
        return POST(address, packUrlParams(formParams), ContentType.APPLICATION_FORM_URLENCODED);
    }

    public HttpResponse PUT(String address, String content, ContentType type) {
        HttpPut put = new HttpPut(constructUrl(address));
        put.setEntity(new StringEntity(content, type));
        return executeRequest(put);
    }

    public HttpResponse PUT(String address, String content) {
        return PUT(address, content, ContentType.APPLICATION_JSON);
    }

    public HttpResponse PUT(String address, Map<String, String> formParams) {
        return PUT(address, packUrlParams(formParams), ContentType.APPLICATION_FORM_URLENCODED);
    }

    public HttpResponse DELETE(String address) {
        HttpDelete delete = new HttpDelete(constructUrl(address));
        return executeRequest(delete);
    }
}
