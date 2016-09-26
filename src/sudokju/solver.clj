(ns sudokju.solver
  (:require [sudokju.puzzle :refer :all]
            [sudokju.util :refer :all]))

(defn next-moves
  "Returns the list of possible next moves."
  [ puzzle ]
  (for [i (range 0 (:base puzzle))
        j (range 0 (:base puzzle))
        n (candidates puzzle i j)]
      {:i i :j j :value n :weight (count (candidates puzzle i j))}))

(defn move
  "Perform move on puzzle"
  [puzzle-with-history move]
   {:puzzle (fill-pos (:puzzle puzzle-with-history)  (:i move) (:j move) (:value move))
    :history (cons move (:history puzzle-with-history))})

(defn done?
  "Tests if a puzzle is done"
  [puzzle-with-history]
  (let [{:keys [puzzle history]} puzzle-with-history]
    (every?
      (partial not-any? (partial = 0))
      (:matrix puzzle))))

(defn correct?
  "Tests if a puzzle is correct"
  [ puzzle-with-history ]
  (let [puzzle (:puzzle puzzle-with-history)
        base (:base puzzle)
        root (:root puzzle)
        complete? (partial = (set (range 1 (inc base))))]
    (and
      (every? complete? (map set (:matrix puzzle)))
      (every? complete?
              (for [ j (range 0 base)]
                (set (col puzzle j))))
      (every? complete?
              (for [i (range 0 base root)
                    j (range 0 base root)]
                (set (cell puzzle i j))))
      )
    )
  )
