package org.hyperskill.hstest.common;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Detects forbidden exit calls in user code that could terminate the JVM.
 * This includes System.exit(), exitProcess(), Runtime.exit(), and Runtime.halt().
 */
public class ExitCallDetector {

    /**
     * Result of the exit call detection
     */
    public static class DetectionResult {
        private final boolean hasExitCalls;
        private final List<String> violations;

        public DetectionResult(boolean hasExitCalls, List<String> violations) {
            this.hasExitCalls = hasExitCalls;
            this.violations = violations;
        }

        public boolean hasExitCalls() {
            return hasExitCalls;
        }

        public List<String> getViolations() {
            return violations;
        }

        public String getFormattedMessage() {
            if (!hasExitCalls) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Your code contains forbidden exit calls that would terminate the test execution:\n\n");
            for (String violation : violations) {
                sb.append("  ").append(violation).append("\n");
            }
            sb.append("\nPlease remove all System.exit(), exitProcess(), Runtime.exit(), and Runtime.halt() calls from your code.");
            return sb.toString();
        }
    }

    /**
     * Analyzes a single Java source file for exit calls
     */
    public static DetectionResult analyzeFile(File file) throws IOException {
        String content = Files.readString(file.toPath());
        return analyzeSourceCode(content, file.getName());
    }

    /**
     * Analyzes Java source code string for exit calls
     */
    public static DetectionResult analyzeSourceCode(String sourceCode, String fileName) {
        List<String> violations = new ArrayList<>();

        // First, do a simple string-based check as a fast pre-filter
        if (!containsSimpleExitPattern(sourceCode)) {
            return new DetectionResult(false, violations);
        }

        // If simple check finds something, do detailed AST analysis
        try {
            JavaParser parser = new JavaParser();
            ParseResult<CompilationUnit> parseResult = parser.parse(sourceCode);

            if (parseResult.isSuccessful() && parseResult.getResult().isPresent()) {
                CompilationUnit cu = parseResult.getResult().get();
                ExitCallVisitor visitor = new ExitCallVisitor(fileName);
                visitor.visit(cu, violations);
            }
        } catch (Exception e) {
            // If parsing fails, fall back to simple string check
            violations.addAll(simpleStringAnalysis(sourceCode, fileName));
        }

        return new DetectionResult(!violations.isEmpty(), violations);
    }

    /**
     * Analyzes all Java files in a directory recursively
     */
    public static DetectionResult analyzeDirectory(Path directory) throws IOException {
        List<String> allViolations = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(directory)) {
            List<Path> javaFiles = paths
                .filter(Files::isRegularFile)
                .filter(p -> p.toString().endsWith(".java") || p.toString().endsWith(".kt"))
                .collect(Collectors.toList());

            for (Path path : javaFiles) {
                DetectionResult result = analyzeFile(path.toFile());
                if (result.hasExitCalls()) {
                    allViolations.addAll(result.getViolations());
                }
            }
        }

        return new DetectionResult(!allViolations.isEmpty(), allViolations);
    }

    /**
     * Fast string-based pre-filter to avoid expensive AST parsing when not needed
     */
    private static boolean containsSimpleExitPattern(String sourceCode) {
        return sourceCode.contains("exit") || sourceCode.contains("halt");
    }

    /**
     * Simple string-based analysis as fallback
     */
    private static List<String> simpleStringAnalysis(String sourceCode, String fileName) {
        List<String> violations = new ArrayList<>();
        String[] lines = sourceCode.split("\n");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            
            // Skip comments
            if (line.startsWith("//") || line.startsWith("/*") || line.startsWith("*")) {
                continue;
            }

            if (line.contains("System.exit")) {
                violations.add(fileName + " (line " + (i + 1) + "): System.exit() call detected");
            }
            if (line.contains("exitProcess")) {
                violations.add(fileName + " (line " + (i + 1) + "): exitProcess() call detected");
            }
            if (line.contains("Runtime") && line.contains(".exit")) {
                violations.add(fileName + " (line " + (i + 1) + "): Runtime.exit() call detected");
            }
            if (line.contains("Runtime") && line.contains(".halt")) {
                violations.add(fileName + " (line " + (i + 1) + "): Runtime.halt() call detected");
            }
        }

        return violations;
    }

    /**
     * AST visitor to find method calls
     */
    private static class ExitCallVisitor extends VoidVisitorAdapter<List<String>> {
        private final String fileName;

        public ExitCallVisitor(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public void visit(MethodCallExpr methodCall, List<String> violations) {
            super.visit(methodCall, violations);

            String methodName = methodCall.getNameAsString();
            
            // Check for exit, exitProcess, or halt calls
            if (methodName.equals("exit") || methodName.equals("exitProcess") || methodName.equals("halt")) {
                
                // Check if it's System.exit()
                if (methodCall.getScope().isPresent()) {
                    String scope = methodCall.getScope().get().toString();
                    
                    if (scope.equals("System")) {
                        int line = methodCall.getBegin().map(pos -> pos.line).orElse(0);
                        violations.add(fileName + " (line " + line + "): System.exit() call detected");
                    } 
                    else if (scope.contains("Runtime")) {
                        int line = methodCall.getBegin().map(pos -> pos.line).orElse(0);
                        if (methodName.equals("exit")) {
                            violations.add(fileName + " (line " + line + "): Runtime.exit() call detected");
                        } else if (methodName.equals("halt")) {
                            violations.add(fileName + " (line " + line + "): Runtime.halt() call detected");
                        }
                    }
                } 
                // Kotlin's exitProcess() has no scope
                else if (methodName.equals("exitProcess")) {
                    int line = methodCall.getBegin().map(pos -> pos.line).orElse(0);
                    violations.add(fileName + " (line " + line + "): exitProcess() call detected");
                }
            }
        }
    }
}
