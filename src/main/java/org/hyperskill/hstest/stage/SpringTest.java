package org.hyperskill.hstest.stage;

import org.apache.http.entity.ContentType;
import org.hyperskill.hstest.common.ReflectionUtils;
import org.hyperskill.hstest.common.Utils;
import org.hyperskill.hstest.mocks.web.request.HttpRequest;
import org.junit.After;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.hyperskill.hstest.mocks.web.constants.Methods.DELETE;
import static org.hyperskill.hstest.mocks.web.constants.Methods.GET;
import static org.hyperskill.hstest.mocks.web.constants.Methods.POST;
import static org.hyperskill.hstest.mocks.web.constants.Methods.PUT;
import static org.hyperskill.hstest.mocks.web.request.HttpRequestExecutor.packUrlParams;

public abstract class SpringTest<T> extends StageTest<T> {

    private static boolean springRunning = false;
    private static Class<?> springClass;
    private static String[] args;

    protected final int port;
    protected String databasePath;

    public static void main(String[] args) throws Exception {
        SpringTest.args = args;
        startSpring();
    }

    public SpringTest(Class<?> clazz, int port) {
        super(SpringTest.class);
        springClass = clazz;
        needReloadClass = false;
        this.port = port;
    }

    public SpringTest(Class<?> clazz, int port, String database) {
        this(clazz, port);
        this.databasePath = database;
        replaceDatabase();
    }

    @After
    public void tearDown() {
        stopSpring();
        if (databasePath != null) {
            revertDatabase();
        }
    }

    public static void startSpring() throws Exception {
        if (!springRunning) {
            Method mainMethod = ReflectionUtils.getMainMethod(springClass);
            mainMethod.invoke(null, new Object[] {args});
            springRunning = true;
        }
    }

    public void stopSpring() {
        if (springRunning) {
            post("/actuator/shutdown", "").send();
            Utils.sleep(1500); // should wait a bit to ensure shutdown
            springRunning = false;
        }
    }

    public void reloadSpring() {
        stopSpring();
        try {
            startSpring();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    private void replaceDatabase() {
        String dbFilePath = System.getProperty("user.dir")
            + File.separator + databasePath;

        String dbTempFilePath = dbFilePath + "-real";

        Path dbFile = Paths.get(dbFilePath);
        Path dbTempFile = Paths.get(dbTempFilePath);

        try {
            if (dbFile.toFile().isFile() && !dbTempFile.toFile().exists()) {
                Files.move(dbFile, dbTempFile);
            }
        } catch (IOException ignored) { }
    }

    private void revertDatabase() {
        String dbFilePath = System.getProperty("user.dir")
            + File.separator + databasePath;

        String dbTempFilePath = dbFilePath + "-real";

        Path dbFile = Paths.get(dbFilePath);
        Path dbTempFile = Paths.get(dbTempFilePath);

        try {
            Files.deleteIfExists(dbFile);
            if (dbTempFile.toFile().isFile()) {
                Files.move(dbTempFile, dbFile);
            }
        } catch (IOException ignored) { }
    }

    public String constructUrl(String address) {
        String delim = "/";
        if (!address.startsWith(delim)) {
            address = delim + address;
        }
        return "http://localhost:" + port + address;
    }

    public HttpRequest get(String address) {
        return new HttpRequest(GET)
            .setUri(constructUrl(address));
    }

    public HttpRequest post(String address, String content) {
        return new HttpRequest(POST)
            .setUri(constructUrl(address))
            .setContent(content)
            .setContentType(ContentType.APPLICATION_JSON);
    }

    public HttpRequest post(String address, Map<String, String> params) {
        return new HttpRequest(POST)
            .setUri(constructUrl(address))
            .setContent(packUrlParams(params))
            .setContentType(ContentType.APPLICATION_FORM_URLENCODED);
    }

    public HttpRequest put(String address, String content) {
        return new HttpRequest(PUT)
            .setUri(constructUrl(address))
            .setContent(content)
            .setContentType(ContentType.APPLICATION_JSON);
    }

    public HttpRequest put(String address, Map<String, String> params) {
        return new HttpRequest(PUT)
            .setUri(constructUrl(address))
            .setContent(packUrlParams(params))
            .setContentType(ContentType.APPLICATION_FORM_URLENCODED);
    }

    public HttpRequest delete(String address) {
        return new HttpRequest(DELETE)
            .setUri(constructUrl(address));
    }
}
