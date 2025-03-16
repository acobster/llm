(ns messaging-app.react-setup
  (:require ["react" :as react]
            ["react-native" :as rn]
            ["create-react-class" :as create-react-class]))

;; Make React available globally
(when (exists? js/window)
  (set! (.-React js/window) react)
  (set! (.-ReactNative js/window) rn)
  (set! (.-createReactClass js/window) create-react-class))

;; Export for direct use
(def React react)
(def ReactNative rn)
(def create-react-class create-react-class)
