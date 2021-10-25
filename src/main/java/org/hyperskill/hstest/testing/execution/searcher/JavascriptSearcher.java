package org.hyperskill.hstest.testing.execution.searcher;

import org.hyperskill.hstest.testing.execution.filtering.FileFilter;
import org.hyperskill.hstest.testing.execution.filtering.MainFilter;
import org.hyperskill.hstest.testing.execution.runnable.RunnableFile;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.MULTILINE;

public class JavascriptSearcher extends BaseSearcher {
    @Override
    public String extension() {
        return ".js";
    }

    @Override
    public RunnableFile search(String whereToSearch) {
        var mainSearcher = Pattern.compile("(^|\n) *function +main +\\( *\\)", MULTILINE);

        var mainFilter = new MainFilter("function main()");
        mainFilter.source(FileFilter.regexFilter(mainSearcher));

        return searchCached(
            whereToSearch,
            null,
            null,
            mainFilter,
            null
        );
    }
}
