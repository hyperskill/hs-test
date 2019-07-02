package org.hyperskill.hstest.dev.stage;

import org.apache.http.entity.ContentType;
import org.hyperskill.hstest.dev.mocks.web.request.HttpRequest;
import org.junit.After;

import java.lang.reflect.Method;

import static org.hyperskill.hstest.dev.common.ReflectionUtils.getMainMethod;

public abstract class SpringTest<T> extends BaseStageTest<T> {

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
        post("/actuator/shutdown", "").send();
    }

    private String constructUrl(String address) {
        if (!address.startsWith("/")) {
            address = "/" + address;
        }
        return "http://localhost:" + port + address;
    }

    public HttpRequest get(String address) {
        return new HttpRequest("GET")
            .setUri(constructUrl(address));
    }

    public HttpRequest post(String address, String content) {
        return new HttpRequest("POST")
            .setUri(constructUrl(address))
            .setContent(content)
            .setContentType(ContentType.APPLICATION_JSON);
    }

    public HttpRequest put(String address, String content) {
        return new HttpRequest("PUT")
            .setUri(constructUrl(address))
            .setContent(content)
            .setContentType(ContentType.APPLICATION_JSON);
    }

    public HttpRequest delete(String address) {
        return new HttpRequest("DELETE")
            .setUri(constructUrl(address));
    }
}
