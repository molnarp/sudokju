(ns sudokju.util)

(defn sqrt
  "Returns the whole number square root of a number."
  [ n ]
  (loop [ k 1 ]
    (if (= (* k k) n) k (recur (inc k)))))

(defn root-of
  "Returns the square root of the base number."
  [ matrix ]
  (sqrt (count matrix)))

