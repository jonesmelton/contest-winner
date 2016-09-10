(ns contest-winner.tweet-filtering-test-constants)

(def tweet 
  "a single tweet"
  {:user-id 2501638598, :tweet-text "this isnt a really a quote but theres a sign that says 'here comes dat boi to vote for ____ as class president'", :tweet-hashtags [], :following-poster? false, :retweeted? false, :favorited? false})

(def tweets-for-filtering
  [{:tweet-id 773633830401957889, :user-id 2369728747, :tweet-text "When you cheating on a test and the teacher looking at you like https://t.co/PyxUQLUiv2", :tweet-hashtags [], :following-poster? false, :retweeted? false, :favorited? false} {:tweet-id 773634255867916288, :user-id 306934919, :tweet-text "RT @6PAPl: so the iPhone 7 starts at $649 and the headphones are sold for an additional $159 https://t.co/0vjeeD9ifW", :tweet-hashtags [], :following-poster? false, :retweeted? false, :favorited? false} {:tweet-id 773634411392827392, :user-id 469329078, :tweet-text "Have you prayed??", :tweet-hashtags [], :following-poster? false, :retweeted? false, :favorited? false}])
