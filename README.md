# QA Assessment - Playwright Java Framework

## Prerequisites

Before running the tests, ensure you have:

- ☕ Java 17 or higher - Download Java
- 📦 Maven 3.6 or higher - Download Maven
- 🌐 Git - Download Git
- Add Sony products to cart
- 💻 IDE (IntelliJ IDEA recommended) - Download IntelliJ

## Verify Installation

```bash
# Check Java version
java -version


# Check Maven version  
mvn -version


# Check Git version
git --version
```


## 📥 Installation

### Step 1: Clone Repository

```bash
# Clone the project

git clone https://github.com/m-amaann/qa-playwright-assesment.git
cd qa-playwright-assesment
```

### Step 2: Install Dependencies

```bash

# Clean and install Maven dependencies
mvn clean install
```

### Step 3: Install Playwright Browsers

```bash

# Install Playwright browsers (Chrome, Firefox, Safari)
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

### Step 4: Install Playwright Browsers

```bash

# Install Playwright browsers (Chrome, Firefox, Safari)
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```


## 🧪 Running Tests

```bash
# Command Line

mvn test                    # Run all tests
```

## Configuration

```bash
# Edit src/test/resources/config.properties:

app.url=https://www.demoblaze.com/
browser.headless=false
timeout=30000
```

