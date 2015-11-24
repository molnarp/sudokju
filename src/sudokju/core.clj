(ns sudokju.core
    (:require [clojure.data.json :as json])
    (:gen-class))

(defn print-problem
  "Prints a problem"
  [p]
  (doseq [ row p]
    (doseq [ cell row ]
      (print cell " "))
    (println)))

(defn read-problem
      "Reads a problem from json"
      [file]
      (json/read-str
        (slurp file)))

(defn has-duplicates
  "Checks if the passed list contains duplicates except for zeros"
  [ row ]
  (>
    (count
      (for [[id freq] (frequencies row)
        :when (and (not (= id 0)) (> freq 1))]
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
  (has-duplicates (map nth problem (repeat 9 colNum))))

;(defn verify-segment
;  "Checks if the specified segment is valid"
;  [ problem segmentNum ]
;  (has-duplicates ))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (print-problem (read-problem "resources/problem1.json")))
