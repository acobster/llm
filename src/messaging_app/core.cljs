(ns messaging-app.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [messaging-app.events]
            [messaging-app.subs]
            [messaging-app.views :as views]
            [messaging-app.react-native-deps]
            ["react-native" :as rn]))

(defn app-root []
  [views/app-container])

(defn init []
  (rf/dispatch-sync [:initialize-db])
  (.registerComponent rn/AppRegistry "MessagingApp" #(r/reactify-component app-root)))
