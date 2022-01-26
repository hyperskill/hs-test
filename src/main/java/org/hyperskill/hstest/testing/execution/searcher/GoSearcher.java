package org.hyperskill.hstest.testing.execution.searcher;

import org.hyperskill.hstest.testing.execution.runnable.RunnableFile;

public class GoSearcher extends BaseSearcher {
    @Override
    public String extension() {
        return ".go";
    }

    @Override
    public RunnableFile search(String whereToSearch) {
        return simpleSearch(whereToSearch,
            "func main()",
            "(^|\n) *func +main +\\( *\\)"
        );
    }
}
