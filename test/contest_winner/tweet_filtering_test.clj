(ns contest-winner.tweet-filtering-test
  (:require [clojure.test :refer :all]
            [contest-winner.core :refer :all]
            [contest-winner.test-data.tweet-filtering-test-test-data :refer :all]
            [contest-winner.tweet-properties :refer :all]))

(deftest regex-matching
  (testing "contains?? returns true if regex matches"
    (is (true? (contains? #"hello" "Hi there hello"))))

  (testing "tweet-contains? return false if regex doesn't match"
    (is (false? (tweet-contains? #"trump" tweet)))))

(deftest tweet-filtering
  (testing "filter-tweets returns a sequence of tweets"
    (is (= '(773633830401957889 773634411392827392) (map :tweet-id (filter-tweets #"you" tweets-for-filtering))))))
