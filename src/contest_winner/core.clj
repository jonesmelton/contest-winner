(ns contest-winner.core
  (:require
                [twitter.api.restful :as rest]
                [twitter.oauth :as twitter-oauth]
                [environ.core :refer [env]])
  (:gen-class))

(def my-creds (twitter-oauth/make-oauth-creds (env :app-consumer-key)
                                              (env :app-consumer-secret)
                                              (env :user-access-token)
                                              (env :user-access-secret)))


(defn user-id [tweet]
  (get-in tweet [:body :user :id]))

(defn tweet-text [tweet]
  (get-in tweet [:body :text]))

(defn tweet-hashtags [tweet]
  (get-in tweet [:body :entities :hashtags]))

(defn following-poster? [tweet]
  (get-in tweet [:body :user :following]))

(defn retweeted? [tweet]
  (get-in tweet [:body :retweeted]))

(defn favorited? [tweet]
  (get-in tweet [:body :favorited]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (rest/statuses-update :oauth-creds my-creds :params {:status args}))
