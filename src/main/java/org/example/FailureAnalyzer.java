package org.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import opennlp.tools.doccat.*;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

// Core Java imports
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

// Apache Commons Text imports for similarity calculations
import org.apache.commons.text.similarity.CosineSimilarity;
import org.apache.commons.text.similarity.LevenshteinDistance;

// OpenNLP imports
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.util.*;

public class FailureAnalyzer {
    private final DoccatModel model;
    private final Map<String, String> historicalFailures = new HashMap<>();


    public FailureAnalyzer() throws IOException {
        // Load pre-trained model
        try (InputStream modelIn = getClass().getResourceAsStream("/models/failure-classifier.bin")) {
            model = new DoccatModel(modelIn);
        }

        // Initialize historical failures (could load from file/database)
        historicalFailures.put("element_001", "NoSuchElementException: Unable to locate element");
        historicalFailures.put("network_002", "ConnectException: Connection refused");
    }

    public AnalysisResult analyzeFailure(TestFailure failure) {
        // 1. Preprocess exception text
        String processedText = preprocessText(failure.getExceptionText());

        // 2. Categorize failure
        DocumentCategorizerME categorizer = new DocumentCategorizerME(model);
        double[] outcomes = categorizer.categorize(new String[]{processedText});
        String category = categorizer.getBestCategory(outcomes);
        double confidence = outcomes[categorizer.getIndex(category)];

        // 3. Get solution suggestions
        String solution = suggestSolution(category);

        // 4. Find similar past failures
        List<String> similarIssues = findSimilarIssues(failure.getExceptionText());

        // 5. Return properly constructed AnalysisResult
        return new AnalysisResult(
                failure.getTestName(),
                failure.getDescription(),
                category,
                confidence,
                solution,
                similarIssues,
                failure.getExceptionText()
        );
    }

    private String suggestSolution(String category) {
        Map<String, String> knowledgeBase = Map.of(
                "name", "Check element locators and page loading timing",
                "network_issue", "Verify API endpoints and network connectivity",
                "assertion_failure", "Validate test data and application state"
        );
        return knowledgeBase.getOrDefault(category, "Review stack trace for patterns");
    }

    public void trainModel() throws IOException {
        ObjectStream<String> lineStream = new PlainTextByLineStream(
                () -> getClass().getResourceAsStream("/training-data.txt"), StandardCharsets.UTF_8);

        ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

        TrainingParameters params = new TrainingParameters();
        params.put(TrainingParameters.ITERATIONS_PARAM, 100);
        params.put(TrainingParameters.CUTOFF_PARAM, 2);

        DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, new DoccatFactory());
        model.serialize(new FileOutputStream("failure-classifier.bin"));
    }

    private List<String> findSimilarIssues(String exceptionText) {
        final CosineSimilarity cosine = new CosineSimilarity();
        final double similarityThreshold = 0.7;

        Map<CharSequence, Integer> queryVector = createVector(exceptionText);

        return historicalFailures.entrySet().stream()
                .filter(entry -> {
                    Map<CharSequence, Integer> entryVector = createVector(entry.getValue());
                    double similarity = cosine.cosineSimilarity(queryVector, entryVector);
                    return similarity > similarityThreshold;
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private Map<CharSequence, Integer> createVector(String text) {
        // Simple word frequency vector
        return Arrays.stream(preprocessText(text).split(" "))
                .collect(Collectors.toMap(
                        word -> word,
                        word -> 1,
                        Integer::sum
                ));
    }


    // Rest of the existing methods remain unchanged...

    private String preprocessText(String text) {
        // 1. Convert to lowercase
        // 2. Remove special characters except spaces and alphanumerics
        // 3. Normalize whitespace
        return text.toLowerCase()
                .replaceAll("[^a-zA-Z0-9\\s]", "")
                .replaceAll("\\s+", " ")
                .trim();
    }
}
