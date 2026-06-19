#!/bin/bash

# Test Runner Script for Java Modernization Labs
# This script compiles and runs JUnit 5 tests

echo "=========================================="
echo "Java Modernization - Test Runner"
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
