(ns sudokju.core
    (:require [clojure.data.json :as json])
    (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn read-problem
      "Reads a problem from json"
      [&]
      (json/read-str
        (slurp "problem1.json")))

(defn print-problem
      "Prints a problem"
      [p]
      (doseq i (range 9)
             ))