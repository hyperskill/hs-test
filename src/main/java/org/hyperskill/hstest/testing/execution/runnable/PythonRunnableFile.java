package org.hyperskill.hstest.testing.execution.runnable;

import lombok.Getter;

import java.io.File;

public class PythonRunnableFile extends RunnableFile {
    @Getter private final String module;

    public PythonRunnableFile(File folder, File file, String module) {
        super(folder, file);
        this.module = module;
    }
}
