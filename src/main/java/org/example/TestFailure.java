package org.example;

public class TestFailure {
    private String testName;
    private String description;
    private String exceptionMessage;
    private String stackTrace;

    public TestFailure(String testName, String description, String exceptionDetails) {
        this.testName = testName;
        this.description = description;

        // Parse exception details into message and stack trace
        String[] parts = exceptionDetails.split("\n", 2);
        this.exceptionMessage = parts[0];
        this.stackTrace = (parts.length > 1) ? parts[1] : "";
    }

    // Getters
    public String getTestName() { return testName; }
    public String getDescription() { return description; }
    public String getExceptionMessage() { return exceptionMessage; }
    public String getStackTrace() { return stackTrace; }

    public String getExceptionText() {
        return exceptionMessage + "\n" + stackTrace;
    }
}
