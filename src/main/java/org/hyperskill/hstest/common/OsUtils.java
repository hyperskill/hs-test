package org.hyperskill.hstest.common;

import java.util.Locale;

public class OsUtils {
    private OsUtils() { }

    public enum OSType {
        Windows, MacOS, Linux, Other
    }

    protected static OSType detectedOS;

    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String os = System.getProperty(
                "os.name", "generic")
                .toLowerCase(Locale.ENGLISH);
            if (os.contains("mac") || os.contains("darwin")) {
                detectedOS = OSType.MacOS;
            } else if (os.contains("win")) {
                detectedOS = OSType.Windows;
            } else if (os.contains("nux")) {
                detectedOS = OSType.Linux;
            } else {
                detectedOS = OSType.Other;
            }
        }
        return detectedOS;
    }

    public static boolean isWindows() {
        return getOperatingSystemType() == OSType.Windows;
    }

    public static boolean isLinux() {
        return getOperatingSystemType() == OSType.Linux;
    }

    public static boolean isMac() {
        return getOperatingSystemType() == OSType.MacOS;
    }

}
