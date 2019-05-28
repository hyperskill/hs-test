package org.hyperskill.hstest.android.stage;

import org.hyperskill.hstest.android.testcase.CheckResult;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AndroidTest extends BaseStageTest {

    private static String stdout = "";
    private static String stderr = "";

    private static String fatalError(String reason) {
        String msg = "Fatal error during testing" +
            ", please send the report to Hyperskill team.";
        return msg + "\n\n" +
            reason + ".\n\n" +
            "stdout:\n\n" + stdout + "\n\n" +
            "stderr:\n\n" + stderr;
    }


    private static String fetchStream(InputStream stream) {
        InputStreamReader isr = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ( (line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
                System.out.println(line);
            }
        } catch (IOException ignored) { }

        return sb.toString();
    }

    private static Process startProcess(String winCommand, String linuxCommand) {
        boolean isWindows = System.getProperty("os.name")
            .toLowerCase().startsWith("windows");

        String command = isWindows
            ? "cmd.exe /c " + winCommand
            : "sh -c " + linuxCommand;

        File f = new File(new File("").getAbsolutePath());

        ProcessBuilder builder = new ProcessBuilder();
        builder.directory(f.getParentFile().getParentFile());
        builder.command(command.split("\\s"));

        try {
            return builder.start();
        } catch (IOException ignored) {
            return null;
        }
    }

    private static void execInShell(String winCommand, String linuxCommand) {
        Process process = startProcess(winCommand, linuxCommand);
        if (process == null) return;

        stdout = fetchStream(process.getInputStream());
        stderr = fetchStream(process.getErrorStream());

        try {
            process.waitFor();
        } catch (InterruptedException ignored) { }
    }

    private static String getGradleTask() {
        File file = new File(new File("").getAbsolutePath());

        String currentPath = file.getAbsolutePath();
        String rootProject = file.getParentFile().getParentFile().getAbsolutePath();

        return ":" +
            currentPath.substring(rootProject.length() + 1)
                .replace(File.separator, "-")
                .replace(" ", "_") +
            ":connectedAndroidTest";
    }

    @Override
    final public CheckResult check(Object clue) {

        execInShell(
            "gradlew.bat " + getGradleTask(),
            "./gradlew " + getGradleTask()
        );

        //System.out.println(stdout);
        System.err.println(stderr);

        if (!stderr.isEmpty()) {
            if (stderr.contains(".DeviceException: No connected devices!")) {
                return CheckResult.FALSE(
                    "Error during testing\n\n" +
                        "Can't find a device to run the application. " +
                        "Please, connect your Android phone or turn on the emulator.");
            }

            if (stderr.contains(".DeviceException: No online devices found.")) {
                return CheckResult.FALSE(
                    "Error during testing\n\n" +
                        "A device was found but tests could not connect to it. " +
                        "Please, reconnect your Android phone or " +
                        "turn off and turn on the emulator.");
            }

            if (stderr.contains("> There were failing tests.")) {

                try {
                    stdout = stdout.trim();
                    stderr = stderr.trim();

                    stdout = stdout
                        .replace("\r\n", "\n")
                        .replace("\r", "\n");

                    String[] lines = stdout.split("\n");

                    List<String> errorText = new ArrayList<>();
                    boolean isErrorStarted = false;
                    int testNum = 0;
                    String testNumMark = "INSTRUMENTATION_STATUS: current=";

                    for (String line: lines) {

                        line = line.trim();

                        if (line.contains(testNumMark)) {
                            testNum = Integer.parseInt(line.split(testNumMark)[1]);
                        }

                        if (line.endsWith("\u001b[31mFAILED \u001b[0m")) {
                            isErrorStarted = true;
                        }

                        if (isErrorStarted) {
                            errorText.add(line);
                        }

                        if (isErrorStarted && line.isEmpty()) {
                            break;
                        }

                    }

                    if (testNum == 0) {
                        return CheckResult.FALSE(
                            fatalError("Can't find failed test")
                        );
                    }

                    String[] parseMethodName = errorText.get(0).split("\\[");
                    parseMethodName = parseMethodName[0].split(">");

                    String className = parseMethodName[0].trim();
                    String methodName = parseMethodName[1].trim();


                    String error = errorText.get(1);
                    String expected = errorText.get(2);

                    error = error.substring(error.indexOf(":") + 1).trim();
                    expected = expected.substring(expected.indexOf(":") + 1).trim();

                    return CheckResult.FALSE(
                        "Test #" + testNum + " failed\n\n" +
                            "You can find what it tests under " +
                            "androidTest folder in file explorer." + "\n\n" +
                            "Test class name: " + className + "\n" +
                            "Test method name: " + methodName + "\n\n" +
                            "Error text: " + error + "\n" +
                            "Expected: " + expected
                    );
                } catch (Exception ex) {
                    return CheckResult.FALSE(
                        fatalError("Error parsing Android tests output")
                    );
                }

            }

            return CheckResult.FALSE(
                fatalError("Unknown error")
            );
        }

        return CheckResult.TRUE;
    }
}
