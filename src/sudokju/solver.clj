(ns sudokju.solver
  (:require [sudokju.puzzle :refer :all]
            [sudokju.util :refer :all]))

(defn next-moves
  "Returns the list of possible next moves."
  [ puzzle ]
  (for [i (range 0 (:base puzzle))
        j (range 0 (:base puzzle))
        n (candidates puzzle i j)]
      {:i i :j j :value n}
    ))

