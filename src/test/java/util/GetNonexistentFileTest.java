package util;

import org.hyperskill.hstest.v4.common.Utils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class GetNonexistentFileTest {
    @Test
    public void TestGetNonexistentFileDoesNotExist() throws IOException {
        final String extension = ".txt";

        final File firstFile = new File(Utils.getNonexistentFilePath(extension));
        assertTrue("The first file has been returned does exist", !firstFile.exists());

        assertTrue("Can't create the first file in the user dir", firstFile.createNewFile());

        final File secondFile = new File(Utils.getNonexistentFilePath(extension));
        assertTrue("The second file has been returned does exist", !secondFile.exists());

        assertTrue("Can't delete the first file", firstFile.delete());
    }

    @Test
    public void TestGetNonexistentFileReturnsUniqueFiles() {
        final String extension = ".txt";

        final String firstFilePath = Utils.getNonexistentFilePath(extension);
        final String secondFilePath = Utils.getNonexistentFilePath(extension);

        assertNotEquals("Two paths are identical", firstFilePath, secondFilePath);
    }

    @Test
    public void TestGetNonexistentFileWithEmptyExtensionDoesNotFail() {
        File file = new File(Utils.getNonexistentFilePath(""));
        assertTrue("The file exists", !file.exists());

        file = new File(Utils.getNonexistentFilePath(null));
        assertTrue("The file exists", !file.exists());

        file = new File(Utils.getNonexistentFilePath());
        assertTrue("The file exists", !file.exists());
    }

    @Test
    public void TestGetNonexistentFileExtensionNormalization() {
        final String extension = "dat";

        File file = new File(Utils.getNonexistentFilePath(extension));
        String fileName = file.getName();
        String actualExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
        assertEquals("Extension is wrong", extension, actualExtension);

        file = new File(Utils.getNonexistentFilePath("." + extension));
        fileName = file.getName();
        actualExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
        assertEquals("Extension is wrong", extension, actualExtension);
    }
}
