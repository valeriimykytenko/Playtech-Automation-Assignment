# Playtech Automation Assignment

[![Selenium Tests](https://github.com/valeriimykytenko/Playtech-Automation-Assignment/actions/workflows/TESTS.yml/badge.svg)](https://github.com/valeriimykytenko/Playtech-Automation-Assignment/actions/workflows/TESTS.yml)

## Overview

This project is an automated test solution for the Playtech QA internship assignment.
It uses Java, Selenium, and JUnit to validate website content and extract required data 

### Tech Stack
- Java 21
- Selenium
- JUnit 6
- Maven

## How to Run

### Clone Repository
```bash
git clone https://github.com/valeriimykytenko/Playtech-Automation-Assignment.git
```
### Run Main
```bash
mvn compile exec:java -Dexec.mainClass="playtechParse.main.Main"
```

### Run Tests
```bash
mvn test
```

### Run Tests (with custom URL)
```bash
mvn test "-Dbase.url=https://www.playtechpeople.com"
```

## Architecture

The project follows the **Page Object Model** pattern — all interactions with the website
which keeps in `PlaytechWebsite`, that is keeping all data extraction logic from the UI.  
`BasePage` provides Selenium utilities such as waits, JavaScript interaction, and scroll.  
`WebDriverManager` manages browser setup and teardown.  
`ReportService` handles output both to console and file.

`PlaytechTest` contains end-to-end test scenarios using JUnit.   
`Main` provides an entry point to execute the same logic without a test framework, for quick runs and report generation.

## Output
Results are printed to the console and saved to `reports/results.txt` after each run.
A sample output file is included in the repository.