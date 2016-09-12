(ns contest-winner.tweet-properties-test
  (:require [clojure.test :refer :all]
            [contest-winner.test-data.tweet-properties-test-test-data :refer :all]
            [contest-winner.tweet-properties :refer :all]))

(deftest tweet-parsing
  (testing "returns user_id"
    (is (= 771789532379222016 (user-id tweet))))

  (testing "return text content of a tweet"
    (is (= "cats" (tweet-text tweet))))

  (testing "should return hashtags on the tweet"
    (is (= [] (tweet-hashtags tweet))))

  (testing "returns following status"
    (is (false? (following-poster? tweet))))

  (testing "returns true if bot retweeted the tweet"
    (is (false? (retweeted? tweet))))

  (testing "returns true if bot favorited the tweet"
    (is (false? (favorited? tweet)))))
