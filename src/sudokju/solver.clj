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

(defn perform-moves
  "Perform moves and return puzzle after the moves have been made."
  [ puzzle-with-history moves ]
  (loop [pwh puzzle-with-history
         ms moves]
    (if (empty? ms)
      pwh
      (let [move (first ms)]
        (recur {:puzzle (fill-pos (:puzzle pwh)  (:i move) (:j move) (:value move))
                :history (cons move (:history pwh))}
               (rest ms))))))

(defn do-single-moves
  "Perform all single moves as much as possible"
  [puzzle-with-history]
  (loop [pwh puzzle-with-history]
    (let [moves (filter #(= (:weight %) 1) (next-moves (:puzzle pwh)))]
      (if (empty? moves)
        pwh
        (recur (perform-moves pwh moves))))))

(defn done?
  "Tests if a puzzle is done"
  [puzzle-with-history]
  (let [{:keys [puzzle _]} puzzle-with-history]
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
                (set (cell puzzle i j)))))))

(defn do-multi-moves
  "Performs multi-moves on the puzzle and returns list new branches."
  [puzzle-with-history moves]
  (loop [ms moves
         result []]
    (if (empty? ms)
      result
      (recur
        (rest ms)
        (cons (perform-moves puzzle-with-history (seq (first ms))) result)))))

(defn solve
  "Solves sudoku puzzles"
  [puzzle]
  (loop [pwhs '({:puzzle puzzle :history ()})
         solutions ()]
    (cond
      (empty? pwhs) solutions
      (done? (first pwhs)) (recur (rest pwhs) (cons (first pwhs) solutions))
      :else
      (let [pwh-non-single (do-single-moves (first pwhs))
            moves (next-moves (:puzzle pwh-non-single))]
        (recur
          (concat
            (do-multi-moves pwh-non-single moves)
            pwhs)
          solutions)))))
