package org.hyperskill.hstest.testing.execution.searcher;

import org.hyperskill.hstest.testing.execution.runnable.RunnableFile;

public class JavascriptSearcher extends BaseSearcher {
    @Override
    public String extension() {
        return ".js";
    }

    @Override
    public RunnableFile search(String whereToSearch) {
        return simpleSearch(whereToSearch,
            "function main()",
            "(^|\n) *function +main +\\( *\\)"
        );
    }
}
