# Lab 3: Validate Migration & Document Results

**Duration:** 20 minutes  
**Difficulty:** Beginner  
**Focus:** Validation, testing, and documenting business value

## 🎯 Objectives

By the end of this lab, you will:
- Validate the Date/Time migration is complete
- Verify code compiles and tests pass
- Document improvements and business value

## 🔨 Exercises

### Exercise 1: Validate Migration Completeness (5 min)

#### Step 1: Ask Bob to Verify

1. **Switch to Plan Mode** (📝) in Bob IDE

2. **Add the migrated Order.java to context**

3. **Ask Bob to validate:**

```
Review the migrated Order.java and verify:
1. All Date/Calendar usage has been replaced with java.time API
2. Timezone handling is explicit where needed
3. Code is thread-safe
4. Business logic is preserved
5. No legacy date/time APIs remain

Provide a validation report.
```

#### Step 2: Review Bob's Validation Report

Bob will confirm:

**✅ Migration Complete:**
- All Date → LocalDateTime/ZonedDateTime
- All Calendar → ZonedDateTime
- All SimpleDateFormat → DateTimeFormatter
- No legacy APIs remain

**✅ Code Quality:**
- Thread-safe by default
- Explicit timezone handling
- Follows Java 21 best practices

**✅ Business Logic Preserved:**
- isOverdue() logic correct
- Date comparisons accurate
- Settlement calculations valid
- No functional regressions

---

### Exercise 2: 🎯 Upgrade from Java 8 to Java 21 (5 min)

#### Step 1: Update Maven Configuration to Java 21

**Ask Bob:**

```
Update the pom.xml to officially upgrade from Java 8 to Java 21:
- Change compiler source from 8 to 21
- Change compiler target from 8 to 21
- Add compiler release property set to 21
- Ensure all dependencies are Java 21 compatible
```

**What's changing:**
- ❌ Java 8 (released 2014, end-of-life 2019)
- ✅ Java 21 (LTS release 2023, supported until 2031)

**Benefits you'll gain:**
- 🔒 5+ years of security patches and improvements
- ⚡ Better performance and memory efficiency
- 🎯 Access to modern language features (Records, Pattern Matching, Sealed Classes)
- 📦 Enhanced APIs and libraries

#### Step 2: Compile with Java 21
Compiling with Java 21 JDK exposes security debt through compiler warnings, this is the starting line for your refactoring journey in Lab 4.
```bash
cd legacy-codebase
mvn clean compile
```

**⚠️ INTENTIONAL COMPILATION ERROR:**

If you have the `Payment.java` file in your codebase, compilation will **fail** with errors like:

```
[ERROR] Payment.java:[45,14] class CreditCardPayment is public, should be declared in a file named CreditCardPayment.java
[ERROR] Payment.java:[75,14] class PayPalPayment is public, should be declared in a file named PayPalPayment.java
[ERROR] Payment.java:[93,14] class BankTransferPayment is public, should be declared in a file named BankTransferPayment.java
```

**Why This Error Occurs:**
- `Payment.java` contains **multiple public classes** in a single file (lines 45, 75, 93)
- Java only allows **ONE public class per file**, and the filename must match the class name
- This is an **intentional legacy code anti-pattern** to demonstrate the need for modernization

**This is expected behavior** - Lab 4 will resolve it with sealed classes


#### Step 3: Run Tests (if available)

```bash
mvn test
```

---

### Exercise 3: Document Improvements (5 min)

#### Step 1: Generate Improvement Summary

**Ask Bob:**

```
Create a concise summary document comparing the before and after state:
- Lines of code reduced
- Thread-safety improvements
- Timezone handling enhancements
- Maintainability gains
- Business value delivered

Format as a markdown report.
```

---

## ✅ Success Criteria

You've completed this lab when:
- [ ] Migration validated and completed
- [ ] Improvements documented

## 🎯 Next Steps

**Explore Full Lab 4** - In [Lab 4](../lab4-advanced-patterns/instructions.md), you'll explore the following advanced Java patterns:
   - Records and Sealed Classes
   - Pattern Matching
   - Stream API improvements
   - Virtual Threads (Java 21)
