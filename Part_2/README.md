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

#### Vectors

```clojure
[3 2 1]
(get [3 2 1] 0)
```

Vecors can contain any type.

```clojure
(vector "Jordan" "Chalupka")
; ["Jordan" "Chalupka"]
```

The `conj` function allows for elements to be added to the *end* of the vector:

```clojure
(conj [1 2 3] 4)
; [1 2 3 4]
(conj [1 2 3] [4]) ; recall that vectors can mix types
; [1 2 3 [4]]
```

#### Lists

Lists are similar to vectors in that they are linear collections of values. 

However: no retrieving elements with `get`

```clojure
'(1 2 3 4)
; (1 2 3 4) ; note that there is no single quote
```

If we want to get an element from a list use the nth function:

````
(nth '(:a :b :c) 0)
; :a
(nth '(:a :b :c) 2)
; :c
(nth '(:a :b :c) 3)
; IndexOutOfBoundsException
````

Note that `nth` is slower than `get` since clojure has to traverse all n elements of the list to get to the nth, whereas it only takes a few hops at most to accesss a vector element by its index.

```clojure
(list 1 "two" {3 4}) ; we can create lists with the list function
; (1 "two" {3 4})
```

Elements are added to the *beginning* of a list

```
(conj `(1 2 3) 4)
; (4 1 2 3)
```

When to use list vs vector?

A good rule of thumb is if you need to easly add items to the beginning of a sequence or if you are writing a macro use a list.  Otherwise use a vector.

#### Sets

Sets are collections of unique values.  There are two types: hash sets and sorted sets.

##### Hash sets

```clojure
#{"Jordan" 20 :icicle}
; #{20 :icicle "Jordan"}

(hash-set 1 1 2 2)
; #{1 2}

(conj #{:a :b} :b)
; #{:a :b}
```

Sets can be created from existing vectors and lists by using the `set` function.

```clojure
(set [3 3 3 4 4])
; #{3 4}
```

Check for set membership using `contains?`, `get` or by using a keyword as a function with the set as its argument.

This returns true or false whereas `get` and keyword lookup will return the value if it exists or nil if it doesn't.

Trying to find if a set contains `get` will always return nil, which is confusing.  This is one example as to why it is better to use `contains?` when testing for set membership.

```clojure
(get #{nil} nil) ; the set contains nil
; nil
(get #{:a} nil) ; the set does not contain nil
; nil

; versus
(contains? #{nil} nil) ; the set contains nil
; true
(contains? #{:a} nil) ; the set does not contain nil
; false
```

### Simplicity

Clojure encourages you to reach for built-in data structures first.  This is very different from most OO approaches.  However you will find that the data does not have to be tightly bundled with a class for it to be useful. 

```
It is better to have 100 functions operate on one data structure than 10 functions on 10 data structures.
—Alan Perlis
```

### Functions

inc

```clojure
(inc 1.1)
; 2.1
```

map

```clojure
(map inc [0 1 2 3])
; (1 2 3 4) ; note that map does not return a vector
```

Clojure evaluates all function calls recursively before passing them to the function.

```clojure
(+ (inc 199) (/ 100 (- 7 2)))
(+ 200 (/ 100 (- 7 2))) ; evaluated "(inc 199)"
(+ 200 (/ 100 5)) ; evaluated (- 7 2)
(+ 200 20) ; evaluated (/ 100 5)
220 ; final evaluation
```

#### Function Calls, Macro Calls, and Special Forms

Function calls are expressions that have a function expression as the operator.  The two other kinds of expressions are *macro calls* and *special forms*.

One example of a special form is the `if` statement.  In general special forms cannot be passed into functions as arguments.

Macros are similar in that they evaluate their operands differently from function calls, and they can't be passed as arguments to functions.

#### Defining Functions

defn

```
(defn name-of-the-function
	"The Docstring"
	[Parameters and Arity]
	function-body)
```

The docstring is a useful way to describe and document your code.

We can use the `doc` function to look up the documentation of a particular function.

```
(doc function-name) 
```

Parameters are the arguments you passed into your function, and the number of parameters is the Arity.  Functions also support arity overloading.  This means different functions will run depending on the arity.

Notice that each arity definition is enclosed in parentheses and has an argument list:

```clojure
(defn multi-arity
  ;; 3-arity arguments and body
  ([first-arg second-arg third-arg]
     (do-things first-arg second-arg third-arg))
  ;; 2-arity arguments and body
  ([first-arg second-arg]
     (do-things first-arg second-arg))
  ;; 1-arity arguments and body
  ([first-arg]
     (do-things first-arg)))
```

Clojure also supports a *rest* parameter, as in "put the rest of these arguments in a list with the following name."

```clojure
(defn incrementAll
	[&numbers]
	(map inc numbers))
(incrementAll 1 2 3)
; 2 3 4
```

Rest parameters can be mixed with normal parameters but the rest parameters must come last.

#### Destructuring

Destructuring lets you concisely bind names to values within a collection.

```clojure
;; Return the first element of a collection
(defn my-first
  [[first-thing]] ; Notice that first-thing is within a vector
  first-thing)

(my-first ["oven" "bike" "war-axe"])
; => "oven"
```

We can also destructure maps:

```clojure
(defn announce-treasure-location
➊   [{lat :lat lng :lng}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

(announce-treasure-location {:lat 100 :lng 50})
; Treasure lat: 100
; Treasure lng: 50
; nil

(announce-treasure-location {:lng 50 :lat 100})
; Treasure lat: 100 ; Notice that these are the same
; Treasure lng: 50
; nil

(defn announce-treasure-location ; Shorthand
  [{:keys [lat lng] :as treasure-location}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng))
  (steer-ship! treasure-location)) ; use :as to access the original map

```

The function body can contain forms of any kind.

#### Anonymous Functions

```clojure
(fn [param-list]
	function-body)

(map (fn [name] (str "Hi, " name))
     ["Jordan" "Dave"])
; ("Hi, Jordan" "Hi, Dave")
```

Anonymous functions can also be associated with a name.

```clojure
(def multiplier (fn [x] (* x 3)))
(multiplier 12)
; 36
```

Or anonymous functions can be created using this syntax:

```clojure
#(* % 3) ; this is the function!
(#(* % 3) 8)
; 24

(map #(str "Hi, " %)
	["Jordan" "Dave"])
; ("Hi, Jordan" "Hi, Dave")
```

This is made possible by *reader macros*.

```clojure
(#(str %1 " and " %2) "Jordan" "Dave")
; "Jordan and Dave"
```

We can also rest a parameter with &%.

#### Returning Functions

Returned functions are *closures*, meaning that they can access all the variables that were in scope when the function was created.

```clojure
(defn inc-maker
	"Create a custom incrementor"
	[inc-by]
	#(+ % inc-by)) ; inc-by is closured into this function
	
(def inc3 (inc-maker 3))
(inc3 7)
; 10
```

### let

Binds names to values.  Think of let as short for *let it be*.

```clojure
(let [x 3]
	x)
; 3

(def dalmation-list
    ["Pongo" "Perdita" "Puppy"])
(let [dalmations (take 2 dalmation-list)]
    dalmations)
; ("Pongo" "Perdita")
```

Let introduces a new scope:

```clojure
(def x 0) ; x is now 0 in the global context
(let [x 1] x) ; x is now 1 in the local context
; 1
x
; 0
```

We can also use rest parameters in let:

```clojure
(let [[pongo & dalmations] dalmation-list]
	[pongo dalmations])
; ["Pongo" ("Pongo" "Perdita" "Puppy")]
```

`let` forms have two main uses:

1. Provide clarity by allowing you to name things
2. Allow you to evaluate an expression only once and reuse the result.

### Into

Returns a new collection consisting of all of the first parameter's members conjoined with the second parameter's members.

```clojure
(into [] (set [:a :a]))
; [:a]

(into [] {:a 1 :b 2})
; [[:a 1] [:b 2]]
```

### Loop

Provides another way to do recursion in clojure.

The first argument to `loop` binds 0 to the variable iteration.

`recur` is called to enter the loop again.

The loop will continue until recur is no longer being called.

```clojure
(loop [iteration 0]
  (println (str "Iteration " iteration))
  (if (> iteration 3)
    (println "Goodbye!")
    (recur (inc iteration))))
; Iteration 0
; Iteration 1
; Iteration 2
; Iteration 3
; Iteration 4
; Goodbye!
```

#### Regular Expressions

Regular expressions are tools for performing pattern matching on text.

```clojure
#"regular-expression"
```

A good way to test regular expressions is with `re-find`.

```clojure
(re-find #"^left-" "left-eye")
; "left-"

(re-find #"^left-" "cleft-chin")
; nil

(re-find #"^left-" "wongleblart")
; nil
```

`re-find` searches the second argument for the regular expression provided in the first argument and returns the match if a match was found, or else nil.

#### Reduce

The pattern of process each element in a sequence and build a result is so common that there's a built in function for it called `reduce`.

```clojure
; sum with reduce
(reduce + [1 2 3 4])
; 10
(+ (+ (+ 1 2) 3) 4) ; equivalent

(reduce + 10 [1 2 3 4]) ; with initial value 10
; 20
```

### More

Lists all of the built-in functions that operate on data-structures covered in this chapter: http://clojure.org/api/cheatsheet

### Exercises

1. Use the `str`, `vector`, `list`, `hash-map`, and `hash-set` functions.

2. Write a function that takes a number and adds 100 to it.

3. Write a function, `dec-maker`, that works exactly like the function `inc-maker` except with subtraction:

   ```
   (def dec9 (dec-maker 9))
   (dec9 10)
   ; => 1

   ```

4. Write a function, `mapset`, that works like `map`

   except the return value is a set:

   ```
   (mapset inc [1 1 2 2])
   ; => #{2 3}

   ```

5. Create a function that’s similar to `symmetrize-body-parts` except that it has to work with weird space aliens with radial symmetry. Instead of two eyes, arms, legs, and so on, they have five.

6. Create a function that generalizes `symmetrize-body-parts` and the function you created in Exercise 5. The new function should take a collection of body parts and the number of matching body parts to add. If you’re completely new to Lisp languages and functional programming, it probably won’t be obvious how to do this. If you get stuck, just move on to the next chapter and revisit the problem later.

## Chapter 4: Core Functions in Depth

In this chapter we'll learn about Clojure's underlying concept of programming to abstractions.  As well we will learn about *lazy sequences*.

### Treating lists, vectors, sets, and maps as sequences

The term sequence refers to a collection of elements organized in a linear order (as apposed to an unordered collection or graph without a before and after relationship).

Clojure is designed to allow us to think in abstract terms as much as possible, and it does this by implementing functions in terms of data structure abstractions.

We will not use Javascript to implement a linked list:

```javascript
const node3 = {
    value: 'last',
    next: null
}

const node2 = {
    value: 'middle',
    next: node3
}

const node1 = {
    value: 'first',
    next: node2
}

// Now implement first, rest, and cons
const first = (node) => node.value;

const rest = (node) => node.next;

const cons = (newValue, node) => ({
   	value: newValue,
    next: node
});

// We can now implement map in terms of first, rest, and cons
const map = (list, transform) => {
    if(list === null) {
        return list;
    } else {
        // Transform the first element of the list and then calls itself again until it reaches the end.
        return cons(transform(first(list)), map(rest(list), transform));
    }
}
```

Since first, rest, and cons are written in terms of Javascript's array functions and we implemented map in terms of first, rest, and cons, we get map for free for any data structure where we can implement first, rest, and cons.

### Abstraction Through Inderection

We're still left with how to implement first in terms of different data structures.  Clojure does this using two forms of inderection.  Inderection is what makes abstraciton possible.  

Polymorphism is one way clojure provides inderection.  (Recall polymorphism is when different functions are used depending on the type of the arguments provided.)

The other way is through lightweight type conversion, producing a data structure that works with an abstraction's functions. For example when you call map, first, rest or cons it calls seq on the data structure in question to obtain a data strucutre that allows for the function being applied.

```clojure
(seq '(1 2 3))
; => (1 2 3)

(seq [1 2 3])
; => (1 2 3)

(seq #{1 2 3})
; => (1 2 3)

(seq {:name "Bill Compton" :occupation "Dead mopey guy"})
; => ([:name "Bill Compton"] [:occupation "Dead mopey guy"])
```

`seq` always returns a value that looks and behaves like a list (we call this value a sequence or seq). The seq of a map consists of two-element key-value vectors.  That's why `map` treats your maps like lists or vectors.   Note that we can convert back into a map using `(into {} (seq {:a 1 :b 2}))`.

This introduces many more useful functions such as reduce, filter, distinct, group-by, and many more.

### Seq Function Examples

More ways to use map:

```clojure
(map str ["a" "b" "c"] ["A" "B" "C"])
; ("aA" "bB" "cC")

;equivalently
(list (str "a" "A") (str "b" "B") (str "c" "C"))
; ("aA" "bB" "cC")

(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg])) ; collection of functions

(stats [3 4 10])
; (17 3 17/3)

(stats [80 1 44 13 6])
; (144 5 144/5)

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])

(map :real identities)
; ("Bruce Wayne" "Peter Parker" "Your mom" "Your dad")

```

More ways to use reduce:

```clojure
; transform a map's values, producing a new map with the same keys but with updated values:
(reduce (fn [new-map [key val]]
            (assoc new-map key (inc val)))
        {}
        {:max 30 :min 10})
; {:max 31 :min 11}

;; filter out keys from a map based on their value
(reduce (fn [new-map [key val]]
            (if (> val 4)
                (assoc new-map key val)
                new-map))
        {}
        {:human 4.1
         :critter 3.9})
; {:human 4.1}
```

The takeaway is that reduce is more flexible than it first appears.  Whenever you want to derive a new value from a sequable data structure, reduce will usually be able to do what you need.

### take, drop, take-while and drop-while

take and drop both take two arguments: a number and a sequence.  take returns the first n elements of the sequence, whereas drop returns the sequence with the first n elements removed.

Related functions take-while and remove-while each take a *predicate function* (function whose return value is evaluated for truth or falsity) to determine when it should stop taking or dropping. 

This is a useful alternative to filter when we do not want to process all of the data.

The some function tests whether the predicate function is truthy for atleast one of the elements in the sequence.

```clojure
(some #(> (:critter %) 3) food-journal)
; returns true or false

(some #(and (> (:critter %) 3) %) food-journal)
; returns the entry, instead of just true or false
```

### Sort and sort-by

sort works nicely for sorting elements in ascending order.  If you need somthing more complicated, you can use sort-by, which allows you to apply a function to the elements to determine the sort order.

```clojure
(sort-by count ["aaa" "c" "bb"])
```

### concat

Simply appends the members of one sequence to the end of another.

### Lazy seqs

A lazy seq is a seq whose members aren't computed until you try to access them.  Computing a seq's members is called realizing the seq.

```clojure
(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? true  :name "McFishwich"}
   1 {:makes-blood-puns? false, :has-pulse? true  :name "McMackson"}
   2 {:makes-blood-puns? true,  :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true,  :has-pulse? true  :name "Mickey Mouse"}})

(defn vampire-related-details
  [social-security-number]
  (Thread/sleep 1000)
  (get vampire-database social-security-number))

(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record))
       record))

(defn identify-vampire
  [social-security-numbers]
  (first (filter vampire?
                 (map vampire-related-details social-security-numbers))))
```

We can test out how long a function takes to run using time

```clojure
(time (some-function))
"Elapsed time: 0.064909 msecs"
```

### Infinite sequences

One easy way to create an infinite sequence is with repeat.  This function creates a sequence whose every member is the argument you pass:

```clojure
(concat (take 8 (repeat "na")) ["Batman!"])
; ("na" "na" "na" "na" "na" "na" "na" "na" "Batman!")

```

We can also use repeatedly which will call the provided function to generate each element in the sequence.

```clojure
(take 3 (repeatedly (fn [] (rand-int 10))))
; 1 4 0

(def rand-numbers (repeatedly (fn [] (rand-int 10))))
(take 3 rand-numbers)
; 3 2 9
(take 5 rand-number)
; 3 2 9 1 3

(defn even-numbers
    ([] (even-numbers 0))
    ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))

(take 3 (even-numbers))
; 0 2 4 6 8
```

### The collection abstraction

The collection abstraction is closely related to the sequence abstraction.  All of clojure's core data structures -vectors, maps, lists, and sets take part in both abstractions.

The sequence abstractino is about operating on members individually, whereas the collection abstraction is about the data structure as a while.  For example the collection functions count, empty?, and every? arn't about any individual element; they're about the whole.

### into vs conj

One of the most important collection functions is into.  As we know many seq functions return a seq rather than the original data structure.  To conver the return value back into the original value we use into:

``` clojure
(map identity {:sublight-reaction "Glitter"})
; ([:sunlight-reaction "Glitter!"])

(into {} (map identity {:sunlight-reaction "Glitter!"}))
; {:sunlight-reaction "Glitter!"}
```

The first argument of into does not have to be empty and can be used to add elements.

Conj also adds elements to a collection, but it does it in a slightly different way:

```clojure
(conj [0] [1])
; [0 [1]]

(into [0] [1])
; [0 1]

(conj [0] 1)
; [0 1]

; this also works
(conj [0] 1 2 3)
; [0 1 2 3]
```

### Function functions

#### apply

Apply explodes a seqable data structure so it can be passed to a function that expects a rest parameter.  For example max:

```clojure
(max 0 1 2)
; 2

(max [0 1 2])
; [0 1 2]

(apply max [0 1 2])
; 2

(defn my-into
	[target additions]
	(apply conj target additions))
(my-into [0] [1 2 3])
; [0 1 2 3]
```

### Partial

Partial takes a function and any number of arguments.  It then returns a new function.  When you call the retuned function, it calls the original function with the original arguements you supplied along with new arguments.

```clojure
(def add10 (partial + 10))
(add10 10)
; 13

(defn lousy-logger
  [log-level message]
  (condp = log-level
    :warn (clojure.string/lower-case message)
    :emergency (clojure.string/upper-case message)))

(def warn (partial lousy-logger :warn))

(warn "Red light ahead")
; => "red light ahead"
```

Calling warn is identical to calling lousy-logger.

### Complement

Before we wanted a function to find one element amid many elements.  Perhaps we want a function to find all elements with a desire property.

```clojure
(defn identify-humans
	[social-security-numbers]
	(filter #(not (vampire? #))
		(map vampire-related-details social-security-numbers)))
```

If we look at the first argument to filter, #(not (vampire? %)) we can see it's common to want the complement (the negation) of a boolean function that there's a function complement for that.

```clojure
(defn not-vampire? (complement vampire?))
(defn identify-humans
	social-security-numbers]
	(filter not-vampire?)
		(map vampire-related-details social-security-numbers))))
```

### A Vampire Data Analyst Program

The contents of this project can be found in the fwpd directory.

### Exercises

The vampire analysis program you now have is already decades ahead of anything else on the market. But how could you make it better? I suggest trying the following:

1. Turn the result of your glitter filter into a list of names.
2. Write a function, `append`, which will append a new suspect to your list of suspects.
3. Write a function, `validate`, which will check that `:name` and `:glitter-index`are present when you `append`. The `validate` function should accept two arguments: a map of keywords to validating functions, similar to `conversions`, and the record to be validated.
4. Write a function that will take your list of maps and convert it back to a CSV string. You’ll need to use the `clojure.string/join` function.

## Chapter 5 Functional Programming

### Pure functions

A pure function is one which has referential transparency and doesn't cause any side effects.  The qualities make it easier to reason about programs because the functions are completely isolated, unable to impact other parts of your system.

In comparison to pure functions are functions which use random numbers, and or use input/output.

#### Pure functions have no side effects

A program has to have some side effects (or else it wouldn't do anything!).  However, side effects can be potentially harmful because they introduce uncertanty about what the names in your code are referring to.  This leads to situations where it's very difficult to trace why and how a name came to be associated with a value, making it hard to debug a program.

To help promote ease of programming all of the core datastructures in clojure are immutable.

### Recursive instead of for/while

Since even a harmless for-loop involves mutation, we must resort to recursion.  The general approach to recurssive problem solving is:

```clojure
(defn sum
    ([vals] (sum vals 0)) ; takes an argument [vals]
   	([vals accumulating-total]
    	(if (empty? vals)
        accumulating-total
        (sum (rest vals) (+ (first vals) accumulating-total)))))
```

Note that you should generally use recur when doing recursion for performance reasons.  The reason being is that clojure doesn't provide tail call optimization. (*http://en.wikipedia.org/wiki/Tail_call*).  So we should really use the following code:

```clojure
(defn sum
    ([vals] (sum vals 0))
    ([vals accumulating-total]
     (if (empty? vals)
         accumulating-total
         (recur (rest vals) (+ (first vals) accumulating-total)))))
```

### Function composition instead of attribute mutation

Another way we commonly use mutation is to build up the final state of an object.  We can do this in clojure as:

```clojure
(require `[clojure.string :as s])
(defn clean
    [text]
    (s/replace (s/trim text) #"lol" "LOL"))

(clean "  My boa constrictor is so sassy lol!  ");
; "My boa constrictor is so sassy LOL!"
```

Combining functions so that the return value of one function is passed as an arguement to another is called function composition.

### Cool things to do with pure functions

#### comp

It's always safe to compose pure functions because you only need to worry about their input/output relationship.  Composing functions is so common in fact that there is a function for it:

```clojure
((comp inc *) 2 3)
; 7
```

And another example:

```clojure
(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})
(def c-int (comp :intelligence :attributes))
(def c-str (comp :strength :attributes))
(def c-dex (comp :dexterity :attributes))

(c-int character)
; 10
(c-str character)
; 4
(c-dex character)
; 5

; alternatively we could have written this as
(fn [c] (:strength (:attributes c)))
```

If one of the functions we want to compose needs to take more than one argument we can wrap it in an anonymous function:

```clojure
(defn spell-slots
    [char]
    (int (inc (/ (c-int char) 2))))
(spell-slots character)
; 6 

; and here's the same logic with comp
(def spell-slots-comp (comp int inc #(/ % 2) c-int))
```

#### memoize

This allows clojure to remember the result of a particular function call.  This is particularily useful for function that take a lot of time to run.

```clojure
(defn sleepy-identity
	"Returns the given value after 1 second"
	[x]
	(Thread/sleep 1000)
	x)
(sleepy-identity "Mr. Fantastico")
; returns "Mr. Fantastic" after 1 second
```

We can create a memoized version of sleepy-identity:

```clojure
(defn memo-sleepy-identity (memoize sleepy-identity))
(memo-sleepy-identity "Hello World")
; returns "Hello World" after 1 second
(memo-sleepy-identity "Hello World")
; returns "Hello World" immediantly
```

Then the second call will no longer incur the one second wait.  We can see this would be useful for slow connections requests, or for functions which are computationaly expensive.

## Chapter 6 Organizing Your Project: A Librarian's Tale

### Your Project as a Library

Think of clojure as storing objects (like data structures and functions) in a vast set of numbered shelves.  No human being could know offhand which shelf an object is stored in.  Instead we give clojure an identifier that it uses to retrieve the object.  It does this association using namespaces, which can be thought of as maps between human friendly symbols and references to shelf addresses.

`(ns-name *ns*)`

Will show us the current namespace.

### Storing Objects with def

```clojure
(def great-books ["East of Eden" "The Glass Bead Game"])
; #'user/great-books

great-books
; ["East of Eden" "The Glass Bead Game"]
```

This process of defining variables is called *interning* a var.  You can interact with a namespace's map of symbols to interned vars using `ns-interns`.  

```clojure
(ns-interns *ns*)
; {great-books #'user/great-books}

; This is just a normal map
(get (ns-interns *ns*) 'great-books)
; #'user/great-books

; The deref function can then be used to dereference this pointer
(deref #'user/great-books)
; ["East of Eden" "The Glass Bead Game"]

; or
(deref (get (ns-interns *ns*) 'great-books))
; ["East of Eden" "The Glass Bead Game"]

; or just use the symbol
great-books
; ["East of Eden" "The Glass Bead Game"]
```

If we define great-books again, this will result in a name collision and the variable will be overwritten.  Using namespaces allows us to avoid this, and the potential errors it will bring.

### Creating And Switching Namespaces

There are three tools for creating namespaces in clojure:

- create-ns (function)
- in-ns (function)
- ns (macro)

We will mostly use ns in our clojure files. 

#### create-ns

Takes a symbol, creates a namespace with that name if it doesn't exist already, and returns the namespace.

```clojure
(create-ns 'cheese.taxonomy)
; #<Namespace cheese.taxonomy>

; Usage
(ns-name (create-ns 'cheese.taxonomy))
; cheese.taxonomy
```

However create-ns is not very common to use because it is unlikely to create a namespace and then not immediantly use it.

#### in-ns

It is more common to use in-ns, which creates a namespace if it doesn't exist and switches to it.

```clojure
(in-ns 'cheese.analysis)
; #<Namespace cheese.analysis>
```

To use functions and data from other namespaces use the fully qualified symbol.  The geneeral form is `namespace/name`

```clojure
(in-ns 'cheese.taxonomy)
(def cheddars ["mild" "medium" "strong"])
(in-ns 'cheese.analysis)

cheddars
; Exception: Unable to resolve symbold :cheddars

cheese.taxonomy/cheddars
; ["mild" "medium" "strong"]
```

But is there some way to tell clojure which objects to give me without specifying the fully qualified name?  Of course, into refer and alias.

### refer

Calling refer with a namespace symbol lets you refer to the corresponding namespace's objects without having to use fully qualified symbols.  It does this by updating the namespace's sumbol/object map.  You can see the new entries like this:

```clojure
; some data
(in-ns 'cheese.taxonomy)
(def cheddars ["mild" "medium" "strong" "sharp" "extra sharp"])
(def bries ["Wisconsin" "Somerset" "Brie de Meaux" "Brie de Melun"])
(in-ns 'cheese.analysis)
(clojure.core/refer 'cheese.taxonomy)

(clojure.core/get (clojure.core/ns-map clojure.core/*ns*) 'bries)
; #'cheese.taxonomy/bries

(clojure.core/get (clojure.core/ns-map clojure.core/*ns*) 'cheddars)
; #'cheese.taxonomy/cheddars
```

It's as if clojure:

1. calls ns-inters on the cheese.taxonomy namespace
2. Merges that with the ns-map of the current namespace
3. Makes the result the new ns-map of the current namespace

When you call refer, you can also pass it the filters :only, :exclude, and :rename. 

```clojure
(clojure.core/refer 'cheese.taxonomy :only ['bries])
bries ; works
cheddars ;error

(clojure.core/refer 'cheese.taxonomy :rename {'bries 'yummy-bries})
yummy-bries ; works
```

Note that we don't need to write clojure.core becore things like clojure.core/ns-map.  This is because the REPL automaticlly refers clojure.core withing the user namespace.  

Use namespaces to group related functions and data together.  Clojure allows you to define private functions (only accessable to other functions within the same namespace) using defn-:

```clojure
(in-ns 'cheese.analysis)
(defn- private-function
    "A private function"
    ; does private things
    )
```

### alias

Alias shortens a namespace name for using fully qualified symbols:

```clojure
(alias 'taxonomy 'cheese.taxonomy)
taxonomy/bries ; bries
```

### Real Project Organization

#### Relationship Between File Paths and Namespace Names

In clojure there is a one to one relationship between a namespace name and the path of the file where the namespace is declared.

#### Requiring and Using Namespaces

Think of require as telling clojure the following:

1. Do nothing if you've already called require with this symbol
2. Otherwise, find the file that corresponds to this symbol (using the rules defined above).
3. Read and evaluate the contents of that file.

Instead of using alias, we can use `:as`:

```clojure
(require '[the-divine-cheese-code.visualization.svg :as svg])
```

Or to automatically require and then refer a namespace use `use`:

```clojure
(use '[the-divine-cheese-code.visualization.svg :as svg])

; we can also use :only, :exclude, etc
(use '[the-divine-cheese-code.visualization.svg :as svg :only [points]])
```

### The ns Macro

The ns macro allows you to use the tools described so far succinctly and provides other useful functionality.  One useful task ns does is refer the clojure.core namespace by default.  You can control what is refered using `:refer-clojure :exclude`or :only etc.

You can also require:

```clojure
(ns the-divine-cheese-code.core
  (:require [the-divine-cheese-code.visualization.svg :as svg]
            [clojure.java.browse :as browse]))
```



## Chapter 7

## Chapter 8

