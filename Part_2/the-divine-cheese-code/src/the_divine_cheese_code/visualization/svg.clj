(ns the-divine-cheese-code.visualization.svg
  (:require [clojure.string :as str])
  (:refer-clojure :exclude [min max]))

(defn comparator-over-maps
  "Creates a function which compares the values
   for the keys provided by the ks param
   using the supplied comparison function"
  [comparison-fn ks]
  (fn [maps]
     (zipmap ks
             (map (fn [k] (apply comparison-fn (map k maps)))
                 ks))))

(def min (comparator-over-maps clojure.core/min [:lat :lng]))
(def max (comparator-over-maps clojure.core/max [:lat :lng]))

(defn latlng->point
  "Convert lat/lng map to comma-seperated string"
  [latlng]
  (str (:lat latlng) ", " (:lng latlng)))

(defn points
  "Creates lat/lng points"
  [locations]
  (clojure.string/join
    " "
    (map latlng->point locations)))


(defn translate-to-00
  "Finds the min of the locations and subtracts
   that value from each location"
  [locations]
  (let [mincoords (min locations)]
    (map #(merge-with - % mincoords) locations)))

(defn scale
  "Multiplies each point by the ratio between the
   maximum latitude and longitude and the desired
   height and width"
  [width height locations]
  (let [maxcoords (max locations)
        ratio {:lat (/ height (:lat maxcoords))
               :lng (/ width (:lng maxcoords))}]
    (map #(merge-with * % ratio) locations)))

(defn line
  "Creates an xml line of points"
  [points]
  (str "<polyline points=\"" points "\" />"))

(defn transform
  "Just chains other functions"
  [width height locations]
  (->> locations
       translate-to-00
       (scale width height)))

(defn xml
  "svg 'template', which also flips the coordinate system"
  [width height locations]
  (str "<svg height=\"" height "\" width=\"" width "\">"
       ;; These two <g> tags change the coordinate system so that
       ;; 0,0 is in the lower-left corner, instead of SVG's default
       ;; upper-left corner
       "<g transform=\"translate(0," height ")\">"
       "<g transform=\"rotate(-90)\">"
       (-> (transform width height locations)
           points
           line)
       "</g></g>"
       "</svg>"))

