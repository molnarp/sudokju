(ns sudokju.util)

(defn sqrt
  "Returns the whole number square root of a number by iterative process."
  [ n ]
  (loop [ k 1 ]
    (if (= (* k k) n) k (recur (inc k)))))

(defn root-of
  "Returns the root number of a puzzle matrix."
  [ matrix ]
  (sqrt (count matrix)))
