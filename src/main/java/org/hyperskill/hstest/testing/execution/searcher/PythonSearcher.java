package org.hyperskill.hstest.testing.execution.searcher;

import org.hyperskill.hstest.testing.execution.filtering.FileFilter;
import org.hyperskill.hstest.testing.execution.filtering.MainFilter;
import org.hyperskill.hstest.testing.execution.runnable.PythonRunnableFile;
import org.hyperskill.hstest.testing.execution.runnable.RunnableFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.MULTILINE;
import static java.util.regex.Pattern.compile;

public class PythonSearcher extends BaseSearcher {
    Map<String, Boolean> isImported = null;

    @Override
    public String extension() {
        return ".py";
    }

    @Override
    public RunnableFile search(String whereToSearch) {
        return search(whereToSearch, null);
    }

    public RunnableFile search(String whereToSearch, FileFilter fileFilter) {
        isImported = new HashMap<>();

        var initFilter = new FileFilter()
            .initFiles(this::initRegexes)
            .file(f -> !isImported.get(f));

        var mainFilter = new MainFilter("if __name__ == '__main__'");
        mainFilter.source(s -> s.contains("__name__") && s.contains("__main__"));

        return searchCached(
            whereToSearch,
            fileFilter,
            initFilter,
            mainFilter,
            null
        );
    }

    private void initRegexes(String folder, Map<String, String> sources) {
        Map<String, List<Pattern>> importRegexes = new HashMap<>();

        for (var item : sources.entrySet()) {
            var file = item.getKey();
            var withoutExt = file.substring(0, file.length() - extension().length());

            isImported.put(file, false);

            importRegexes.put(file, List.of(
                compile("(^|\n)import +[\\w., ]*\\b" + withoutExt + "\\b[\\w., ]*", MULTILINE),
                compile("(^|\n)from +\\.? *\\b" + withoutExt + "\\b +import +", MULTILINE)
            ));
        }

        for (var item : sources.entrySet()) {
            var source = item.getValue();

            for (var item2 : importRegexes.entrySet()) {
                var f = item2.getKey();
                var r1 = item2.getValue().get(0);
                var r2 = item2.getValue().get(1);

                if (r1.matcher(source).find() || r2.matcher(source).find()) {
                    isImported.put(f, true);
                }
            }
        }
    }

    @Override
    public PythonRunnableFile find(String whereToSearch) {
        var runnable = super.find(whereToSearch);
        var name = runnable.getFile().getName();

        return new PythonRunnableFile(
            runnable.getFolder(),
            runnable.getFile(),
            name.substring(0, name.length() - extension().length())
        );
    }
}
