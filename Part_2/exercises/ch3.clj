;; Answers to the chapter 3 exercises in Clojure for the Brave and True.

;; use the str, vector, hash-map, and hash-set functions.

(str "hello" "World")
; helloWorld

(vector :a :b :c)
; [:a :b :c]

(list :a :b :c)
; (:a :b :c)

(hash-map :a "a" :b "b")
; {:b "b", :a "a"}

(hash-set :a "a" :b "b")
; #{"a" "b" :b :a}

;; write a function that takes a number and adds 100 to it
(defn addOneHundred
  "Takes a number and adds 100 to it."
  [x]
  (+ 100 x))

(addOneHundred 1)
; 101

;; Write a function, dec-maker, that works exactly like the function inc-maker except with subtraction.
(defn dec-maker
  "Returns a function which decriments a number by dec-by"
  [dec-by]
  #(- % dec-by))

(def dec2 (dec-maker 2))
(dec2 2)
; 0

;; write a function, mapset, that works like map except the return value is a set
(defn mapset
  "Applies a function to all elements of a vector and returns a set"
  [fn v]
  (into #{} (map fn v)))

(mapset #(+ % 2) [1 2 3 3])
#{4 3 5}

;; function similar to symmetrize-body-parts except that it has to work with weird space aliencs with radial symmetry.  Instead of two eyes, arms, legs, and so on, they have five.
(defn matching-parts
  [part multiplier]
  (if (re-find #"^left-" (:name part))
    (multiplier
      {:name (clojure.string/replace (:name part) #"left-" "")
       :size (:size part)})
    part))

(defn to-seq
  "converts a collection to a sequence"
  [thing]
  (if (seq? thing)
    thing
    [thing]))

(defn multiply-by
  ""
  [multiple]
  #(repeat multiple %))

(defn weird-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size and returns the body parts of a weird alien"
  [asym-body-parts]
  (reduce (fn 
           [parts part]
           (reduce (fn [parts part] (cons part parts)) parts (to-seq (matching-parts part (multiply-by 1)))))
    [] asym-body-parts))
