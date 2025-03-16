(ns env.dev.main
  (:require [messaging-app.core :as core]
            [figwheel.main.api :as figwheel]))

;; Figwheel-main doesn't require the same watch-and-reload setup
;; as the older figwheel-sidecar did

(core/init)

;; Optionally add development-specific initialization here
(defn on-jsload []
  (core/init))
