<!DOCTYPE html>
<html>
<head>
    <title>${reportTitle}</title>
    <style>
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 8px; border: 1px solid #ddd; text-align: left; }
        tr:nth-child(even) { background-color: #f2f2f2; }
        .critical { color: red; font-weight: bold; }
        .high { color: orange; }
        .medium { color: #cc9900; }
        .low { color: green; }
    </style>
</head>
<body>
    <h1>${reportTitle}</h1>
    <p>Generated at: ${generationDate}</p>

    <h2>Summary Statistics</h2>
    <ul>
        <li>Total Failures: ${summaryStats.totalFailures}</li>
        <li>Average Confidence: ${summaryStats.avgConfidence?string["0.##%"]}</li>
    </ul>

    <h2>Failure Distribution</h2>
    <table>
        <tr><th>Category</th><th>Count</th></tr>
        <#list categoryDistribution?keys as category>
            <tr><td>${category}</td><td>${categoryDistribution[category]}</td></tr>
        </#list>
    </table>

    <h2>Detailed Analysis</h2>
    <table>
        <tr>
            <th>Test Name</th>
            <th>Category</th>
            <th>Confidence</th>
            <th>Severity</th>
            <th>Suggested Solution</th>
        </tr>
        <#list results as result>
            <tr class="${result.severity?lower_case}">
                <td>${result.testName}</td>
                <td>${result.failureCategory}</td>
                <td>${result.confidenceScore?string["0.##%"]}</td>
                <td>${result.severity}</td>
                <td><pre>${result.suggestedSolution}</pre></td>
            </tr>
        </#list>
    </table>
</body>
</html>