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

(defn get-tweet 
  "test tweet for when u need a tweet mane"
  []
  (first (parse-tweets "dat boi")))

(defn saft
  "search-and-filter-tweets"
  [regex search-term]
  (props/filter-tweets regex (parse-tweets search-term)))

(defn retweet
  [tweet]
  (rest/statuses-retweet-id :oauth-creds my-creds :params {:id tweet}))

(defn retweet-user
  [name]
  (rest/statuses-user-timeline :oauth-creds my-creds :params {:screen_name name}))

(defn get-users-tweets
  [tweets]
  (get-in tweets [:body]))

(defn tweet-ids
  []
  (map props/tweet-id (get-users-tweets (retweet-user "chasey_rogers"))))

(defn retweet-every-tweet
  []
  (map retweet (tweet-ids)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (rest/statuses-update :oauth-creds my-creds :params {:status args}))

;; search tweets by regex vector
;; get back hella tweets
;;==see what those tweets need us to do. 
;; -retweet
;; -favorite
;; -both?
;; -follow the tweeter 
;; -follow a mentioned account
;;============================
;; do every thing required
;; confirm everything is done
;; check dm's/ mentions
