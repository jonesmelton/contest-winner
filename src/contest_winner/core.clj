(ns contest-winner.core
  (:require
                [twitter.api.restful :as rest]
                [twitter.oauth :as twitter-oauth]
                [twitter.callbacks :as callbacks]
                [twitter.callbacks.handlers :as handlers]
                [twitter.api.streaming :as stream]
                [environ.core :refer [env]]
                [clojure.data.json :as json]
                [contest-winner.tweet-properties :as props])
  (:gen-class)
  (:import
   (twitter.callbacks.protocols AsyncStreamingCallback)))

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
  (-> (str "twitter.api.restful/" rest-route) symbol resolve))

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

; string->hashmap
(defn gather-tweets
  "Takes a search query, and returns a hashmap of tweets organized by which actions need to be taken on them."
  [search-query]
  (let [all-tweets {}
        tweets (props/tweets-without props/contest-exclusions (saft props/contest-matchers search-query))]
    (assoc all-tweets :to-retweet tweets
                      :to-follow (props/filter-tweets props/follow-matchers tweets)
                      :to-favorite (props/filter-tweets props/favorite-matchers tweets))))

(defn execute-follow-users
  [organized-tweets]
  (dorun (map follow-user (:to-follow organized-tweets))) organized-tweets)

(defn execute-favorite-tweets
  [organized-tweets]
  (dorun (map favorite-tweet (:to-favorite organized-tweets))) organized-tweets)

(defn execute-retweet
  [organized-tweets]
  (dorun (map retweet (:to-retweet organized-tweets))) organized-tweets)

(def perform-actions
  (comp execute-retweet execute-follow-users execute-favorite-tweets))

(defn win
  [search-query]
  (perform-actions (gather-tweets search-query)))

; retrieves the user stream, waits 1 minute and then cancels the async call


; supply a callback that only prints the text of the status
(def ^:dynamic 
  json->hashmap
     (AsyncStreamingCallback. (comp println #(:text %) json/read-json #(str %2)) 
                      (fn [_])
                      (fn [_ ex]
                        (.printStackTrace ex))))

(defn search-stream
  [search-term]
  (stream/statuses-filter :params {:track search-term}
                   :oauth-creds my-creds
                   :callbacks json->hashmap))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (win args))

