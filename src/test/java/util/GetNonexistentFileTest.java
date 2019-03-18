package util;

import org.hyperskill.hstest.dev.common.Utils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetNonexistentFileTest {
    @Test
    public void TestGetNonexistentFileDoesNotExist() throws IOException {
        final String extension = ".txt";

        final File firstFile = Utils.getNonexistentFile(extension);
        assertTrue("The first file has been returned does exist", !firstFile.exists());

        assertTrue("Can't create the first file in the user dir", firstFile.createNewFile());

        final File secondFile = Utils.getNonexistentFile(extension);
        assertTrue("The second file has been returned does exist", !secondFile.exists());

        assertTrue("Can't delete the first file", firstFile.delete());
    }

    @Test
    public void TestGetNonexistentFileWithEmptyExtensionDoesNotFail() {
        File file = Utils.getNonexistentFile("");
        assertTrue("The file exists", !file.exists());

        file = Utils.getNonexistentFile(null);
        assertTrue("The file exists", !file.exists());
    }

    @Test
    public void TestGetNonexistentFileExtensionNormalization() {
        final String extension = "dat";

        File file = Utils.getNonexistentFile(extension);
        String fileName = file.getName();
        assertEquals("Extension is wrong", extension, fileName.substring(fileName.lastIndexOf('.') + 1));

        file = Utils.getNonexistentFile("." + extension);
        fileName = file.getName();
        assertEquals("Extension is wrong", extension, fileName.substring(fileName.lastIndexOf('.') + 1));
    }
}
