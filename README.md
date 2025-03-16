# AI-Powered Test Failure Analysis for TestNG Projects

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Maven](https://img.shields.io/badge/Maven-3.6%2B-red)
![TestNG](https://img.shields.io/badge/TestNG-7.8-green)

# AI-Powered Test Failure Analysis for TestNG Projects

## Overview

Modern test automation frameworks generate large amounts of test execution data, making it challenging to analyze failures efficiently. This AI-powered test failure analysis system leverages Natural Language Processing (NLP) and machine learning to automatically classify test failures, identify root causes, and suggest actionable solutions. The tool integrates seamlessly with Java, Maven, and TestNG, providing valuable insights through comprehensive reports.

## Features

- 🧠 **AI-Powered Failure Classification**: Uses OpenNLP to categorize test failures based on historical patterns.
- 📊 **Smart HTML/Text Reports**: Generates detailed failure statistics for easier debugging and analysis.
- 🔍 **Historical Failure Pattern Matching**: Compares current failures with past results to identify recurring issues.
- 🛠️ **Suggested Solutions**: Provides recommended fixes based on failure categories and historical data.
- 📈 **Confidence Scoring**: Assigns a confidence level to each classification result to ensure accuracy.

## Installation

### Prerequisites

Ensure the following dependencies are installed before proceeding:
- **Java 17+** (Required for compilation and execution)
- **Maven 3.6+** (Dependency management and build tool)
- **TestNG 7.8+** (Test execution framework)

### Installation Steps

```sh
git clone https://github.com/yourusername/ai-test-failure-analysis.git
cd ai-test-failure-analysis
mvn clean install
```

## Configuration

### Model Setup
The AI model used for failure classification must be placed in the following directory:
```
src/main/resources/models/failure-classifier.bin
```
Ensure that the trained model is correctly formatted and accessible by the application.

## Training Data Format
The AI system relies on structured training data for failure classification. The format follows a simple mapping between failure categories and exception messages.

### Example Training Data:
```
element_not_found NoSuchElementException: Unable to locate element
network_issue ConnectException: Connection refused
assertion_failure Expected true but found false
```
Expanding the dataset with more examples improves classification accuracy.

## Usage Guide

### 1. Run Automated Tests
Execute your TestNG test suite using the following command:
```sh
mvn clean test
```

### 2. Train the AI Model (Initial Setup)
Before analyzing failures, train the model with historical failure data:
```sh
org.example.ModelTrainer
```

### 3. Analyze Failures
Run the main analysis program to classify test failures and generate reports:
```sh
mvn exec:java -Dexec.mainClass="org.example.Main"
```

### 4. View Reports
- **HTML Report:** `reports/failure-analysis.html`
- **Text Summary:** `reports/summary.txt`

## Sample Report

![HTML Report Preview](https://github.com/user-attachments/assets/43c29780-37bc-4877-8115-5ab6274f6ba7)

## Dependencies

This tool depends on the following libraries to provide AI-based failure classification and reporting:

- **OpenNLP 2.3.0** - NLP engine for text analysis and classification.
- **Apache Commons Text 1.10.0** - Utility library for text similarity analysis.
- **Freemarker 2.3.32** - Template engine for generating structured reports.

## Common Issues & Troubleshooting

### 1. Model File Not Found:
- Ensure the model file exists in `src/main/resources/models/`
- Verify correct file naming and case sensitivity

### 2. Low Confidence Scores:
- Expand training dataset with additional failure examples
- Retrain model using:
```sh
org.example.ModelTrainer
```

### 3. Empty Results:
- Verify that tests actually failed before running the analysis
- Ensure the correct TestNG XML result file path is configured

## Contribution Guidelines

We welcome contributions to enhance the AI-powered test failure analysis tool. Follow these steps to contribute:

1. **Fork the repository**
2. **Create a feature branch** (`git checkout -b feature-name`)
3. **Implement changes and write tests**
4. **Commit changes** (`git commit -m "Description of changes"`)
5. **Push to your branch** (`git push origin feature-name`)
6. **Create a pull request** (PR) for review

Join us in making automated test failure analysis smarter and more efficient!

