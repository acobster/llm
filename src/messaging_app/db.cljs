(ns messaging-app.db)

(def default-db
  {:messages []
   :current-user {:id "user1" :name "You"}
   :contacts [{:id "user2" :name "Alice"}
              {:id "user3" :name "Bob"}
              {:id "user4" :name "Charlie"}]
   :current-chat "user2"})
