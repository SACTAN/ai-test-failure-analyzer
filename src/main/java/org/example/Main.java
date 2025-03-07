package org.example;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. Parse TestNG Results
            TestNGResultParser parser = new TestNGResultParser();
            List<TestFailure> failures = parser.parseFailures(
                    new File("test-output/testng-results.xml")
            );

            // 2. Analyze Failures
            FailureAnalyzer analyzer = new FailureAnalyzer();
            List<AnalysisResult> results = failures.stream()
                    .map(analyzer::analyzeFailure)
                    .collect(Collectors.toList());

            // 3. Generate Reports
            ReportGenerator reporter = new ReportGenerator();

            // HTML Report
            reporter.generateHtmlReport(
                    results,
                    new File("reports/failure-analysis.html")
            );

            // Text Summary
            reporter.generateTextSummary(
                    results,
                    new File("reports/summary.txt")
            );

            // 4. Retrain Model Periodically (example)
            if (shouldRetrainModel()) {
                new ModelTrainer().retrainModelWithNewData();
            }

            System.out.println("Analysis complete. Reports generated in 'reports' directory.");

        } catch (Exception e) {
            System.err.println("Analysis failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean shouldRetrainModel() {
        // Implement your retrain logic (e.g., every 100 runs)
        return false;
    }
}
