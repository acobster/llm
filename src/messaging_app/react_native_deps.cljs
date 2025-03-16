(ns messaging-app.react-native-deps
  (:require ["react" :as react]
            ["react-native" :as rn]
            ["react-native-vector-icons/MaterialIcons" :default MaterialIcons]
            ["create-react-class" :as create-react-class]))

;; Make React available globally for Reagent
(when (exists? js/window)
  (set! (.-React js/window) react))

;; Export for direct use in other namespaces
(def React react)
(def ReactNative rn)
(def MaterialIconsComponent MaterialIcons)
(def create-react-class create-react-class)
