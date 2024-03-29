package org.hyperskill.hstest.testing.execution.runnable;

import lombok.Data;

import java.io.File;

@Data
public class RunnableFile {
    final File folder;
    final File file;

    public RunnableFile(File folder, File file) {
        this.folder = folder;
        this.file = file;
    }
}
