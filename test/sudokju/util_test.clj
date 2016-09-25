(ns sudokju.util-test
  (:require [ clojure.test :refer :all]
            [ sudokju.util :refer :all]))

(deftest sqrt-4
  (is (= 2 (sqrt 4))))

(deftest root-of-9
  (is (= 3 (root-of [ 0 0 0 0 0 0 0 0 0 ]))))
