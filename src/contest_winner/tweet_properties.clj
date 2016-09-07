(ns contest-winner.tweet-properties)

(defn tweet-id [tweet]
  (get-in tweet [:id]))

(defn user-id [tweet]
  (get-in tweet [:user :id]))

(defn tweet-text [tweet]
  (get-in tweet [:text]))

(defn tweet-hashtags [tweet]
  (get-in tweet [:entities :hashtags]))

(defn following-poster? [tweet]
  (get-in tweet [:user :following]))

(defn retweeted? [tweet]
  "indicates if the current user has retweeted the tweet"
  (get-in tweet [:retweeted]))

(defn favorited? [tweet]
  (get-in tweet [:favorited]))

(def tweet-keys
  [:tweet-id :user-id :tweet-text :tweet-hashtags :following-poster? :retweeted? :favorited?])

(def tweet-parsers
  [tweet-id user-id tweet-text tweet-hashtags following-poster? retweeted? favorited?])

(defn parse-tweet
    [tweet]
    (zipmap tweet-keys ((apply juxt tweet-parsers) tweet)))

(defn tweets-from-response
  [search-response]
  (get-in search-response [:body :statuses]))

(defn contains?? 
  [regex string]
  (boolean (re-find regex string)))

(defn tweet-contains?
  [regex tweet]
  (contains?? regex (:tweet-text tweet)))

(defn filter-tweets
  [regex tweets]
  (filter (partial tweet-contains? regex) tweets))

