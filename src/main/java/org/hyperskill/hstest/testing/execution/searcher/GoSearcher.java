package org.hyperskill.hstest.testing.execution.searcher;

import org.hyperskill.hstest.testing.execution.filtering.FileFilter;
import org.hyperskill.hstest.testing.execution.filtering.MainFilter;
import org.hyperskill.hstest.testing.execution.runnable.RunnableFile;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.MULTILINE;

public class GoSearcher extends BaseSearcher {
    @Override
    public String extension() {
        return ".go";
    }

    @Override
    public RunnableFile search(String whereToSearch) {
        var mainSearcher = Pattern.compile("(^|\n) *func +main +\\( *\\)", MULTILINE);

        var mainFilter = new MainFilter("func main()");
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
