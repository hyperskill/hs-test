package org.hyperskill.hstest.dynamic;

import org.hyperskill.hstest.exception.outcomes.UnexpectedError;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Class that searches for all classes inside a package
 * Thanks to https://stackoverflow.com/a/22462785
 */
public class ClassSearcher {
    /**
     * Private helper method
     *
     * @param directory
     *            The directory to start with
     * @param pckgname
     *            The package name to search for. Will be needed for getting the
     *            Class object.
     * @param classes
     *            if a file isn't loaded but still is in the directory
     * @throws ClassNotFoundException
     */
    private static void checkDirectory(File directory, String pckgname,
                                       ArrayList<Class<?>> classes) {
        File tmpDirectory;

        if (directory.exists() && directory.isDirectory()) {
            final String[] files = directory.list();

            for (final String file : files) {
                if (file.endsWith(".class")) {
                    try {
                        String className = pckgname + '.' + file.substring(0, file.length() - 6);
                        Class<?> clazz = Thread.currentThread()
                            .getContextClassLoader().loadClass(className);

                        classes.add(clazz);
                    } catch (final NoClassDefFoundError | ClassNotFoundException e) {
                        // do nothing. this class hasn't been found by the
                        // loader, and we don't care.
                    }
                } else if ((tmpDirectory = new File(directory, file)).isDirectory()) {
                    checkDirectory(tmpDirectory, pckgname + "." + file, classes);
                }
            }
        }
    }

    /**
     * Private helper method.
     *
     * @param connection
     *            the connection to the jar
     * @param pckgname
     *            the package name to search for
     * @param classes
     *            the current ArrayList of all classes. This method will simply
     *            add new classes.
     * @throws ClassNotFoundException
     *             if a file isn't loaded but still is in the jar file
     * @throws IOException
     *             if it can't correctly read from the jar file.
     */
    private static void checkJarFile(JarURLConnection connection,
                                     String pckgname, ArrayList<Class<?>> classes)
        throws ClassNotFoundException, IOException {
        final JarFile jarFile = connection.getJarFile();
        final Enumeration<JarEntry> entries = jarFile.entries();
        String name;

        for (JarEntry jarEntry = null; entries.hasMoreElements()
            && ((jarEntry = entries.nextElement()) != null);) {
            name = jarEntry.getName();

            if (name.contains(".class")) {
                name = name.substring(0, name.length() - 6).replace('/', '.');

                if (name.contains(pckgname)) {
                    try {
                        classes.add(Class.forName(name));
                    } catch (NoClassDefFoundError ignored) { }
                }
            }
        }
    }

    /**
     * Attempts to list all the classes in the specified package as determined
     * by the context class loader
     *
     * @param pckgname
     *            the package name to search
     * @return a list of classes that exist within that package
     * @throws ClassNotFoundException
     *             if something went wrong
     */
    public static ArrayList<Class<?>> getClassesForPackage(String pckgname) {

        final ArrayList<Class<?>> classes = new ArrayList<>();

        try {
            final ClassLoader cld = Thread.currentThread().getContextClassLoader();

            if (cld == null) {
                throw new UnexpectedError("Can't get class loader.");
            }

            String resourcePath = pckgname.replace('.', '/');
            final Enumeration<URL> resources = cld.getResources(resourcePath);
            URLConnection connection;

            for (URL url; resources.hasMoreElements() && ((url = resources.nextElement()) != null);) {
                connection = url.openConnection();
                if (connection.getClass().getCanonicalName()
                    .equals("sun.net.www.protocol.file.FileURLConnection")) {

                    String fullPath = URLDecoder.decode(url.getPath(), "UTF-8");
                    checkDirectory(new File(fullPath), pckgname, classes);
                }
            }

        } catch (final IOException ex) {
            throw new UnexpectedError(
                "IOException was thrown when trying to get all resources for " + pckgname, ex);
        }

        return classes;
    }
}

