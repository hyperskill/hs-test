package org.hyperskill.hstest.testing.execution.filtering;

import lombok.Getter;
import lombok.Setter;

public class MainFilter extends FileFilter {

    @Getter @Setter String programShouldContain;

    public MainFilter(String programShouldContain) {
        this.programShouldContain = programShouldContain;
    }

}
