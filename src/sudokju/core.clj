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
  (+ (count problem) 1))

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

(defn cell
  "Returns the k x k cell which the (i,j) position belongs to"
  [ problem i j ]
  (let [ k (base-k problem)
         n (base-n problem) ]
    (let [ i-start (* k (quot i k))
           i-end (* (+ k 1) (quot i k))
           j-start (* k (quot j k))
           j-end (* (+ k 1) (quot j k))]

    )



  (loop [ p ])
  )

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

;(defn candidates
;  "Finds candidates for the specified space, i-th row, j-th column"
;  [problem i j]
;  (set/intersection
;    (candidates-row problem i)
;    (candidates-column problem j)
;    (candidates-square problem i j))
;  )

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

(defn verify-column
  "Checks the specified column of the passed problem if it's valid."
  [ problem colNum ]
  (has-duplicates ))

  ;(defn verify-segment
  ;  "Checks if the specified segment is valid"
  ;  [ problem segmentNum ]
  ;  (let i range (quot (count problem segmentNum) (+ (count problem segmentNum) 2))))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (print-problem (read-problem "resources/problem1.json")))