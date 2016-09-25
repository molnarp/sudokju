(ns sudokju.puzzle
  (:require [sudokju.util :refer :all]
            [clojure.data.json :as json]
            [clojure.set :refer :all]))

(defn load-puzzle
  "Reads a problem from json"
  [file]
  (let [matrix (json/read-str (slurp file))
        k (root-of matrix)]
    {:base (* k k) :root k :matrix matrix})
)

(defn print-puzzle
  "Prints a problem"
  [ puzzle ]
  (println "root: " (:root puzzle))
  (doseq [ row (:matrix puzzle) ]
    (doseq [ val row ]
      (print val " "))
    (println)))

(defn pos
  "Returns the number at position (i j)"
  [ puzzle i j ]
  (nth (nth (:matrix puzzle) i) j))

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
  (let [i-start (- i (mod i (:root puzzle)))
        i-end (* (inc (quot i (:root puzzle))) (:root puzzle))
        j-start (- j (mod j (:root puzzle)))
        j-end (* (inc (quot j (:root puzzle))) (:root puzzle))]
    (mapcat
      (partial seq-segment j-start j-end)
      (seq-segment i-start i-end (:matrix puzzle)))))

(defn candidates
  "Returns candidates for given position (i j"
  [ puzzle i j ]
  (if (not (= 0 (pos puzzle i j)))
    #{}
    (difference
      (set (range 1 (inc (:base puzzle)) ))
      (set (row puzzle i))
      (set (col puzzle j))
      (set (cell puzzle i j)))))

(defn fill-pos
  "Fills in position i j in puzzle with number n"
  [ puzzle i j n ]
  (assert (= 0 (pos puzzle i j)))
  (let [updated-row (assoc (row puzzle i) j n)
        updated-matrix (assoc (:matrix puzzle) i updated-row)]
       (assoc puzzle :matrix updated-matrix)))
