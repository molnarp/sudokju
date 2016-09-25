(ns sudokju.puzzle-test
  (:require [clojure.test :refer :all]
            [sudokju.puzzle :refer :all]))

(def puzzle {:base 4 :root 2 :matrix [[3 0 4 0] [0 1 0 3] [2 3 0 0] [1 0 0 2]] })

(deftest load-2by2
  (testing "load 2-by-2 sudoku"
    (is (= {:base 4 :root 2 :matrix [[3 0 4 0] [0 1 0 3] [2 3 0 0] [1 0 0 2]]}
           (load-puzzle "resources/problem2.json"))))

  (testing "print 2-by-2 sudoku"
    (print-puzzle (load-puzzle "resources/problem2.json"))))

(deftest test-pos
  (testing "position at (0 2)"
    (is (= 4 (pos puzzle 0 2))))
  (testing "position at 3 3"
    (is (= 2 (pos puzzle 3 3)))))

(deftest second-row-of-puzzle
  (testing "read row of puzzle"
    (is (=
          [0 1 0 3]
          (row puzzle 1)))))

(deftest second-column-of-puzzle
  (testing "read 2nd row of puzzle"
    (is (=
          [0 1 3 0]
          (col puzzle 1)))))

(deftest cell-at-2-2
  (testing "Cell at location (0 0)"
    (is (=
          [3 0 0 1]
          (cell puzzle 0 0))))
  (testing "Cell at location (2 2)"
    (is (=
          [0 0 0 2]
          (cell puzzle 2 2)))))

(deftest test-candidates
  (testing "no candidates on filled position"
    (is (= #{} (candidates puzzle 0 0))))
  (testing "candidates on unfilled position"
    (is (= #{2} (candidates puzzle 0 1)))))

(deftest test-fill-pos
  (testing "fill in pos 0 1"
    (is (= {:base 4 :root 2 :matrix [[3 2 4 0] [0 1 0 3] [2 3 0 0] [1 0 0 2]] }
           (fill-pos puzzle 0 1 2)))))
