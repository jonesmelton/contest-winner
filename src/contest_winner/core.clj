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

(def tweet-keys  [:user-id :tweet-text :tweet-hashtags :following_poster? :retweeted? :favorited?])

(defn raw-parse-tweet
  [tweet]
  (zipmap tweet-keys ((juxt props/user-id props/tweet-text props/tweet-hashtags props/following-poster? props/retweeted? props/favorited?) tweet)))

(defn get-tweets
  [query]
  (rest/search-tweets :oauth-creds my-creds :params {:q query}))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (rest/statuses-update :oauth-creds my-creds :params {:status args}))
