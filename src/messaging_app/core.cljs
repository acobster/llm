(ns messaging-app.core
  (:require [messaging-app.react-native-deps]  ;; Load React deps first
            [reagent.core :as r]
            [re-frame.core :as rf]
            [messaging-app.events]
            [messaging-app.subs]
            [messaging-app.views :as views]
            ["react-native" :as rn]))

(defn app-root []
  [views/app-container])

(defn init []
  (rf/dispatch-sync [:initialize-db])
  (.registerComponent rn/AppRegistry "MessagingApp" #(r/reactify-component app-root)))
