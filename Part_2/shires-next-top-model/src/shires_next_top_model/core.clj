(ns shires-next-top-model.core
  (:use clojure.pprint)
  (:gen-class))

;; Define the left and center parts of the hobbit body
(def asym-hobbit-body-parts 
  [{:name "head" :size 3}
   {:name "left-eye" :size 1}
   {:name "left-ear" :size 1}
   {:name "mouth" :size 1}
   {:name "nose" :size 1}
   {:name "neck" :size 2}
   {:name "left-shoulder" :size 3}
   {:name "left-upper-arm" :size 3}
   {:name "chest" :size 10}
   {:name "back" :size 10}
   {:name "left-forearm" :size 3}
   {:name "abdomen" :size 6}
   {:name "left-kidney" :size 1}
   {:name "left-hand" :size 2}
   {:name "left-knee" :size 2}
   {:name "left-thigh" :size 4}
   {:name "left-lower-leg" :size 3}
   {:name "left-achilles" :size 1}
   {:name "left-foot" :size 2}])

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      ; Create a new scope, and within it associate part with the first
      ; element of remaining-asym-parts. Associate remaining with the
      ; rest of the elements.
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
          ; Use set to create a set consisting of part and its
          ; matching part.  Then use the function into to add the 
          ; elements of that set to the vector final-body-parts.
          (into final-body-parts
            (set [part (matching-part part)])))))))

(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          [] ; initialize
          asym-body-parts))            

(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts)
      ; add up all of the sizes of body parts
      body-part-size-sum (reduce + (map :size sym-parts))
      ; get a random floating point number between 0 and the total size
      target (rand body-part-size-sum)]
    ; (println (str "target: " target))
    (loop [[part & remaining] sym-parts
        accumulated-size (:size part)]
      (do
        ; (pprint [part accumulated-size])    
        (if (> accumulated-size target)
          part
          (recur remaining (+ accumulated-size (:size (first remaining)))))))))

(defn -main
  "Given a predefined vector of hobbit body parts, creates the missing body parts so that the hobbit is perfectly symmetrical."
  [& args]
  (let [part-name (:name (hit asym-hobbit-body-parts))]
  (pprint (str "We hit the: " part-name))))