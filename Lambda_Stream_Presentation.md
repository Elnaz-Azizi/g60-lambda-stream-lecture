![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Functional Programming, Lambdas & Stream API

---

## Table of Contents

1. Overview of Functional Programming
2. Functional Interface
3. Lambda Expressions
4. Prebuilt Functional Interfaces
5. Stream API

---

# 1. Overview of Functional Programming

Functional Programming (FP) is a programming paradigm that focuses on **using functions as the main building blocks** of
a program.

In Functional Programming:

- Programs are built by **combining small functions**
- Data is **transformed** instead of being **modified in place**
- The focus is on **what result** we want to achieve, not the exact steps to get it

Instead of writing step-by-step instructions, we describe **data transformations**.

Functional Programming describes a program as a **flow of data through functions**.

> Take input → apply a function → produce output  
> The output then becomes the input for the next function  
> This continues until we reach the final result

Each function:

- Does **one clear job**
- Takes an input
- Returns a **new value**
- Does not modify the original data

---

## Thinking in Flows and Pipelines

In Functional Programming, we often think in terms of **pipelines**.

Data moves **forward**, step by step, through a sequence of transformations.

```
Input 
    ↓
Apply an operation
    ↓
Produce a result
    ↓
Apply another operation on the result of the previous operation
    ↓
Produce a new result
    ↓
Apply another operation
    ↓
Final result
```

Each step:

- Does **one clear job**
- Does **not modify** the original data
- Produces a **new value** for the next step

We describe **what happens to the data**, not how each step is implemented.

One of the most important ideas in Functional Programming is the shift in thinking:

- From **how** to perform an operation (**imperative programming**)
- To **what result** we want to achieve (**functional programming or declarative programming**)

This change makes code easier to read, understand, and maintain.

---

### Imperative Thinking – “How do I do this?”

In imperative programming, we tell the computer **every step** it must perform.

We describe:

- How to loop
- How to update variables
- How to manage state

#### Example (Imperative Style)

```java
void main() {
    // Goal: Get the hire dates of all IT Developers hired after 2022-01-01
    List<LocalDate> result = new ArrayList<>();

    for (Employee e : employeeList) {
        // filter by department
        if (e.getDepartment().equals("IT")) {
            // filter by role
            if (e.getRole().equals("Developer")) {
                // filter by hire date
                if (e.getHireDate().isAfter(LocalDate.of(2022, 1, 1))) {
                    // convert Employee -> LocalDate
                    result.add(e.getHireDate());
                }
            }
        }
    }
}
```

Here we explain:

- How to loop
- How to check conditions
- How to modify a list
- How data changes step by step

The **how** is more visible than the **intent**.

---

### Functional Thinking – “What do I want?”

In functional programming, we describe **what should happen to the data**, not how to do it step by step.

We focus on:

- The transformation
- The final result
- The logic, not the mechanics

#### Example (Functional Style)

```java
// The goal is simple:
// Get the hire dates of all IT Developers hired after 2022-01-01
List<LocalDate> result = employeeList.stream()
                .filter(e -> e.getDepartment().equals("IT"))
                .filter(e -> e.getRole().equals("Developer"))
                .filter(e -> e.getHireDate().isAfter(LocalDate.of(2022, 1, 1)))
                .map(e -> e.getHireDate())
                .toList();
```

## Why & When Functional Programming

### Why use Functional Programming?

Functional Programming helps us write code that is **clearer, safer, and easier to maintain** by focusing on **what we
want to do with data**, rather than **how to do it step by step**.

We use Functional Programming because it:

- **Makes code easier to read** – the intent is clear at a glance
- **Reduces bugs** – data is transformed instead of modified
- **Improves maintainability** – small, independent functions are easier to change
- **Simplifies testing** – functions focus on input → output
- **Encourages clean design** – less shared state and fewer side effects

In short:

> **Functional Programming helps us focus on the result, not the steps.**

### When to use Functional Programming?

Functional Programming is especially useful when:

- **Working with collections of data** – filtering, mapping, and aggregating lists
- **Building data pipelines** – chaining multiple operations together
- **Avoiding side effects** – keeping data immutable and predictable
- **Writing declarative code** – expressing *what* should happen
- **Improving readability** – making business logic easier to understand
- **Using parallel processing** – safer execution across multiple threads

Functional Programming is not about replacing imperative code everywhere,  
but about choosing a **better model** when working with data transformations.


---

# 2. Functional Interface

A functional interface is an interface with **exactly one abstract method**.

- It represents **a single behavior**
- It can have multiple **default** or **static** methods  
  (only abstract methods are counted)
- It is often marked with the `@FunctionalInterface` annotation  
  *(optional but recommended)*

`@FunctionalInterface` ensures that:

- The interface always remains **lambda-compatible**
- The compiler catches **breaking changes** early

```java

@FunctionalInterface
public interface MyFunctionalInterface {
    void myMethod(String param);
}
```

Functional interfaces exist to support **lambda expressions**.

Because a functional interface has exactly one abstract method,
Java can map a lambda expression directly to that method.


---

# 3. Lambda Expressions

A lambda expression is a **short, anonymous function** that represents an implementation of a **functional interface**.

It allows us to define **behavior** without:

- Creating a separate class
- Writing anonymous inner classes
- Adding boilerplate code

In simple terms, a lambda lets us treat **logic as a value** that can be stored in a variable or passed to a method.

Lambda expressions were introduced to:

- **Reduce boilerplate code** – less syntax than anonymous classes
- **Improve readability** – focus on the rule, not the wrapper
- **Make behavior easier to pass as data**
- **Work naturally with functional interfaces**
- **Support functional programming in Java**

In short:

> Lambdas make functional interfaces practical and easy to use.

## How Lambda Expressions Work

A lambda expression implements the **single abstract method** of a functional interface.

Because a functional interface has exactly one abstract method, the compiler always knows:

- Which method the lambda refers to
- What parameters it takes
- What value it returns

### Lambda Syntax

```text
(parameters) -> expression or single statement
```

or

```text
(parameters) -> {
    // block of code or multiple statements
}
```

>
>- parameters → input to the method
>- -> → separates input from behavior, called arrow token
>- body → logic that is executed (and returned if needed)

### Example

```java

@FunctionalInterface
public interface TaskFilter {
    boolean matches(Todo todo);
}
```

> This interface doesn’t do anything by itself.
> It only defines a rule: “Given a Todo, return true or false.”


> We want to find tasks using different rules
> without rewriting the same loop every time.

```java
TaskFilter highPriorityFilter = task -> task.getPriority() == 1;
TaskFilter highPriorityIncomplete = task -> !task.isCompleted() && task.getPriority() == 1;
```

> This lambda is just an implementation of matches(Todo).
> What if tomorrow we want a different condition?

```java
public static List<Todo> findTasks(List<Todo> todoList, TaskFilter filter) {
    List<Todo> result = new ArrayList<>();
    for (Todo task : todoList) {
        if (filter.matches(task)) {
            result.add(task);
        }
    }
    return result;
}
```

## Advantages

### 1. Separation of Logic and Algorithm

With functional programming, we separate:

- **What we want to do** (the condition or rule)
- **How it is applied** (the loop or algorithm)

```java
findTasks(todoList, filter);
```

The algorithm (`findTasks`) stays the same,  
only the behavior (`filter`) changes.

This makes code:

- Easier to understand
- Easier to reuse
- Easier to maintain

---

### 2. Reusability Through Behavior

Instead of writing many similar loops, we reuse one loop and plug in different behaviors.

```java
TaskFilter notCompleted = task -> !task.isCompleted();
TaskFilter highPriority = task -> task.getPriority() == 1;
```

The same method works with many rules.

---

### 3. Easier Testing

Each lambda represents a **small, focused rule**.

```java
TaskFilter highPriority = task -> task.getPriority() == 1;
```

This makes logic:

- Easier to test
- Easier to reason about
- Easier to debug

---

### 4. Improved Readability

When behavior is named and extracted, code becomes self-describing.

```java
findTasks(todoList, highPriorityIncomplete);
```

# 4. Prebuilt Functional Interfaces

So far, we have seen how to **create our own functional interfaces** to represent behavior.
This helps us understand *what* a functional interface is and *why* it exists.

However, Java already provides many **prebuilt functional interfaces** that cover the most common use cases.
Instead of creating new interfaces every time, we can often reuse these existing ones.

These interfaces are found in the package:

```
java.util.function
```

They represent **common types of behavior** such as:

- Checking a condition
- Transforming a value
- Consuming a value
- Producing a value

Prebuilt functional interfaces exist to:

- Reduce boilerplate code
- Avoid reinventing common interfaces
- Improve consistency across Java APIs
- Work seamlessly with **lambdas** and the **Stream API**

## The Most Important Prebuilt Functional Interfaces

Java provides several **prebuilt functional interfaces** in the package `java.util.function`.
They represent **common types of behavior** that appear in almost every application.

---

### 1. `Predicate<T>` — A Condition

**What it is**  
A `Predicate` represents a **condition** that returns `true` or `false`.

```java
boolean test(T value);
```

**When to use it**  
Use `Predicate` when you need to **filter**, **validate**, or **check rules**.

---

### 2. `Function<T, R>` — A Transformation

**What it is**  
A `Function` converts one value into another.

```java
R apply(T value);
```

**When to use it**  
Use `Function` when you need to **transform data** or **extract values**.

---

### 3. `Consumer<T>` — An Action

**What it is**  
A `Consumer` performs an **action** on a value and returns nothing.

```java
void accept(T value);
```

**When to use it**  
Use `Consumer` when you need to **modify data** or perform **side effects**.

---

### 4. `Supplier<T>` — A Value Provider

**What it is**  
A `Supplier` provides a value **without input**.

```java
T get();
```

**When to use it**  
Use `Supplier` for **lazy values**, **defaults**, or **factories**.

---

## Additional Common Prebuilt Functional Interfaces

---

### 5. `BiPredicate<T, U>` — A Condition with Two Inputs

**What it is**  
A `BiPredicate` represents a condition that depends on **two values**.

```java
boolean test(T t, U u);
```

**When to use it**  
Use `BiPredicate` when a rule depends on **two related inputs**.

---

### 6. `BiFunction<T, U, R>` — A Transformation with Two Inputs

**What it is**  
A `BiFunction` combines **two input values** into one result.

```java
R apply(T t, U u);
```

**When to use it**  
Use `BiFunction` when the output depends on **two inputs**.

---

### 7. `UnaryOperator<T>` — A Transformation of the Same Type

**What it is**  
A `UnaryOperator` transforms a value **without changing its type**.

```java
T apply(T value);
```

**When to use it**  
Use `UnaryOperator` when updating or modifying a value of the same type.

---

### 8. `BinaryOperator<T>` — Combining Two Values of the Same Type

**What it is**  
A `BinaryOperator` combines **two values of the same type** into one.

```java
T apply(T a, T b);
```

**When to use it**  
Use `BinaryOperator` when reducing or aggregating values.

---

| Interface           | Inputs | Output  | Typical Use      |
|---------------------|--------|---------|------------------|
| Predicate<T>        | 1      | boolean | Filtering        |
| Function<T, R>      | 1      | R       | Mapping          |
| Consumer<T>         | 1      | void    | Side effects     |
| Supplier<T>         | 0      | T       | Value creation   |
| BiPredicate<T, U>   | 2      | boolean | Comparisons      |
| BiFunction<T, U, R> | 2      | R       | Combining values |
| UnaryOperator<T>    | 1      | T       | Updating values  |
| BinaryOperator<T>   | 2      | T       | Reductions       |

---


# 5. Stream API

Stream API is a **collection-like API** for processing data in **functional programming**.
It is an Application Programming Interface (API) that provides a **pipeline** of operations to process data.

The **Stream API** allows us to process collections of data using **functional programming concepts**.
It is built around the idea of a **pipeline**, where data flows through a sequence of operations.

A **stream** is a sequence of elements that:
- Comes from a data source (collection, array, file, etc.)
- Is processed step by step
- Produces a result

Important:
- A stream does **not store data**
- A stream **processes data from a source**

---

## Stream Pipeline

A **stream pipeline** describes how data flows through a program.

It consists of **three main parts**:

```
Source → Intermediate Operations → Terminal Operation
```

### 1. Source
The source provides the data.

Examples:
- `List.stream()`
- `Set.stream()`
- `Arrays.stream(array)`

```java
employees.stream();
```

---

### 2. Intermediate Operations
Intermediate operations:
- Transform the stream
- Return a **new stream**
- Are **lazy** (not executed immediately)

Examples:
- `filter`
- `map`
- `sorted`
- `limit`
- `distinct`

```java
employees.stream()
         .filter(Employee::isActive)
         .map(Employee::getEmail);
```

Nothing happens yet — the pipeline is only **defined**, not executed.

---

### 3. Terminal Operation
The terminal operation:
- **Triggers execution** of the pipeline
- Produces a final result or side effect
- Ends the stream

Examples:
- `toList()`
- `forEach()`
- `count()`
- `reduce()`

```java
employees.stream()
         .filter(Employee::isActive)
         .map(Employee::getEmail)
         .toList();
```

---

## Stream Pipeline Architecture

Internally, the Stream API follows a **pull-based architecture**:

- The terminal operation requests elements
- Each intermediate operation processes elements **one by one**
- Elements flow through the pipeline only when needed

This enables:
- **Lazy evaluation**
- **Better performance**
- **Short-circuiting** (early termination)

---

## Lazy Evaluation

Streams are **lazy**.

This means:
- No work is done until a terminal operation is called
- Operations are applied only when needed

Example:

```java
employees.stream()
         .filter(e -> {
             System.out.println("Filtering " + e.getName());
             return e.isActive();
         });
```

Nothing is printed because there is **no terminal operation**.

---

## Short-Circuiting

Some terminal operations stop the pipeline early.

Examples:
- `findFirst()`
- `anyMatch()`
- `limit()`

```java
boolean hasExpired =
    employees.stream()
             .filter(Employee::isActive)
             .anyMatch(Employee::isExpired);
```

The stream stops as soon as a match is found.

---

## Common Intermediate Operations

- `filter(Predicate<T>)` → keeps matching elements
- `map(Function<T, R>)` → transforms elements
- `sorted()` → sorts elements
- `limit(n)` → keeps first n elements
- `distinct()` → removes duplicates

---

## Common Terminal Operations

- `toList()` → collects results
- `forEach(Consumer<T>)` → performs actions
- `count()` → counts elements
- `reduce(BinaryOperator<T>)` → combines elements
- `anyMatch / allMatch / noneMatch` → checks conditions

---

## Complete Example Pipeline

```java
List<String> emails =
    employees.stream()                  // source
             .filter(Employee::isActive) // intermediate
             .map(Employee::getEmail)    // intermediate
             .toList();                  // terminal
```

Reads like:

> From employees, keep active ones, extract emails, collect into a list.

---

## Collection Framework vs Stream API

- The **Collection Framework** is used to **store and manage data** in memory.
- Collections hold elements such as `List`, `Set`, or `Map` and can be modified.
- A collection can be **reused** and accessed multiple times.
- The **Stream API** is used to **process data**, not to store it.
- A stream works on data from a source (usually a collection).
- Streams are **immutable** and do not change the original data.
- A stream is **single-use** and cannot be reused once consumed.
- Collections focus on **data**, while streams focus on **operations**.
- Collections are **eager**, streams are **lazy**.
- Collections store data; **streams process data through a pipeline**.

