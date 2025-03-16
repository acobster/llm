(ns messaging-app.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :messages
 (fn [db]
   (:messages db)))

(rf/reg-sub
 :current-chat-messages
 :<- [:messages]
 :<- [:current-chat]
 (fn [[messages current-chat] _]
   (filter #(or (and (= (:sender %) (:current-user-id))
                     (= (:receiver %) current-chat))
                (and (= (:receiver %) (:current-user-id))
                     (= (:sender %) current-chat)))
           messages)))

(rf/reg-sub
 :current-user
 (fn [db]
   (:current-user db)))

(rf/reg-sub
 :contacts
 (fn [db]
   (:contacts db)))

(rf/reg-sub
 :current-chat
 (fn [db]
   (:current-chat db)))

(rf/reg-sub
 :current-chat-contact
 :<- [:contacts]
 :<- [:current-chat]
 (fn [[contacts current-chat] _]
   (first (filter #(= (:id %) current-chat) contacts))))
