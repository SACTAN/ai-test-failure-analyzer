package org.example;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import freemarker.template.Configuration;
import freemarker.template.Template;


import freemarker.template.*;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportGenerator {
    private static final String TEMPLATE_DIR = "/templates";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void generateHtmlReport(List<AnalysisResult> results, File outputFile) throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setClassForTemplateLoading(getClass(), TEMPLATE_DIR);
        cfg.setDefaultEncoding("UTF-8");

        Map<String, Object> data = new HashMap<>();
        data.put("results", results);
        data.put("reportTitle", "AI Test Failure Analysis Report");
        data.put("generationDate", dtf.format(LocalDateTime.now()));
        data.put("summaryStats", calculateSummaryStats(results));
        data.put("categoryDistribution", calculateCategoryDistribution(results));

        Template template = cfg.getTemplate("analysis-report.ftl");

        try (Writer out = new FileWriter(outputFile)) {
            template.process(data, out);
        }
    }

    private Map<String, Object> calculateSummaryStats(List<AnalysisResult> results) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalFailures", results.size());
        stats.put("avgConfidence", results.stream()
                .mapToDouble(AnalysisResult::getConfidenceScore)
                .average()
                .orElse(0));
        return stats;
    }

    private Map<String, Long> calculateCategoryDistribution(List<AnalysisResult> results) {
        Map<String, Long> distribution = new HashMap<>();
        results.forEach(result -> {
            String category = result.getFailureCategory();
            distribution.put(category, distribution.getOrDefault(category, 0L) + 1);
        });
        return distribution;
    }

    public void generateTextSummary(List<AnalysisResult> results, File outputFile) throws IOException {
        StringBuilder summary = new StringBuilder();
        summary.append("Test Failure Analysis Summary\n");
        summary.append("==============================\n");

        results.forEach(result -> {
            summary.append(String.format(
                    "Test: %s\nCategory: %s\nConfidence: %.2f%%\nSolution: %s\n\n",
                    result.getTestName(),
                    result.getFailureCategory(),
                    result.getConfidenceScore() * 100,
                    result.getSuggestedSolution()
            ));
        });

        Files.write(outputFile.toPath(), summary.toString().getBytes());
    }
}
