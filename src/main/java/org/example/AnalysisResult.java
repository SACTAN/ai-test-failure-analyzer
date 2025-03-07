package org.example;

import java.util.List;

public class AnalysisResult {
    private final String testName;
    private final String testDescription;
    private final String failureCategory;
    private final double confidenceScore;
    private final String suggestedSolution;
    private final List<String> similarPastFailures;
    private final String rawException;

    public AnalysisResult(String testName,
                          String testDescription,
                          String failureCategory,
                          double confidenceScore,
                          String suggestedSolution,
                          List<String> similarPastFailures,
                          String rawException) {
        this.testName = testName;
        this.testDescription = testDescription;
        this.failureCategory = failureCategory;
        this.confidenceScore = confidenceScore;
        this.suggestedSolution = suggestedSolution;
        this.similarPastFailures = similarPastFailures;
        this.rawException = rawException;
    }

    // Format confidence score for display
    public String getFormattedConfidence() {
        return String.format("%.2f%%", confidenceScore * 100);
    }

    // Getters
    public String getTestName() { return testName; }
    public String getTestDescription() { return testDescription; }
    public String getFailureCategory() { return failureCategory; }
    public double getConfidenceScore() { return confidenceScore; }
    public String getSuggestedSolution() { return suggestedSolution; }
    public List<String> getSimilarPastFailures() { return similarPastFailures; }
    public String getRawException() { return rawException; }

    // For debugging purposes
    @Override
    public String toString() {
        return String.format(
                "[%s] %s - %s (Confidence: %.2f%%)%nSolution: %s%nSimilar Issues: %s",
                failureCategory.toUpperCase(),
                testName,
                testDescription,
                confidenceScore * 100,
                suggestedSolution,
                String.join(", ", similarPastFailures)
        );
    }

    // Optional: Add severity level based on category
    public String getSeverity() {
        return switch (failureCategory.toLowerCase()) {
            case "environment_issue" -> "CRITICAL";
            case "element_not_found", "timeout" -> "HIGH";
            case "assertion_failure" -> "MEDIUM";
            case "data_issue" -> "LOW";
            default -> "UNKNOWN";
        };
    }
}
