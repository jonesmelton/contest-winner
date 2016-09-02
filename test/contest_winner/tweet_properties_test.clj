(ns contest-winner.tweet-properties-test
  (:require [clojure.test :refer :all]
            [contest-winner.tweet-properties :refer :all]))

(def tweet
  {:headers {:cache-control "no-cache, no-store, must-revalidate, pre-check=0, post-check=0", :content-disposition "attachment; filename=json.json", :content-length "1836", :content-type "application/json;charset=utf-8", :date "Fri, 02 Sep 2016 20:25:51 GMT", :expires "Tue, 31 Mar 1981 05:00:00 GMT", :last-modified "Fri, 02 Sep 2016 20:25:51 GMT", :pragma "no-cache", :server "tsa_a", :set-cookie ["lang=en; Path=/" "guest_id=v1%3A147284795121495437; Domain=.twitter.com; Path=/; Expires=Sun, 02-Sep-2018 20:25:51 UTC"], :status "200 OK", :strict-transport-security "max-age=631138519", :x-access-level "read-write-directmessages", :x-connection-hash "658c8bad4133cc1ae2d7a7708fe8cfe1", :x-content-type-options "nosniff", :x-frame-options "SAMEORIGIN", :x-response-time "81", :x-transaction "00aa899e00bf93d5", :x-tsa-request-body-time "1", :x-twitter-response-tags "BouncerCompliant", :x-xss-protection "1; mode=block"}, :status {:code 200, :msg "OK", :protocol "HTTP/1.1", :major 1, :minor 1}, :body {:in_reply_to_screen_name nil, :is_quote_status false, :coordinates nil, :in_reply_to_status_id_str nil, :place nil, :geo nil, :in_reply_to_status_id nil, :entities {:hashtags [], :symbols [], :user_mentions [], :urls []}, :source "<a href=\"http://www.example.com\" rel=\"nofollow\">clj-contest</a>", :lang "en", :in_reply_to_user_id_str nil, :id 771806363668271104, :contributors nil, :truncated false, :retweeted false, :in_reply_to_user_id nil, :id_str "771806363668271104", :favorited false, :user {:description "yep", :profile_link_color "2B7BB9", :profile_sidebar_border_color "C0DEED", :is_translation_enabled false, :profile_image_url "http://pbs.twimg.com/profile_images/771790247508242432/QP_8oCHX_normal.jpg", :profile_use_background_image true, :default_profile true, :profile_background_image_url nil, :is_translator false, :profile_text_color "333333", :name "boomhower", :profile_background_image_url_https nil, :favourites_count 0, :screen_name "datboomwow", :entities {:description {:urls []}}, :listed_count 0, :profile_image_url_https "https://pbs.twimg.com/profile_images/771790247508242432/QP_8oCHX_normal.jpg", :statuses_count 2, :has_extended_profile true, :contributors_enabled false, :following false, :lang "en", :utc_offset nil, :notifications false, :default_profile_image false, :profile_background_color "F5F8FA", :id 771789532379222016, :follow_request_sent false, :url nil, :time_zone nil, :profile_sidebar_fill_color "DDEEF6", :protected false, :profile_background_tile false, :id_str "771789532379222016", :geo_enabled false, :location "Chicago, IL", :followers_count 0, :friends_count 0, :verified false, :created_at "Fri Sep 02 19:18:58 +0000 2016"}, :retweet_count 0, :favorite_count 0, :created_at "Fri Sep 02 20:25:51 +0000 2016", :text "cats"}})

(deftest tweet-parsing 
  (testing "returns user_id"
    (is (= 771789532379222016 (user-id tweet))))

  (testing "return text content of a tweet"
    (is (= "cats" (tweet-text tweet))))
  
  (testing "should return hashtags on the tweet"
    (is (= [] (tweet-hashtags tweet))))
  
  (testing "returns following status"
    (is (= false (following-poster? tweet))))
  
  (testing "returns true if bot retweeted the tweet"
    (is (= false (retweeted? tweet))))

  (testing "returns true if bot favorited the tweet"
    (is (= false (favorited? tweet)))))
