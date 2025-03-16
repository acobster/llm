(ns env.dev.main
  (:require [messaging-app.core :as core]
            [figwheel.client :as figwheel :include-macros true]))

(figwheel/watch-and-reload
 :websocket-url "ws://localhost:3449/figwheel-ws"
 :heads-up-display true
 :jsload-callback #(core/init))

(core/init)
