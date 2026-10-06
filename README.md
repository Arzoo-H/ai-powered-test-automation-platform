# AI-Powered Test Automation Platform

A Java-based UI and API test automation framework enhanced with AI capabilities for **failure analysis** and **execution summarization**.

The project is built on top of a reusable Selenium/TestNG automation framework and demonstrates how execution data can be collected, analyzed, enriched with AI-generated insights, and presented through test reports.

---

## 🚀 Current Capabilities

### Phase 1 — Automation Framework ✅

Core automation framework supporting:

* **UI automation** — Selenium WebDriver + Java + TestNG
* **API automation** — REST Assured
* **Page Object Model**
* **Parallel and cross-browser execution**
* Chrome, Firefox, Edge and Safari support
* Environment-based configuration
* Thread-safe execution context using `ThreadLocal`
* Retry mechanism
* Extent Reports
* Screenshots for failed tests
* Centralized logging

---

# Phase 2.1 — AI Failure Analyzer ✅

The framework captures execution information for each test and uses it to analyze failures.

### Execution data captured

Examples include:

* Test name and class
* Status and duration
* Browser and environment
* Current URL
* Exception type and message
* Relevant stack trace
* Last executed action
* Locator
* Element diagnostics
* API method, endpoint, status code and response time

### Failure analysis

A common `FailureAnalyzer` interface allows different analysis strategies:

```text
                    FailureAnalyzer
                         │
              ┌──────────┴──────────┐
              ↓                     ↓
   RuleBasedFailureAnalyzer   GeminiFailureAnalyzer
                                      │
                                   AIClient
                                      │
                                Gemini API
```

The analyzer produces a structured `FailureAnalysis` containing:

* Failure type
* Root cause
* Confidence
* Explanation
* Possible causes
* Suggested action
* Evidence

The AI analysis is **evidence-bound** and is designed to avoid inventing information that is not present in the execution data.

---

# Phase 2.2 — AI Execution Summary ✅

Phase 2.2 builds on the execution data collected in Phase 2.1 and generates a suite-level execution summary.

### Retry-aware execution model

TestNG can produce multiple execution attempts for the same logical test when retries are enabled.

The framework therefore groups attempts using an `invocationId`:

```text
Test Class + Test Name + Parameters
                ↓
          invocationId
                ↓
      ExecutionHistory
                ↓
       Final Test Result
```

This allows the framework to distinguish between:

* Logical tests
* Total execution attempts
* Retry attempts
* Failed attempts
* Tests retried
* Tests recovered after retry

For example:

```text
Total Tests:          2
Passed:               2
Failed:               0
Skipped:              0

Total Attempts:       5
Retry Attempts:       3
Tests Retried:        2
Failed Attempts:      3
Recovered After Retry: 2
```

Retries therefore do **not** inflate the logical test count.

---

## 🧠 AI Execution Summary

After the complete suite finishes, the framework generates an `ExecutionSummary` containing:

* Total tests
* Passed / failed / skipped tests
* Total attempts
* Retry attempts
* Failed attempts
* Recovered tests
* Total execution duration
* Overall health
* AI-generated summary
* Key observations

The AI summary is generated at **suite level**, rather than once per test.

---

## 🏗️ High-Level Architecture

```text
                         TestNG Suite
                              │
                ┌─────────────┴─────────────┐
                │                           │
            UI Tests                    API Tests
                │                           │
        ElementActions                 UserApiClient
                │                           │
                └─────────────┬─────────────┘
                              ↓
                         TestContext
                              ↓
                    ExecutionDataCollector
                              ↓
                       ExecutionResult
                              ↓
                       ExecutionStore
                              ↓
                    ExecutionHistory
                              ↓
                     SuiteListener
                              │
                ┌─────────────┴─────────────┐
                ↓                           ↓
        Failure Analysis             Execution Summary
                │                           │
        Rule-Based / Gemini               AIClient
                │                           │
                └─────────────┬─────────────┘
                              ↓
                       Extent Report
```

### Listener design

The framework separates listener responsibilities by lifecycle scope:

**Test-level**

* `TestListener` — collects execution information
* `RetryListener` — handles retry behavior

**Suite-level**

* `SuiteListener` — performs final aggregation, failure analysis, AI summary generation and reporting

`SuiteListener` uses `ISuiteListener` so suite-level processing happens after the complete TestNG suite has finished.

---

## 🔌 AI Provider Configuration

AI capabilities are configurable independently.

```properties
failure.analysis.strategy=RULE_BASED
execution.summary.provider=FAKE
```

For Gemini:

```properties
failure.analysis.strategy=GEMINI
execution.summary.provider=GEMINI
```

### Why two configurations?

Failure analysis and execution summarization are separate capabilities.

This allows combinations such as:

| Failure Analysis | Execution Summary |
| ---------------- | ----------------- |
| Rule-based       | Fake              |
| Rule-based       | Gemini            |
| Gemini           | Fake              |
| Gemini           | Gemini            |

This separation also allows additional analyzers or AI providers to be introduced independently.

---

## 🔐 API Key

Gemini credentials are **not stored in the repository**.

The framework reads the API key from the environment:

```text
GEMINI_API_KEY
```

Set the environment variable before running Gemini-powered features.

---

## 🛠️ Tech Stack

* Java 17
* Selenium WebDriver
* TestNG
* REST Assured
* Maven
* Extent Reports
* WebDriverManager
* Gemini API
* GitHub Actions

---

## ▶️ Running the Framework

### Run the TestNG suite

```bash
mvn test
```

### Use rule-based / fake AI mode

```properties
failure.analysis.strategy=RULE_BASED
execution.summary.provider=FAKE
```

This mode is useful for framework development and debugging without consuming Gemini API quota.

### Use Gemini

```properties
failure.analysis.strategy=GEMINI
execution.summary.provider=GEMINI
```

Make sure `GEMINI_API_KEY` is available in the environment.

---

## 📊 Reporting

Execution results are reported through **Extent Reports**.

The report includes:

* Test execution results
* Screenshots for failures
* Execution metadata
* AI failure analysis
* AI execution summary
* Overall execution health
* Key observations

The framework maintains a direct:

```text
ExecutionResult → ExtentTest
```

association so suite-level AI analysis can be mapped back to the correct test report entry.

---

## 🗺️ Roadmap

* [x] Phase 1 — Automation Framework
* [x] Phase 2.1 — AI Failure Analyzer
* [x] Phase 2.2 — AI Execution Summary
* [ ] Phase 2.3 — AI Test Case Generator
* [ ] Phase 2.4 — AI Locator Healing
* [ ] Phase 2.5 — AI Risk Prediction

---

## 📚 Learning & Design Notes

The repository includes [`notes.txt`](notes.txt), containing the learning process, design decisions, implementation questions, experiments and reasoning behind the framework architecture.

The notes are intentionally informal. **The implementation in the repository is the source of truth for the current architecture.**

---

## 🎯 Project Goal

The goal of this project is to explore how traditional UI/API test automation can be enhanced with AI to provide more meaningful insights than simple pass/fail results.

The current focus is on using execution data to:

**Execute → Analyze → Summarize**

Specifically:

* **Analyze** test failures using available execution evidence and context.
* **Summarize** complete test-suite executions, including results, retries, failures and recovery patterns.

Future phases will explore additional ways AI can assist the software testing lifecycle.

