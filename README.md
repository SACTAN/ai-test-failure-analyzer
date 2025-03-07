# AI-Powered Test Failure Analysis for TestNG Projects

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Maven](https://img.shields.io/badge/Maven-3.6%2B-red)
![TestNG](https://img.shields.io/badge/TestNG-7.8-green)

An intelligent test failure analysis system that uses NLP and machine learning to automatically classify test failures and suggest root causes.

## Features

- 🧠 AI-powered failure classification using OpenNLP
- 📊 Smart HTML/Text reports with failure statistics
- 🔍 Historical failure pattern matching
- 🛠️ Suggested solutions based on failure categories
- 📈 Confidence scoring for analysis results

## Installation

### Prerequisites
- Java 17+
- Maven 3.6+
- TestNG 7.8+

```bash
git clone https://github.com/yourusername/ai-test-failure-analysis.git
cd ai-test-failure-analysis
mvn clean install

## Configuration
Model Setup:
Place your trained model at:
src/main/resources/models/failure-classifier.bin

## Training Data Format

**Example:**
element_not_found NoSuchElementException: Unable to locate element
network_issue ConnectException: Connection refused
assertion_failure Expected true but found false

Usage
1. Run Tests
mvn clean test

2. Train Model (Initial Setup)
"org.example.ModelTrainer"

3. Analyze Failures
mvn exec:java -Dexec.mainClass="org.example.Main"

4. View Results
HTML Report: reports/failure-analysis.html
Text Summary: reports/summary.txt


## Sample Report

![HTML Report Preview](https://github.com/user-attachments/assets/43c29780-37bc-4877-8115-5ab6274f6ba7)

## Dependencies

- **OpenNLP 2.3.0** (NLP processing)
- **Apache Commons Text 1.10.0** (Text similarity)
- **Freemarker 2.3.32** (Report templating)

### Common Issues

**Model File Not Found:**
- Verify file exists in `src/main/resources/models/`
- Check case sensitivity in filenames

**Low Confidence Scores:**
- Add more training examples
- Retrain model with:
  ```bash
  "org.example.ModelTrainer"

## Empty Results:
Ensure tests actually failed
Verify TestNG XML result file path

## Contribution
Contributions welcome! Please follow:
Fork the repository
Create your feature branch
Commit changes with tests
Push to the branch
Open a PR
