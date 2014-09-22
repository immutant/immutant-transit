(defproject example-app "0.1.0-SNAPSHOT"
  :description "Immntant-transit example app."
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.immutant/immutant "2.x.incremental.298"]
                 [org.immutant/immutant-transit "0.2.1"]]
  :repositories [["Immutant incremental builds"
                  "http://downloads.immutant.org/incremental/"]]
  :main example-app.core)
