(ns contest-winner.core
  (:require
                [twitter.api.restful :as rest]
                [twitter.oauth :as twitter-oauth]
                [environ.core :refer [env]]
                [contest-winner.tweet-properties :as props])
  (:gen-class))

(def my-creds (twitter-oauth/make-oauth-creds (env :app-consumer-key)
                                              (env :app-consumer-secret)
                                              (env :user-access-token)
                                              (env :user-access-secret)))

(defn search-tweets
  [query]
  (rest/search-tweets :oauth-creds my-creds :params {:q query}))

(defn parse-tweets
  [search-query]
  (->> search-query
       (search-tweets)
       (props/tweets-from-response)
       (map props/parse-tweet)))

(defn contains?? 
  [regex string]
  (boolean (re-find regex string)))

(defn tweet-contains?
  [regex tweet]
  (contains?? regex (:tweet-text tweet)))

(defn get-tweet 
  []
  (first (parse-tweets "dat boi")))

(defn filter-tweets
  [regex tweets]
  (filter (partial tweet-contains? regex) tweets))

(defn saft
  "search-and-filter-tweets"
  [regex search-term]
  (filter-tweets regex (parse-tweets search-term)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (rest/statuses-update :oauth-creds my-creds :params {:status args}))

#_ {:user-id 771789532379222016, :tweet-text "helllooooooo", :tweet-hashtags [], :following_poster? false, :retweeted? false, :favorited? false}
