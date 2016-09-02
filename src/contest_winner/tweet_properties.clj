(ns contest-winner.tweet-properties)

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

