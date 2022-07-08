package org.hyperskill.hstest.testing.execution.searcher;

import lombok.Data;
import org.hyperskill.hstest.common.FileUtils;
import org.hyperskill.hstest.exception.outcomes.ErrorWithFeedback;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.testing.execution.filtering.FileFilter;
import org.hyperskill.hstest.testing.execution.filtering.MainFilter;
import org.hyperskill.hstest.testing.execution.runnable.RunnableFile;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.MULTILINE;
import static java.util.stream.Collectors.toSet;

public abstract class BaseSearcher {

    @Data
    private static class CacheKey {
        final String extension;
        final String whereToSearch;
    }

    private static final Map<String, String> fileContentsCached = new HashMap<>();
    private static final Map<CacheKey,RunnableFile> searchCached = new HashMap<>();
    private static final String moduleSeparator = ".";


    public abstract String extension();
    public abstract RunnableFile search(String whereToSearch);

    private static Map<String, String> getContents(List<File> files) {
        var contents = new HashMap<String, String>();

        for (File file : files) {
            var path = FileUtils.abspath(file);

            if (fileContentsCached.containsKey(path)) {
                contents.put(file.getName(), fileContentsCached.get(path));

            } else if (FileUtils.exists(path)) {
                String fileContent = FileUtils.readFile(path);

                if (fileContent != null) {
                    contents.put(file.getName(), fileContent);
                    fileContentsCached.put(path, fileContent);
                }
            }
        }

        return contents;
    }

    private RunnableFile searchNonCached(
        String whereToSearch,
        FileFilter fileFilter,
        FileFilter preMainFilter,
        MainFilter mainFilter,
        FileFilter postMainFilter
    ) {

        var currFolder = FileUtils.abspath(whereToSearch);

        for (var walk : FileUtils.walkUserFiles(currFolder)) {
            var folder = walk.getFolder();
            var dirs = walk.getDirs();
            var files = walk.getFiles();

            var contents = getContents(files);

            var initialFilter = new FileFilter()
                .file(f -> f.endsWith(extension()))
                .generic(fileFilter::filter);

            Set<File> candidates = new HashSet<>(files);

            for (var currFilter : new FileFilter[] {
                    initialFilter, preMainFilter, mainFilter, postMainFilter }) {

                currFilter.initFilter(FileUtils.abspath(folder), contents);

                var filteredFiles = files.stream()
                    .filter(file -> currFilter.filter(
                        FileUtils.abspath(folder),
                        FileUtils.abspath(file),
                        contents.get(file.getName())
                    )).collect(toSet());

                currFilter.filtered(filteredFiles);

                if (filteredFiles.size() == 0) {
                    if (currFilter == initialFilter) {
                        break;
                    } else {
                        continue;
                    }
                }

                if (filteredFiles.size() == 1) {
                    var file = filteredFiles.iterator().next();
                    return new RunnableFile(folder, file);
                }

                var newCandidates = new HashSet<>(candidates);
                newCandidates.retainAll(filteredFiles);

                if (newCandidates.size() != 0) {
                    candidates = newCandidates;
                }

                if (candidates.size() == 1) {
                    var file = candidates.iterator().next();
                    return new RunnableFile(folder, file);
                }
            }

            if (initialFilter.filtered().size() == 0) {
                continue;
            }

            if (candidates.size() > 1 && mainFilter.filtered().size() > 0) {
                var strFiles = FileUtils.displaySorted(candidates);
                throw new ErrorWithFeedback(
                    "Cannot decide which file to run out of the following: " + strFiles + "\n" +
                    "They all have " + mainFilter.getProgramShouldContain() + ". Leave one file with this line."
                );
            }

            if (candidates.size() == 0) {
                candidates = initialFilter.filtered();
            }

            var strFiles = FileUtils.displaySorted(candidates);

            throw new ErrorWithFeedback(
                "Cannot decide which file to run out of the following: " + strFiles + "\n" +
                    "Write \"" + mainFilter.getProgramShouldContain() + " in one of them to mark it as an entry point."
            );
        }

        throw new ErrorWithFeedback(
            "Cannot find a file to execute your code.\n" +
                "Are your project files located at \"" + currFolder + "\"?"
        );
    }

    protected RunnableFile searchCached(
        String whereToSearch,
        FileFilter fileFilter,
        FileFilter preMainFilter,
        MainFilter mainFilter,
        FileFilter postMainFilter
    ) {

        if (!extension().startsWith(".")) {
            throw new UnexpectedError(
                "File extension \"" + extension() + "\" should start with a dot");
        }

        if (whereToSearch == null) {
            whereToSearch = FileUtils.cwd();
        }

        boolean doCaching = false;
        var cacheKey = new CacheKey(extension(), whereToSearch);

        if (fileFilter == null) {
            if (searchCached.containsKey(cacheKey)) {
                return searchCached.get(cacheKey);
            }

            doCaching = true;
            fileFilter = new FileFilter();
        }

        if (preMainFilter == null) {
            preMainFilter = new FileFilter();
        }

        if (mainFilter == null) {
            mainFilter = new MainFilter("");
        }

        if (postMainFilter == null) {
            postMainFilter = new FileFilter();
        }

        var result = searchNonCached(
            whereToSearch, fileFilter, preMainFilter, mainFilter, postMainFilter);

        if (doCaching) {
            searchCached.put(cacheKey, result);
        }

        return result;
    }

    protected RunnableFile simpleSearch(String whereToSearch, String mainDesc, String mainRegex) {
        var mainSearcher = Pattern.compile(mainRegex, MULTILINE);

        var mainFilter = new MainFilter(mainDesc);
        mainFilter.source(FileFilter.regexFilter(mainSearcher));

        return searchCached(
            whereToSearch,
            null,
            null,
            mainFilter,
            null
        );
    }

    @Data
    private static class ParsedSource {
        final String folder;
        final String file;
        final String module;
    }

    public RunnableFile find(String source) {
        if (source == null || source.isEmpty()) {
            return search(null);
        }

        var ext = extension();

        var parsed = parseSource(source);
        var sourceFolder = parsed.folder;
        var sourceFile = parsed.file;
        var sourceModule = parsed.module;

        if (sourceFolder != null && FileUtils.isdir(sourceFolder)) {
            return searchCached(sourceFolder,
                null, null, null, null);

        } else if (sourceFile != null && FileUtils.isfile(sourceFile)) {
            var index = sourceModule.lastIndexOf(moduleSeparator);
            var path = sourceModule.substring(0, index);
            var file = sourceModule.substring(index + 1);
            var folder = FileUtils.abspath(path.replace(moduleSeparator, File.separator));
            return new RunnableFile(new File(folder), new File(file + ext));

        } else {
            var index = sourceModule.lastIndexOf(moduleSeparator);
            var path = sourceModule.substring(0, index);
            var folder = FileUtils.abspath(path.replace(moduleSeparator, File.separator));
            throw new ErrorWithFeedback("Cannot find a file to execute your code in directory \"" + folder + "\".");
        }
    }

    private ParsedSource parseSource(String source) {
        var ext = extension();

        source = source
            .replace("/", File.separator)
            .replace("\\", File.separator);

        if (source.endsWith(ext)) {
            var module = source
                .substring(0, source.length() - ext.length())
                .replace(File.separator, moduleSeparator);

            return new ParsedSource(null, source, module);

        } else if (source.contains(File.separator)) {
            if (source.endsWith(File.separator)) {
                source = source.substring(0, source.length() - File.separator.length());
            }
            var module = source.replace(File.separator, moduleSeparator);
            return new ParsedSource(source, null, module);

        } else {
            var sourceFolder = source.replace(moduleSeparator, File.separator);
            var sourceFile = sourceFolder + ext;
            return new ParsedSource(sourceFolder, sourceFile, source);
        }
    }
}
