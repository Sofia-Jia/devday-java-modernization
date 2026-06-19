#!/bin/bash

# Test Runner Script for TD Bank Java Modernization Labs
# This script compiles and runs JUnit 5 tests

echo "=========================================="
echo "TD Bank Java Modernization - Test Runner"
echo "=========================================="
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Error: Maven is not installed or not in PATH"
    echo "Please install Maven: https://maven.apache.org/install.html"
    exit 1
fi

echo "📦 Cleaning previous builds..."
mvn clean

echo ""
echo "🔨 Compiling code..."
mvn compile

# Check compilation results
if [ $? -ne 0 ]; then
    echo ""
    echo "⚠️  EXPECTED COMPILATION ERROR DETECTED"
    echo "=========================================="
    echo ""
    echo "This is INTENTIONAL for Lab 4 teaching purposes!"
    echo ""
    echo "The Payment.java file contains multiple public classes in one file,"
    echo "which violates Java rules. This legacy code pattern will be fixed"
    echo "in Lab 4 by converting to sealed classes."
    echo ""
    echo "To proceed with labs:"
    echo "  • Lab 1-3: Focus on Order.java (Date/Time migration)"
    echo "  • Lab 4: Fix Payment.java by converting to sealed classes"
    echo ""
    echo "See Payment.java comments for details."
    echo "=========================================="
    exit 1
fi

echo ""
echo "🧪 Running tests..."
mvn test

# Check test results
if [ $? -eq 0 ]; then
    echo ""
    echo "✅ All tests passed!"
    echo ""
    echo "📊 Test Report: target/surefire-reports/"
else
    echo ""
    echo "❌ Some tests failed. Review the output above."
    echo ""
    echo "📊 Test Report: target/surefire-reports/"
    exit 1
fi

# Made with Bob
