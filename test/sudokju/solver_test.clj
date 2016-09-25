(ns sudokju.solver-test
  (:require [clojure.test :refer :all]
            [clojure.pprint :refer :all]
            [sudokju.solver :refer :all]))

(def puzzle {:base 4 :root 2 :matrix [[3 0 4 0] [0 1 0 3] [2 3 0 0] [1 0 0 2]] })

(deftest test-next-moves
  (testing "list next moves"
    ;(pprint (next-moves puzzle))
    (is (= [{:i 0 :j 1 :value 2}
            {:i 0 :j 3 :value 1}
            {:i 1 :j 0 :value 4}
            {:i 1 :j 2 :value 2}
            {:i 2 :j 2 :value 1}
            {:i 2 :j 3 :value 1}
            {:i 2 :j 3 :value 4}
            {:i 3 :j 1 :value 4}
            {:i 3 :j 2 :value 3}
            ]
           (next-moves puzzle)))))
