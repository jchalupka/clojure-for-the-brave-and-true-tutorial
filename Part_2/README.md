[TOC]

# Part 2

## Chapter 3: Do Things: A Clojure Crash Course

### Forms

Clojure recognizes two kinds of structures:

- Literal representations of data structures
- Operations

Clojure evaluates every form to produce  a value.

E.g.

```clojure
; The following are all valid forms
1
"A string"
["A" "vector" "of" "strings"]
```

These free-floating literals are great but we'll want to include some operations so that we can get things done.

```clojure
; operations take the form of
; (operator operand1 operand2 ... operandn)
(+ 1 2 3)
; 6
```

### Control Flow

#### if

```
(if boolean-form
	then-form
	optional-else-form)
```

```clojure
(if true
	1
	2)
; 1
```

If the if operator's boolean form is evaluated as falsy, and there is no else form, the if form will evaluate as nil.

```clojure
(if false
	"No else statement ):")
; nil
```

#### do

We can use the 'do' operator to allow for multiple then and/or else forms.

```clojure
(if true
    (do (prinln "Success")
        "What a wonderful world")
    (do (println "Failure")
        "We have failed"))
; Success
; What a wonderful world
```

Note that "Success" is printed, and the if form evaluates to (returns) "What a wonderful world" in the previous example. 

#### when

The when operator is like a combination of if and do, but with no else branch.

```clojure
(when true
	(println 1)
	"abdra cadabra")
; "Success"
; "abra cadabra"
```

Note that only the last value is returned:

```clojure
(when true
	1
	2
	3
	4)
; 4
```

In when forms nil is always returned with the boolean form is falsy.

```clojure
(when false
	; anything
	)
; nil
```

#### nil, true, false, Truthiness, Equality, and Boolean Expressions

`true` and `false` are evaluated as usual.

`nil` is used to indicate *no value* in clojure.

Check if a value is nil using the nil? function:

```clojure
(nil? 1)
; false

(nil? nil)
; true
```

Both `nil` and `false`are used to represent logical falsiness.

All other values are truthy.

Clojures equality operator is `=`.

```clojure
= 1 1
; true
= nil nil
; true
= 1 2
; false
```

Clojure also uses the boolean opeartors `and` and `or`.

### Naming Values with def

Use  `def` to *bind* a name to a value.  (For now we can treat def as though we are defining constants)

```
(def myName
	"Jordan")
```

We use the term bind because in clojure it is not encouraged to assign multiple values to the same variable.  For example in Javascript (before template literals) it is common to write the following code:

```javascript
let severity = 'mild';
let error_message = "OH NO! It's a disaster! We're ";
if (severity === 'mild') {
    error_message = error_message + 'Mildly inconvenienced';
}
else {
    error_message = error_message + 'DOOOOOOMED';
}
```

We may be tempted to write:

```clojure
(def severity :mild)
(def error-message "OH NO! It's a disaster! We're ")
(if (= severity :mild)
	(def error-message (str error-message "Mildly inconvenienced"))
	(def error-message (str error-message "DOOOOOOMED")))
; "OH NO! It's a disaster! We're Mildly inconvenienced"
```

However, changing the value associated with a name can make it harder to understand your program.

The above code could be written as:

```clojure
(defn error-message
	[severity]
	(str "OH NO! It's a disaster! We're "
		(if (= severity :mild)
			"Mildly inconvenienced"
			"DOOOOOOMED")))
(error-message :mild)
; "OH NO! It's a disaster! We're Mildly inconvenienced"
```

### Data Structures

All of clojure's data structures are immutable, meaning you can't change them in place.

#### Numbers

Clojure has pretty sophisticated number support

```clojure
93  ; integer
1.2 ; float
1/5 ; ratio
```

#### Strings

Only double quotes are allowed for defining strings

Also there is no string interpolation.  It only allows concatenation via the str function.

#### Maps

`{}`

```clojure
{:first-name "Charlie" :last-name "Johnson"}
```

Besides using map literals as above, you can use the `hash-map` function to create a map:

```clojure
(hash-map :a 1 :b 2)
; {:a 1 :b 2}
```

Look up values with the `get` function:

```clojure
(get {:a 0 :b 1} :b)
; 1

(get {:a 0 :b 1} :c)
; nil

(get {:a 0 :b 1} :c "A default value") ; note we can provide a default value
; "A default value"
```

Look up nested values with `get-in`:

```clojure
(get-in {:a 0 :b {:c "ho hum"}} [:b :c])
; => "ho hum"
```

Another way to accomplish the same thing is to treat the map like a function with the key as its argument:

```
({:name "The human coffeepot"} :name)
; "The human coffeepot"
```

#### Keywords

Probably best understood by seeing how they are used

```clojure
:a
:rumplestiltsken
:34
:_?
```

Can be used to look up a value in a data structure:

```clojure
(:a {:a 1 :b 2 :c 3}) ; this is equivalent to 'get'
; 1
(:d {:a 1 :b 2 :c 3} "Default") ; A default can be provided
; "Default"
```

#### Vectors todo continue here: https://www.braveclojure.com/do-things/

#### Lists

#### Sets

### Functions

### let

### Loop

#### Regular Expressions

#### Reduce



##Chapter 4

##Chapter 5

##Chapter 6

##Chapter 7

##Chapter 8

