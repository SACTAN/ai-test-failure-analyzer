package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;



public class TestNGResultParser {

    public List<TestFailure> parseFailures(File xmlFile) throws Exception {
        List<TestFailure> failures = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);

        NodeList testCases = doc.getElementsByTagName("test-method");

        for (int i = 0; i < testCases.getLength(); i++) {
            Element testCase = (Element) testCases.item(i);

            if ("FAIL".equals(testCase.getAttribute("status"))) {
                String testName = testCase.getAttribute("name");
                String message = "";
                String exceptionType = "";
                String fullStackTrace = "";
                String locator = "N/A"; // Default value if locator not found

                NodeList exceptions = testCase.getElementsByTagName("exception");
                if (exceptions.getLength() > 0) {
                    Element exception = (Element) exceptions.item(0);
                    exceptionType = exception.getAttribute("class");

                    // Extracting exception message
                    NodeList messages = exception.getElementsByTagName("message");
                    if (messages.getLength() > 0) {
                        message = messages.item(0).getTextContent();
                    }

                    // Extracting full stack trace
                    NodeList stackTraces = exception.getElementsByTagName("full-stacktrace");
                    if (stackTraces.getLength() > 0) {
                        fullStackTrace = stackTraces.item(0).getTextContent();
                    }

                    // Extracting locator from message (if it's a Selenium NoSuchElementException)
                    if (exceptionType.contains("NoSuchElementException")) {
                        locator = extractLocatorFromMessage(message);
                    }
                }

                failures.add(new TestFailure(testName, message, exceptionType));
            }
        }
        return failures;
    }

    // Method to extract locator from Selenium NoSuchElementException messages
    private String extractLocatorFromMessage(String message) {
        if (message.contains("Element not found:")) {
            return message.substring(message.indexOf("Element not found:") + 18).trim();
        }
        return "N/A";
    }
    private String getExceptionDetails(Element testCase) {
        // Extract stack trace and error message
        // ... XML parsing implementation ...
        return null;
    }
}
