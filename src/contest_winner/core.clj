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

; structure for retweet/follow-user/favorite-tweet functions
(defn create-route
  "Takes a rest route as a string and returns 
   a symbol referring to that function."
  [rest-route]
  (symbol (str "rest/" rest-route)))

(defn tweet-actions
  "Takes a api function and hashmap of params."
  [rest-route property]
  ((create-route rest-route) :oauth-creds my-creds :params property))

(defn retweet
  [tweet]
  (tweet-actions "statuses-retweet-id" {:id (:tweet-id tweet)}))

(defn follow-user
  [tweet]
  (tweet-actions "friendships-create" {:user-id (:user-id tweet)}))

(defn favorite-tweet
  [tweet]
  (tweet-actions "favorites-create" {:id (:tweet-id tweet)}))

(defn retweet-every-tweet
  [tweets]
  (map retweet tweets))

; string->hashmap
(defn gather-tweets
  "Takes a search query, and returns a hashmap of tweets organized by which actions need to be taken on them."
  [search-query]
  (let [all-tweets {}
        tweets (props/tweets-without props/contest-exclusions (saft props/contest-matchers search-query))]
    (assoc all-tweets :to-retweet tweets
                      :to-follow (props/filter-tweets props/follow-matchers tweets)
                      :to-favorite (props/filter-tweets props/favorite-matchers tweets))))

(defn perform-actions
  [organized-tweets]
  (do
    (dorun (map follow-user (:to-follow organized-tweets)))
    (dorun (map favorite-tweet (:to-favorite organized-tweets)))
    (dorun (map retweet (:to-retweet organized-tweets)))))

(defn win
  [search-query]
  (perform-actions (gather-tweets search-query)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (win args))

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
