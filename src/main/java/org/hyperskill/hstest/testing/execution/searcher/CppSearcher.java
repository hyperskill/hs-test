package org.hyperskill.hstest.testing.execution.searcher;

import org.hyperskill.hstest.testing.execution.runnable.RunnableFile;

/**
 * Searches for C++ runnable files
 * (files with main function)
 * in the given directory
 * and returns the first one found.
 */
public class CppSearcher extends BaseSearcher {
    @Override
    public String extension() {
        return ".cpp";
    }

    @Override
    public RunnableFile search(String whereToSearch) {
        return simpleSearch(whereToSearch,
                "int main()",
                "(^|\\n)\\s*int\\s+main\\s*\\(.*\\)"
        );
    }
}
