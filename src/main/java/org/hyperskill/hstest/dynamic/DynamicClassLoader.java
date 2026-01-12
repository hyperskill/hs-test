package org.hyperskill.hstest.dynamic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DynamicClassLoader extends ClassLoader {
    private static final Map<String, byte[]> FILE_CACHE = new HashMap<>();

    private final Map<String, Class<?>> savedClasses = new HashMap<>();
    private String searchLocation;

    public DynamicClassLoader(Class<?> clazz) {
        URL location = clazz.getProtectionDomain().getCodeSource().getLocation();
        try {
            searchLocation = new File(location.toURI()).getPath();
        } catch (URISyntaxException ignored) {
            searchLocation = null;
        }
    }

    protected synchronized Class<?> loadClass(String name, boolean resolve)
        throws ClassNotFoundException {
        Class<?> result = findClass(name);
        if (resolve) {
            resolveClass(result);
        }
        return result;
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> result = savedClasses.get(name);

        if (result != null) {
            return result;
        }

        try {
            byte[] classBytes = FILE_CACHE.getOrDefault(name, null);

            if (classBytes == null) {
                File f = findFile(name);
                if (f == null) {
                    return findSystemClass(name);
                }

                classBytes = loadFileAsBytes(f);
                FILE_CACHE.put(name, classBytes);
            }

            result = defineClass(name, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(
                "Cannot load class " + name + ": " + e);
        } catch (ClassFormatError e) {
            throw new ClassNotFoundException(
                "Format of class file incorrect for class " + name + " : " + e);
        }

        savedClasses.put(name, result);
        return result;
    }

    private File findFile(String name) {
        File f = new File(
            searchLocation
                + File.separator
                + name.replace(".", File.separator)
                + ".class"
        );

        if (f.exists()) {
            return f;
        }

        return null;
    }

    private static byte[] loadFileAsBytes(File file) throws IOException {
        byte[] result = new byte[(int) file.length()];
        try (FileInputStream f = new FileInputStream(file)) {
            f.read(result, 0, result.length);
        }
        return result;
    }
}
