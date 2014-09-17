(defproject example-app "0.1.0-SNAPSHOT"
  :description "Immntant-transit example app."
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.cognitect/transit-clj "0.8.255"]
                 [org.immutant/immutant "2.x.incremental.292"]
                 [org.immutant/immutant-transit "0.1.0-SNAPSHOT"]]
  :repositories [["Immutant incremental builds"
                  "http://downloads.immutant.org/incremental/"]])
