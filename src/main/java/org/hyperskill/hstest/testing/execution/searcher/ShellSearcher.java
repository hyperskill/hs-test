package org.hyperskill.hstest.testing.execution.searcher;

import org.hyperskill.hstest.testing.execution.runnable.RunnableFile;

public class ShellSearcher extends BaseSearcher {
    @Override
    public String extension() {
        return ".sh";
    }

    @Override
    public RunnableFile search(String whereToSearch) {
        return simpleSearch(whereToSearch,
            "# main",
            "(^|\n)# *main"
        );
    }
}
