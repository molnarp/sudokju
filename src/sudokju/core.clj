(ns sudokju.core
  (:require [clojure.data.json :as json]
            [clojure.set :as set])
    (:gen-class))

(defn read-problem
      "Reads a problem from json"
      [file]
      (json/read-str
        (slurp file)))

(defn print-problem
  "Prints a problem"
  [ p ]
  (doseq [ row p ]
    (doseq [ val row ]
      (print val " "))
    (println)))

(defn base-n
  "Returns the base number of the problem ie number of rows and columns"
  [ problem ]
  (count problem))

(defn sqrt
  "Returns the whole number square root of a number."
  [ n ]
  (loop [ k 1 ]
    (if (= (* k k) n)
      k
      (recur (inc k))))
  )

(defn base-k
  "Returns the square root of the base number."
  [ problem ]
  (sqrt (base-n problem)))

(defn row
  "Returns the i-th row as a list"
  [ problem i ]
  (nth problem i))

(defn col
  "Returns the j-th column as a list"
  [ problem j ]
  (map nth problem (repeat (base-n problem) j)))

(defn segment
  "Returns the segment specified by the two arguments"
  [ start end xs ]
  (drop start (take end xs)))

(defn cell
  "Returns the k x k cell which the (i,j) position belongs to"
  [problem i j]

  (def k (base-k problem))
  (def i-start (- i (mod i k)))
  (def i-end (* (inc (quot i k)) k))
  (def j-start (- j (mod j k)))
  (def j-end (* (inc (quot j k)) k))

  (mapcat (partial segment j-start j-end) (segment i-start i-end problem)))

(defn candidates-base
  "Returns the basic set of candidates."
  [problem]
  (set (range 1 (+ (count problem) 1))))

(defn candidates-row
  "Finds candidates for the specified row, i-th row"
  [problem i]
  (set/difference
    (candidates-base problem)
    (set (row problem i)))
  )

(defn candidates-column
  "Finds candidates in the specified column"
  [ problem j ]
  (set/difference
    (candidates-base problem)
    (set (col problem j)))
  )

(defn candidates-cell
  "Finds candidates in the specified cell"
  [problem i j ]
  (set/difference
    (candidates-base problem)
    (set (cell problem i j))))

(defn candidates
  "Finds candidates for the specified space, i-th row, j-th column"
  [problem i j]
  (set/intersection
    (candidates-row problem i)
    (candidates-column problem j)
    (candidates-cell problem i j))
  )

(defn has-duplicates
  "Checks if the passed list contains duplicates except for zeros"
  [ row ]
  (>
    (count
      (for [
            [id freq]
            (frequencies row)
            :when (and (not (= id 0)) (> freq 1))
            ]
        id))
    0)
  )

(defn verify-row
  "Checks the specified row of the passed problem, if it's valid."
  [ problem rowNum ]
  (has-duplicates (nth problem rowNum)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (print-problem (read-problem "resources/problem1.json")))