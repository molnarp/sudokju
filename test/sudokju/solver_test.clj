(ns sudokju.solver-test
  (:require [clojure.test :refer :all]
            [clojure.pprint :refer :all]
            [sudokju.solver :refer :all]))

(def puzzle {:base 4 :root 2 :matrix [[3 0 4 0] [0 1 0 3] [2 3 0 0] [1 0 0 2]] })
(def done-puzzle {:base 4 :root 2 :matrix [[3 2 4 1] [4 1 2 3] [2 3 1 4] [1 4 3 2]]})
(def incorrect-puzzle-row {:base 4 :root 2 :matrix [[3 2 4 4] [4 1 2 3] [2 3 1 4] [1 4 3 2]] })
(def incorrect-puzzle-col {:base 4 :root 2 :matrix [[3 2 4 1] [3 1 2 3] [2 3 1 4] [1 4 3 2]] })
(def incorrect-puzzle-cell {:base 4 :root 2 :matrix [[3 2 4 1] [2 3 1 4] [4 1 2 3]  [1 4 3 2]] })

(deftest test-next-moves
  (testing "list next moves"
    ;(pprint (next-moves puzzle))
    (is (= [{:i 0 :j 1 :value 2 :weight 1}
            {:i 0 :j 3 :value 1 :weight 1}
            {:i 1 :j 0 :value 4 :weight 1}
            {:i 1 :j 2 :value 2 :weight 1}
            {:i 2 :j 2 :value 1 :weight 1}
            {:i 2 :j 3 :value 1 :weight 2}
            {:i 2 :j 3 :value 4 :weight 2}
            {:i 3 :j 1 :value 4 :weight 1}
            {:i 3 :j 2 :value 3 :weight 1}]
           (next-moves puzzle)))))

(deftest test-perform-moves
  (testing "perform moves"
    (is (= {:puzzle {:base 4 :root 2 :matrix [[3 2 4 1] [4 1 2 3] [2 3 1 0] [1 4 3 2]] }
            :history (reverse '({:i 0 :j 1 :value 2 :weight 1}
                                 {:i 0 :j 3 :value 1 :weight 1}
                                 {:i 1 :j 0 :value 4 :weight 1}
                                 {:i 1 :j 2 :value 2 :weight 1}
                                 {:i 2 :j 2 :value 1 :weight 1}
                                 {:i 3 :j 1 :value 4 :weight 1}
                                 {:i 3 :j 2 :value 3 :weight 1}))}
           (perform-moves {:puzzle puzzle :history ()}
                          '({:i 0 :j 1 :value 2 :weight 1}
                             {:i 0 :j 3 :value 1 :weight 1}
                             {:i 1 :j 0 :value 4 :weight 1}
                             {:i 1 :j 2 :value 2 :weight 1}
                             {:i 2 :j 2 :value 1 :weight 1}
                             {:i 3 :j 1 :value 4 :weight 1}
                             {:i 3 :j 2 :value 3 :weight 1}))))))

(deftest test-do-single-moves
  (testing "do all single moves in puzzle"
    (is (= {:puzzle {:base 4 :root 2 :matrix [[3 2 4 1] [4 1 2 3] [2 3 1 4] [1 4 3 2]] }
            :history (reverse '({:i 0 :j 1 :value 2 :weight 1}
                                 {:i 0 :j 3 :value 1 :weight 1}
                                 {:i 1 :j 0 :value 4 :weight 1}
                                 {:i 1 :j 2 :value 2 :weight 1}
                                 {:i 2 :j 2 :value 1 :weight 1}
                                 {:i 3 :j 1 :value 4 :weight 1}
                                 {:i 3 :j 2 :value 3 :weight 1}
                                 {:i 2 :j 3 :value 4 :weight 1}))}
           (do-single-moves {:puzzle puzzle :history ()})))))


(deftest test-done
  (testing "test not done"
    (is (not (done? {:puzzle puzzle :history ()}))))
  (testing "done"
    (is (done? {:puzzle done-puzzle :history ()}))))

(deftest test-correct
  (testing "test correct"
    (is (correct? {:puzzle done-puzzle :history ()})))
  (testing "not correct because unfinished"
    (is (not (correct? {:puzzle puzzle :history ()}))))
  (testing "incorrect row"
    (is (not (correct? {:puzzle incorrect-puzzle-row :history ()}))))
  (testing "incorrect col"
    (is (not (correct? {:puzzle incorrect-puzzle-col :history ()}))))
  (testing "incorrect cell"
    (is (not (correct? {:puzzle incorrect-puzzle-cell :history ()})))))
