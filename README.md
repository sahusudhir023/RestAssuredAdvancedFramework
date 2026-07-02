# RESTASSURED-API-ADVANCED-FRAMEWORK

A robust and production-ready API Automation Framework built from scratch using RestAssured, TestNG, and Java. This project implements advanced automation concepts like Request/Response Spec Builders, Serialization/Deserialization (POJO), Data-Driven testing, and automated Extent Reporting.

## 📂 PROJECT ARCHITECTURE

The framework follows a modular structure to maintain a clear separation between tests, utilities, and test data:

* **`com.automation.api.tests`**: Contains core CRUD execution tests (GET, POST, PUT, DELETE).
* **`com.automation.api.payloads`**: Handles Data Modeling via POJO classes for request and response body payloads.
* **`com.automation.api.utilities`**: 
  * `ConfigReader`: Manages environment-specific configuration dynamically via properties file.
  * `ExtentReportManager`: Captures runtime test outcomes and automatically embeds request/response payloads into HTML reports.
* **`src/test/resources`**: Contains `config.properties` for managing configuration data like URLs and environment details.

## 💻 TECHNICAL STACK & KEY PRACTICES

* **Core Tool**: RestAssured (Fluent Chain Validation)
* **Execution & Assertions**: TestNG (Prioritization, DataProvider)
* **Reporting**: Extent Reports (Spark Reporter with Dark Theme)
* **Data Parsing**: JsonPath & Java Collections (HashMap implementation)
* **Design Concept**: OOPs Principles applied (Strict Encapsulation for global specifications)

## 🛠️ HOW TO EXECUTE
1. Clone the repository to your local workspace.
2. Ensure Maven dependencies are fully updated via `pom.xml`.
3. Right-click on `testng.xml` and run as **TestNG Suite**.
4. Check generated execution logs in `logging.txt` and interactive reports in the `target/ExtentReport.html` path.