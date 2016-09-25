(ns sudokju.puzzle
  (:require [sudokju.util :refer :all]
            [clojure.data.json :as json]))

(defn load-puzzle
  "Reads a problem from json"
  [file]
  (def matrix (json/read-str (slurp file)))
  (def k (root-of matrix))
  {:base (* k k) :root k :matrix matrix})

(defn print-puzzle
  "Prints a problem"
  [ puzzle ]
  (println "root: " (:root puzzle))
  (doseq [ row (:matrix puzzle) ]
    (doseq [ val row ]
      (print val " "))
    (println)))

(defn row
  "Returns the i-th row as a list"
  [ puzzle i ]
  (nth (:matrix puzzle) i))

(defn col
  "Returns the j-th column as a list"
  [ puzzle j ]
  (map
    nth
    (:matrix puzzle)
    (repeat (:base puzzle) j)))

(defn seq-segment
  "Returns a segment of the sequence"
  [ from to xs ]
  (drop from (take to xs)))

(defn cell
  "Returns the cell of position (i j)"
  [ puzzle i j ]
  (def i-start (- i (mod i (:root puzzle))))
  (def i-end (* (inc (quot i (:root puzzle))) (:root puzzle)))
  (def j-start (- j (mod j (:root puzzle))))
  (def j-end (* (inc (quot j (:root puzzle))) (:root puzzle)))

  (mapcat
    (partial seq-segment j-start j-end)
    (seq-segment i-start i-end (:matrix puzzle))))
