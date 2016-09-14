(ns contest-winner.tweet-properties
  (:refer-clojure :exclude [contains?]))

(defmacro def-tweet-getter
  "Defines a function that gets the final value from the tweet."
  [fn-name & rest]
  `(defn ~fn-name [tweet#]
     (get-in tweet# ~(vec rest))))

(def-tweet-getter tweet-id :id)
(def-tweet-getter user-id :user :id)
(def-tweet-getter tweet-text :text)
(def-tweet-getter tweet-hashtags :entities :hashtags)
(def-tweet-getter following-poster? :user :following)
(def-tweet-getter retweeted? :retweeted)
(def-tweet-getter favorited? :favorited)

(def tweet-parsers
  "List of functions to get values from tweet."
  [tweet-id user-id tweet-text tweet-hashtags following-poster? retweeted? favorited?])

(def tweet-keys
  "Keywords for keys of tweet hashmap."
  [:tweet-id :user-id :tweet-text :tweet-hashtags :following-poster? :retweeted? :favorited?])

(defn parse-tweet
  [tweet]
  (zipmap tweet-keys ((apply juxt tweet-parsers) tweet)))

(defn tweets-from-response
  [search-response]
  (get-in search-response [:body :statuses]))

(defn contains?
  [regex string]
  (boolean (re-find regex string)))

;; regex matchers
(def contest-matchers #"(?i)rt|retweet.+(?i)win|enter")

(def contest-exclusions #"facebook|tumblr|instagram|youtube")

(def follow-matchers #"follow|FOLLOW|Follow")

(def favorite-matchers #"Favorite|FAVORITE|favorite")

(defn tweet-contains?
  [regex tweet]
  (contains? regex (:tweet-text tweet)))

(defn tweet-doesnt-have
  [regex tweet]
  (complement (contains? regex (:tweet-text tweet))))

(defn tweets-without 
  [regex tweets]
  (filter (partial tweet-doesnt-have regex) tweets))

(defn filter-tweets
  [regex tweets]
  (filter (partial tweet-contains? regex) tweets))
