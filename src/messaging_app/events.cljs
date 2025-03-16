(ns messaging-app.events
  (:require [re-frame.core :as rf]
            [messaging-app.db :as db]))

(rf/reg-event-db
 :initialize-db
 (fn [_ _]
   db/default-db))

(rf/reg-event-db
 :send-message
 (fn [db [_ message]]
   (let [current-user-id (get-in db [:current-user :id])
         current-chat (get db :current-chat)
         new-message {:id (str (random-uuid))
                      :text message
                      :sender current-user-id
                      :receiver current-chat
                      :timestamp (.now js/Date)}]
     (update db :messages conj new-message))))

(rf/reg-event-db
 :change-chat
 (fn [db [_ contact-id]]
   (assoc db :current-chat contact-id)))
