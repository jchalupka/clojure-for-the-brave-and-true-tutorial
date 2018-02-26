[TOC]

# Part 1

## Chapter 1

###*What is clojure?*

- Combines lisp, functional programming, with some unique qualities
- 'Clojure' refers to the language and the compiler
- Clojure is a *hosted* language.  This means that clojure programs are executed within a JVM and rely on the JVM for core features. (Implementations are available for Javascript amoung others)

###Leiningen

- Used to build and manage projects
- Has a clojure REPL

####Basic commands

Creating a new project

```
lein new app clojure-noob
```

Running the project

```
lein run
# Hello, World!
```

Building

```
lein uberjar
# This will create a file named 'target/uberjar/clojure-noob-0.1.0-SNAPSHOT-standalone.jar'.
```

Run the new jar file

```
java -jar target/uberjar/clojure-noob-0.1.0-SNAPSHOT-standalone.jar
```

REPL

```
lein repl
```

Playing with the REPL

```
clojure-noob.core=>
clojure-noob.core=>(-main) # execute the main function
# Hello, World!
# nil
```



## Chapter 2

## How to Use Emacs, an Excellent Clojure Editor (skipped)

(I've decided to skip the emacs part of this chapter as I will be continuing to use VS Code and Vim as my main text editors).