# Study Room Booking — TDD Assessment

This repository contains my Java and JUnit work for the Agile Software Verification Study Room Booking assessment.

## Aim

Develop the booking system using test-driven development (TDD): write a test first, run it and observe it fail, implement the smallest change needed to pass, then refactor only when the tests are green.

## Tools

- Java 21
- JUnit Platform Console Standalone 6.1.3
- Visual Studio Code
- Git and GitHub

## Project structure

```text
.
├── README.md
├── NOTES.md
├── .gitignore
├── junit.jar                 # local test runner; not committed
└── src/
    ├── main/java/            # production Java classes
    └── test/java/            # JUnit tests
```

All Java classes should remain in the default package: do not add `package` declarations.

## Running the tests on Windows

Download the JUnit Platform Console Standalone 6.1.3 JAR into the repository root and name it `junit.jar`. Run these commands in PowerShell once Java source and test files exist:

```powershell
New-Item -ItemType Directory -Force out
$sources = Get-ChildItem -Path src/main/java,src/test/java -Filter *.java -Recurse | ForEach-Object { $_.FullName }
javac -d out -cp junit.jar $sources
java -jar junit.jar execute --class-path out --scan-class-path
```

If the lecturer's assessment brief specifies a different command or setup, follow the brief.

## TDD and Git workflow

- Write a meaningful failing test before changing production code.
- Make small, focused changes and commit them using the required commit-message format from the assessment brief.
- Run the tests after each change.
- Push regularly, and ensure the final branch and complete commit history are pushed before submission.

See [NOTES.md](NOTES.md) for genuine issues, decisions, feedback, and corrections encountered while working.