(ns env.dev.main
  (:require [messaging-app.core :as core]
            [shadow.expo.keep-awake :refer [keep-awake]]))

;; Shadow-cljs doesn't require the same watch-and-reload setup
;; as figwheel did

(defn ^:dev/after-load on-reload []
  (core/init))

(defn init []
  (keep-awake)
  (core/init))
