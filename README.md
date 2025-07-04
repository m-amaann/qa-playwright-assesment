# QA Assessment - Playwright Java Framework

## 📋 Test Scenarios Covered
This project automates the complete e-commerce workflow for https://www.demoblaze.com/

- Sign up with unique user
- Login with created user
- Search for Sony laptops
- Add Sony products to cart
- Remove Sony vaio i5 from cart
- Place order successfully

## Quick Setup

### Clone and navigate
git clone <repository-url>
cd playwright-framework-qa-assessment

### Install dependencies

mvn clean install

### Install Playwright browsers
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"

### Run tests

```
mvn test 
```

## 🧪 Running Tests

```bash
# Command Line

mvn test                    # Run all tests
mvn test -Dtest=ECommerceTest  # Run specific test
```

## Configuration

```bash
# Edit src/test/resources/config.properties:

app.url=https://www.demoblaze.com/
browser.headless=false
timeout=30000
```

