(ns fwpd.core
  (:use clojure.pprint))
(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
        (clojure.string/split string #"\n")))
        
(defn mapify
  "Returns a seq of maps like {:name \"Edward Collen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
        (reduce (fn [row-map [vamp-key value]]
                 (assoc row-map vamp-key (convert vamp-key value)))
                {}
                (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

; Exercises

; 1. Turn the result of your giltter function into a list of names
(defn get-names
  [rows]
  (map :name rows))
; (get-names (glitter-filter 3 (mapify (parse (slurp filename)))))

; 2. Write a function append, which will append a new suspect to your list of suspects
(defn append
  [suspect-list new-suspect]
  (conj suspect-list new-suspect))

; (pprint
;   (append
;    (mapify (parse (slurp filename)))
;    {:name "Jordan"}))

; 3. Write a function, validate, which will check that :name and :glitter-index are present when you append. The validate function should accept two arguments: a map of keywords to validating functions, similitar to conversions, and the record to be validated.
(defn validate [keys m]
 (every? (partial contains? m) keys))

; (validate
;   [:name :glitter-index] {:name "Jordan" :glitter-index 5})

; We can now rewrite append to use validate
; (defn append
;   [suspect-list new-suspect]
;   (if (validate [:name :glitter-index] new-suspect)
;     (conj suspect-list new-suspect)
;     (seq suspect-list)))
  

; Write a function that will take your list of maps and convert it back to a CSV string.  Use clojure.string/join

; convert from map to csv
(defn convertBack
  [m]
  (clojure.string/join "," (toVector m)))
      
(defn toVector
  [m]
  (conj []
        (:name m) 
        (:glitter-index m)))

; a test
; (convertBack
;   {:name "Jordan" :glitter-index 5})

; this could be written with let
(def )
  
(println
  (let [output
        (mapify (parse (slurp filename)))]
       (reduce str 
        (map #(str % "\n")
         (map convertBack output)))))
  
