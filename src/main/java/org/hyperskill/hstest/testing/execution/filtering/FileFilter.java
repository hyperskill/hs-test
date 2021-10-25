package org.hyperskill.hstest.testing.execution.filtering;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@Accessors(fluent = true, chain = true)
public class FileFilter {

    public interface InitFilter {
        void apply(String folder, Map<String, String> sources);
    }

    public interface Filter {
        boolean apply(String thing);
    }

    public interface GenericFilter {
        boolean apply(String folder, String file, String source);
    }

    @Getter @Setter private InitFilter initFiles = (f, s) -> { };
    @Getter @Setter private Filter folder = f -> true;
    @Getter @Setter private Filter file = f -> true;
    @Getter @Setter private Filter source = f -> true;
    @Getter @Setter private GenericFilter generic = (f1, f2, s) -> true;
    @Getter @Setter private Set<File> filtered = null;

    public static Filter regexFilter(Pattern regex) {
        return t -> regex.matcher(t).find();
    }

    public final void initFilter(String folder,  Map<String, String> sources) {
        initFiles.apply(folder, sources);
    }

    public final boolean filter(String folder, String file, String source) {
        return this.folder.apply(folder)
            && this.file.apply(file)
            && this.source.apply(source)
            && this.generic.apply(folder, file, source);
    }

}
