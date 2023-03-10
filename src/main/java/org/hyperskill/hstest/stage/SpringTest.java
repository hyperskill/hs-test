package org.hyperskill.hstest.stage;

import org.apache.http.entity.ContentType;
import org.hyperskill.hstest.common.FileUtils;
import org.hyperskill.hstest.common.NetworkUtils;
import org.hyperskill.hstest.common.ReflectionUtils;
import org.hyperskill.hstest.dynamic.output.InfiniteLoopDetector;
import org.hyperskill.hstest.dynamic.output.OutputHandler;
import org.hyperskill.hstest.exception.outcomes.ErrorWithFeedback;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.mocks.web.request.HttpRequest;
import org.hyperskill.hstest.testing.Settings;
import org.hyperskill.hstest.testing.runner.SpringApplicationRunner;
import org.junit.After;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hyperskill.hstest.common.Utils.tryManyTimes;
import static org.hyperskill.hstest.mocks.web.constants.Methods.DELETE;
import static org.hyperskill.hstest.mocks.web.constants.Methods.GET;
import static org.hyperskill.hstest.mocks.web.constants.Methods.POST;
import static org.hyperskill.hstest.mocks.web.constants.Methods.PUT;
import static org.hyperskill.hstest.mocks.web.request.HttpRequestExecutor.packUrlParams;

public abstract class SpringTest extends StageTest<Object> {

    private static final int DEFAULT_PORT = 8080;

    private static boolean isTearDown = false;
    private static boolean springRunning = false;
    private static String[] args;

    protected final int port;
    protected String databasePath;

    public static void launchSpring(String[] args) throws Exception {
        SpringTest.args = args;
        startSpring();
    }

    public SpringTest() {
        this(detectPort());
    }

    @Deprecated
    public SpringTest(Class<?> clazz) {
        this();
    }

    public SpringTest(int port) {
        InfiniteLoopDetector.setWorking(false);
        Settings.doResetOutput = false;
        runner = new SpringApplicationRunner();
        this.port = port;
    }

    @Deprecated
    public SpringTest(Class<?> clazz, int port) {
        this(port);
    }

    public SpringTest(String database) {
        this(detectPort(), database);
    }

    @Deprecated
    public SpringTest(Class<?> clazz, String database) {
        this(database);
    }

    public SpringTest(int port, String database) {
        this(port);
        this.databasePath = database;
        replaceDatabase();
    }

    @Deprecated
    public SpringTest(Class<?> clazz, int port, String database) {
        this(port, database);
    }

    private static int detectPort() {
        String[] resourcesDirs = new String[] {
            "resources", "src" + File.separator + "resources"
        };

        for (String resDir : resourcesDirs) {
            File folder = new File(resDir);
            if (!folder.exists() || !folder.isDirectory()) {
                continue;
            }

            File[] filesArray = folder.getAbsoluteFile().listFiles();
            if (filesArray == null) {
                continue;
            }

            List<File> files = new ArrayList<>(Arrays.asList(filesArray));

            while (!files.isEmpty()) {
                File file = files.remove(0);

                try {
                    if (file.isDirectory()) {
                        filesArray = file.getAbsoluteFile().listFiles();
                        if (filesArray != null) {
                            files.addAll(Arrays.asList(filesArray));
                        }
                        continue;
                    }

                    String content = FileUtils.readFile(file.getAbsolutePath());
                    if (content == null) {
                        continue;
                    }

                    try (BufferedReader bufReader = new BufferedReader(new StringReader(content))) {
                        String line;
                        String toSearch = "server.port";
                        while ((line = bufReader.readLine()) != null) {
                            if (line.startsWith(toSearch) && line.contains("=")) {
                                String portNumber = line.substring(line.indexOf("=") + 1).trim();
                                return Integer.parseInt(portNumber);
                            }
                        }
                    }
                } catch (IOException | NumberFormatException ignored) { }
            }
        }

        return DEFAULT_PORT;
    }

    @After
    public void tearDown() {
        isTearDown = true;
        stopSpring();
        if (databasePath != null) {
            revertDatabase();
        }
    }

    public static void startSpring() throws Exception {
        if (!springRunning) {
            String annotationPath = "org.springframework.boot.autoconfigure.SpringBootApplication";
            List<Class<?>> suitableClasses = ReflectionUtils.getClassesAnnotatedWith(annotationPath);
                    //.stream()
                    //.filter(ReflectionUtils::hasMainMethod)
                    //.collect(Collectors.toList());;

            int length = suitableClasses.size();
            if (length == 0) {
                throw new ErrorWithFeedback("No class found with annotation " + annotationPath);
            } else if (length > 1) {
                throw new ErrorWithFeedback(
                        "More than one class found with annotation " + annotationPath +
                                " , please leave only 1 class with this annotation." + "\n" +
                                "Found classes: " + suitableClasses.stream()
                                .map(Class::getCanonicalName)
                                .collect(Collectors.joining(", "))
                );
            }

            if (ReflectionUtils.hasMainMethod(suitableClasses.get(0)))
                ReflectionUtils.getMainMethod(suitableClasses.get(0))
                        .invoke(null, new Object[] {args});
            else {
                Class<?> mainClassKotlin = ReflectionUtils
                        .getAllClassesFromPackage("")
                        .stream()
                        .filter(it -> {
                            if (it.getCanonicalName().equals("ApplicationKt") && ReflectionUtils.hasMainMethod(it)) {
                                return true;
                            }
                            return false;
                        }).collect(Collectors.toList()).get(0);
                ReflectionUtils.getMainMethod(mainClassKotlin)
                        .invoke(null, new Object[] {args});
            }

            //Class<?> springClass = suitableClasses.get(0);
            //Method mainMethod = ReflectionUtils.getMainMethod(springClass);
            //mainMethod.invoke(null, new Object[] {args});
            springRunning = true;
        }
    }

    public void stopSpring() {
        if (springRunning) {
            int status = post("/actuator/shutdown", "").send().getStatusCode();

            if (isTearDown) {
                return;
            }

            if (status != 200) {
                throw new WrongAnswer("Cannot stop Spring application.\n"
                    + "Please make POST \"/actuator/shutdown\" endpoint accessible without authentication.\n"
                    + "The endpoint should return status code 200, returned " + status + ".");
            }

            springRunning = false;

            tryManyTimes(100, 100,
                () -> OutputHandler.getOutput().contains("Shutdown completed.\n"));

            if (!tryManyTimes(100, 100, () -> NetworkUtils.isPortAvailable(port))) {
                throw new UnexpectedError("Cannot stop Spring application, " +
                    "port " + port + " is not freed");
            }
        }
    }

    public void reloadSpring() {
        stopSpring();
        try {
            startSpring();
        } catch (Exception ex) {
            throw new UnexpectedError(ex.getMessage(), ex);
        }
    }

    private void replaceDatabase() {
        String dbFilePath = System.getProperty("user.dir")
            + File.separator + databasePath;

        String dbTempFilePath = dbFilePath + "-real";

        Path dbFile = Paths.get(dbFilePath);
        Path dbTempFile = Paths.get(dbTempFilePath);

        try {
            if (dbTempFile.toFile().exists()) {
                Files.deleteIfExists(dbFile);
            } else if (dbFile.toFile().exists() && !dbTempFile.toFile().exists()) {
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
