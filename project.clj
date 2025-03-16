(defproject messaging-app "0.1.0-SNAPSHOT"
  :description "A mobile messaging app built with React Native and ClojureScript"
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/clojurescript "1.10.891"]
                 [reagent "1.1.1"]
                 [re-frame "1.2.0"]]
  :plugins [[lein-cljsbuild "1.1.8"]
            [lein-figwheel "0.5.20"]]
  :clean-targets ["target/" "index.ios.js" "index.android.js"]
  :aliases {"prod-build" ^{:doc "Recompile code with prod profile."}
            ["do" "clean"
             ["cljsbuild" "once" "ios"]
             ["cljsbuild" "once" "android"]]}
  :profiles {:dev {:dependencies [[figwheel-sidecar "0.5.20"]
                                  [cider/piggieback "0.5.2"]]
                   :source-paths ["src" "env/dev"]}}
  :cljsbuild {:builds {:ios {:source-paths ["src" "env/prod"]
                             :compiler {:output-to "index.ios.js"
                                        :main "env.ios.main"
                                        :output-dir "target/ios"
                                        :optimizations :advanced}}
                       :android {:source-paths ["src" "env/prod"]
                                 :compiler {:output-to "index.android.js"
                                           :main "env.android.main"
                                           :output-dir "target/android"
                                           :optimizations :advanced}}
                       :dev {:source-paths ["src" "env/dev"]
                             :figwheel true
                             :compiler {:output-to "target/dev/messaging.js"
                                        :main "env.dev.main"
                                        :output-dir "target/dev"
                                        :optimizations :none
                                        :source-map true}}}}
  :figwheel {:server-port 3449})
