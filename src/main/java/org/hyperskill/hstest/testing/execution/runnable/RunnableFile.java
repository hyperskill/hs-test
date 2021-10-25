package org.hyperskill.hstest.testing.execution.runnable;

import java.io.File;

public class RunnableFile {
    final File folder;
    final File file;

    public RunnableFile(File folder, File file) {
        this.folder = folder;
        this.file = file;
    }

    public File getFolder() {
        return this.folder;
    }

    public File getFile() {
        return this.file;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof RunnableFile)) return false;
        final RunnableFile other = (RunnableFile) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$folder = this.getFolder();
        final Object other$folder = other.getFolder();
        if (this$folder == null ? other$folder != null : !this$folder.equals(other$folder)) return false;
        final Object this$file = this.getFile();
        final Object other$file = other.getFile();
        if (this$file == null ? other$file != null : !this$file.equals(other$file)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof RunnableFile;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $folder = this.getFolder();
        result = result * PRIME + ($folder == null ? 43 : $folder.hashCode());
        final Object $file = this.getFile();
        result = result * PRIME + ($file == null ? 43 : $file.hashCode());
        return result;
    }

    public String toString() {
        return "RunnableFile(folder=" + this.getFolder() + ", file=" + this.getFile() + ")";
    }
}
