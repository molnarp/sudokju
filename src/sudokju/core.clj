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

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (print-problem (read-problem "resources/problem1.json")))
