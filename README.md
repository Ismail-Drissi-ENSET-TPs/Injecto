# Injecto

A lightweight dependency injection framework created for educational purposes to demonstrate the principles of dependency injection and inversion of control.

## Features
- Field-based dependency injection using annotations:
  - `@Inject`: Injects dependencies based on type
  - `@InjectValue(value="")`: Injects literal values into fields

## Core Components

### InjectoClassPathsFinder
Scans the classpath to discover target classes and retrieves their fully qualified class names for subsequent class loading operations.

### InjectoClassLoader
Instantiates `Class` objects for each discovered class and maintains a mapping between their fully qualified names and corresponding `Class` instances.

### InjectoInjectDependency
Handles the dependency injection process using Java Reflection. It analyzes class fields and methods at runtime to construct fully initialized objects.

## Usage
To obtain a fully initialized instance of a class with all its dependencies:
```java
MyClass instance = injectoDependency.getBean(MyClass.class);