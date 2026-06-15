# Lab 4: Advanced Java 21 Patterns

**Duration:** 1 hour
**Difficulty:** Advanced  
**Prerequisites:** Completed Labs 1-3 or familiar with java.time APIs

## 🎯 Objectives

By the end of this lab, you will:
- Use **Records** for immutable data classes
- Apply **Pattern Matching** for cleaner instanceof checks
- Implement **Sealed Classes** for controlled type hierarchies
- Leverage **Text Blocks** for readable multi-line strings

## 📚 Background

Beyond Date/Time APIs, Java 21 introduces powerful features that make code more concise, safer, and easier to maintain. This lab explores these modern patterns in an enterprise context.

**Note:** The legacy codebase intentionally contains a compilation error (multiple public classes in Payment.java) that will be resolved in Exercise 3 by converting to sealed classes.

## 🔍 What You'll Learn

### 1. Records (JEP 395)
Replace verbose POJOs with concise, immutable data carriers:

```java
// Old way (50+ lines)
public class Transaction {
    private final String id;
    private final BigDecimal amount;
    // constructor, getters, equals, hashCode, toString...
}

// New way (1 line!)
public record Transaction(String id, BigDecimal amount) {}
```

### 2. Pattern Matching for instanceof (JEP 394)
Eliminate casting boilerplate:

```java
// Old way
if (payment instanceof CreditCardPayment) {
    CreditCardPayment cc = (CreditCardPayment) payment;
    processCard(cc.getCardNumber());
}

// New way
if (payment instanceof CreditCardPayment cc) {
    processCard(cc.cardNumber());
}
```

### 3. Sealed Classes (JEP 409)
Control which classes can extend/implement:

```java
// Define allowed subtypes
public sealed interface Payment 
    permits CreditCardPayment, DebitCardPayment, WireTransfer {}

// Compiler ensures exhaustive handling
```

### 4. Text Blocks (JEP 378)
Write readable multi-line strings:

```java
// Old way
String json = "{\n" +
              "  \"account\": \"123\",\n" +
              "  \"amount\": 100.00\n" +
              "}";

// New way
String json = """
    {
      "account": "123",
      "amount": 100.00
    }
    """;
```

## 🛠️ Lab Exercises

### Exercise 1: Convert POJOs to Records (8 minutes)

**Goal:** Simplify data classes using Records

1. **Open the legacy code:**
   ```bash
   cd legacy-codebase/src/main/java/com/example/ecommerce/model
   ```

2. **Identify candidates for Records:**
   - Look at `Product.java`, `Payment.java`
   - These are immutable data carriers - perfect for Records!

3. **Use Bob to convert Customer to a Record:**

   **Prompt Bob:**
   ```
   Convert Customer.java to a Java 21 Record. Keep all fields but remove
   the constructor, getters, equals, hashCode, and toString methods since
   Records provide these automatically.
   ```

4. **Review the changes:**
   - Notice how much code was eliminated
   - Records are final and immutable by default
   - All methods are auto-generated

5. **Run tests to verify:**
   ```bash
   cd legacy-codebase
   ./run-tests.sh
   ```

**Expected Result:** Data classes reduced significantly with Records!

### Exercise 2: Apply Pattern Matching (5 minutes)

**Goal:** Simplify instanceof checks in payment processing

1. **Open PaymentService.java:**
   ```bash
   cd legacy-codebase/src/main/java/com/example/ecommerce/service
   ```

2. **Find the processPayment method:**
   - Look for multiple instanceof checks with casting
   - This is verbose and error-prone

3. **Use Bob to apply pattern matching:**

   **Prompt Bob:**
   ```
   In PaymentService.processPayment(), replace all instanceof checks 
   with pattern matching. For example, change:
   
   if (payment instanceof CreditCardPayment) {
       CreditCardPayment cc = (CreditCardPayment) payment;
       // use cc
   }
   
   to:
   
   if (payment instanceof CreditCardPayment cc) {
       // use cc directly
   }
   ```

4. **Verify the changes:**
   - Casting is eliminated
   - Variables are scoped to the if block
   - Code is more readable

### Exercise 3: Implement Sealed Classes (5 minutes)

**Goal:** Create a controlled payment type hierarchy

This exercise will resolve the intentional compilation error (multiple public classes in Payment.java).

1. **Create a sealed Payment interface:**

   **Prompt Bob:**
   ```
   Create a sealed interface Payment in the model package that permits 
   only CreditCardPayment, DebitCardPayment, and WireTransfer. Then 
   update these three classes to implement Payment and be final.
   ```

2. **Update PaymentService to use exhaustive switch:**

   **Prompt Bob:**
   ```
   In PaymentService.processPayment(), replace the if-else chain with 
   a switch expression that handles all Payment types exhaustively. 
   The compiler should verify all cases are covered.
   ```

3. **Observe the benefits:**
   - Compiler enforces exhaustive handling
   - Adding new payment types requires explicit handling
   - No default case needed (compiler knows all types)

### Exercise 4: Add Text Blocks for JSON (5 minutes)

**Goal:** Improve readability of multi-line strings

1. **Find JSON generation code:**
   - Look in `OrderService.java` for JSON string building

2. **Use Bob to convert to text blocks:**

   **Prompt Bob:**
   ```
   Replace the JSON string concatenation in OrderService with a text 
   block. Use triple quotes and proper indentation.
   ```

3. **Compare before and after:**
   - No more escape sequences
   - Natural indentation
   - Much easier to read and maintain

## 🧪 Testing Your Changes

After each exercise, run the test suite:

```bash
cd legacy-codebase
mvn clean compile
./run-tests.sh
```

If any test failures occur, work with Bob to resolve them!🤖🛠️

**What to verify:**
- ✅ All tests pass (green)
- ✅ No compilation errors
- ✅ Code is more concise
- ✅ Functionality is preserved

## 🤖 Bob Differentiators in This Lab

### 1. **Literate Coding**
Bob explains why Records are better than traditional POJOs, helping you understand the benefits beyond just syntax.

### 2. **Multi-Mode Intelligence**
- **Ask Mode:** "What are the benefits of sealed classes?"
- **Code Mode:** Apply the pattern matching transformation
- **Plan Mode:** Design the sealed hierarchy before implementing

### 3. **Intelligent Model Selection**
For simple refactorings (Records), Bob uses faster models. For complex logic (sealed classes), it uses more powerful models automatically.

### 4. **Enterprise Modernization**
Bob specializes in Java modernization, understanding patterns like Records, sealed classes, and pattern matching that are crucial for enterprise applications.


### Further Learning

- **JEP 395:** Records - https://openjdk.org/jeps/395
- **JEP 394:** Pattern Matching - https://openjdk.org/jeps/394
- **JEP 409:** Sealed Classes - https://openjdk.org/jeps/409
- **JEP 378:** Text Blocks - https://openjdk.org/jeps/378

## ✅ Completion Checklist

- [ ] Converted at least 2 classes to Records
- [ ] Applied pattern matching in PaymentService
- [ ] Implemented sealed Payment hierarchy
- [ ] Added text blocks for multi-line strings

## 🎉 Congratulations!

You've mastered advanced Java 21 patterns! These features will make your applications more maintainable, safer, and easier to understand. In [Lab 5](../lab5-built-your-frontend/instructions.md), you will build a demo front-end for this application.

**Time to build out the front end for this application!** 🚀

