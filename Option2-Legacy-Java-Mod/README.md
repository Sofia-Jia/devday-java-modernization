# Java Modernization Labs

Welcome to the Java Modernization hands-on labs! These labs are designed for migrating legacy Java 8 applications to modern Java 21, covering Date/Time APIs, advanced language features, and full-stack integration.

## 🎯 Lab Overview

This bobathon includes 5 progressive labs that take you from analyzing legacy code to building a complete modern application:

| Lab | Duration | Focus | Difficulty |
|-----|----------|-------|------------|
| [Lab 1: Analyze Legacy Code](lab1-analyze-legacy/instructions.md) | 10mins | Identify Date/Calendar issues | Beginner |
| [Lab 2: Migrate Date/Time APIs](lab2-migrate-datetime/instructions.md) | 1hr | Hands-on migration with Bob | Intermediate |
| [Lab 3: Validate Results](lab3-validate-results/instructions.md) | 20mins | Testing and documentation | Intermediate |
| [Lab 4: Advanced Patterns](lab4-advanced-patterns/instructions.md) | 1hr | Records, Pattern Matching, Sealed Classes | Advanced |
| [Lab 5: Frontend Integration](lab5-built-your-frontend/instructions.md) | 1hr | React frontend with Spring Boot REST API | Advanced |

## 📋 Prerequisites

Before starting the labs, ensure you have:

1. **Java 21 JDK** installed and configured
   - See [Java 21 Download](https://www.oracle.com/java/technologies/downloads/#java21)
   ```bash
   java -version  # Should show version 21.x.x
   ```

2. **Maven 3.6+** for building and testing
   - See [Maven Download](https://maven.apache.org/install.html)
   ```bash
   mvn -version
   ```

3. **IBM Bob IDE** installed and authenticated
   - See [Bob Download](https://bob.ibm.com/download)

**Hint**: You can ask Bob to install the Java JDK and Maven for you!


## 📁 Repository Structure

### `/legacy-codebase` - Your Working Directory

This is the main Java 8 e-commerce application that you'll modernize throughout all labs:

```
legacy-codebase/
├── pom.xml                 # Maven configuration (Java 8)
├── run-tests.sh           # Test runner script
└── src/
    ├── main/java/com/example/ecommerce/
    │   ├── model/
    │   │   ├── Order.java          # Uses legacy Date/Calendar
    │   │   ├── Payment.java        # Multiple public classes (Lab 4 fix)
    │   │   ├── Product.java
    │   │   └── OrderStatus.java
    │   └── service/
    │       ├── OrderService.java
    │       ├── PaymentService.java
    │       ├── InventoryService.java
    │       └── AsyncService.java
    └── test/java/com/example/ecommerce/
        └── model/
            └── OrderTest.java      # JUnit 5 tests
```

### Lab Folders

Each lab folder (`lab1-analyze-legacy/`, `lab2-migrate-datetime/`, etc.) contains:
- **`instructions.md`** - Step-by-step guide for that lab
- **Lab 5 only:** `completed/` folder with a reference implementation

```
lab1-analyze-legacy/
├── instructions.md          # Step-by-step guide

...

lab5-built-your-frontend/
├── instructions.md
└── completed/              # Reference implementation
    ├── legacy/            # Completed backend (mvn spring-boot:run)
    └── frontend/          # Completed React app (npm install && npm run dev)
```
### Running Completed Sample
```bash
# Lab 5: Run the completed sample
cd lab5-built-your-frontend/completed/legacy
mvn spring-boot:run

# In another terminal
cd lab5-built-your-frontend/completed/frontend
npm install && npm run dev
```


## 🧪 Running Tests

Each lab includes a complete test suite to validate your changes. All labs use **JUnit 5 (Jupiter)** for testing.

**⚠️Note:** If you encounter compilation errors when running tests on the legacy codebase, this is intentional - the Payment.java file contains multiple public classes that need to be fixed in Lab 4 by converting to sealed classes.


### Quick Test Execution

Each lab has a convenient test runner script:

```bash
# Navigate to legacy folder
cd legacy-codebase/

# Run the test script
./run-tests.sh
```


### Understanding Test Results

- **✅ Green tests** = Code works correctly
- **❌ Red tests** = Issues found (expected in Lab 1 legacy code)
- **Test reports** = Located in `target/surefire-reports/`


## 🛠️ Troubleshooting

If you hit an error, you can ask **Bob directly** for help.🤖

Examples:
- "I got this Maven error. What does it mean and how do I fix it?"
- "My tests are failing. Help me debug the failure."
- "The frontend can't reach the backend. Can you help me troubleshoot it?"


## 💡 Tips for Success

### Working with Bob

1. **Be specific:** "Migrate Order.java Date fields to LocalDateTime"
2. **One step at a time:** Don't try to migrate everything at once
3. **Test frequently:** Run tests after each change
4. **Review changes:** Review Bob's suggestions before applying

## 🎯 Success Criteria

By the end of this bobathon, you should be able to:

- ✅ Identify problems with legacy Date/Calendar APIs
- ✅ Migrate Date/Calendar to java.time APIs using Bob
- ✅ Handle timezones correctly with ZonedDateTime
- ✅ Write and run JUnit 5 tests to validate migrations
- ✅ Apply modern Java 21 features (records, pattern matching, sealed classes)
- ✅ Build REST APIs with Spring Boot 3.x
- ✅ Create React frontends that integrate with Java backends
- ✅ Document migration decisions for your team
- ✅ Apply these patterns to your own applications


## 📝 Feedback

We'd love to hear about your experience! After completing the labs, please share:

- What worked well?
- What was challenging?
- Suggestions for improvement?

---

**Ready to start?** Head to [Lab 1: Analyze Legacy Code](lab1-analyze-legacy/instructions.md) and begin your Java modernization journey! 🚀
