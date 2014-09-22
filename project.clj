;; Copyright 2014 Red Hat, Inc, and individual contributors.
;;
;; Licensed under the Apache License, Version 2.0 (the "License");
;; you may not use this file except in compliance with the License.
;; You may obtain a copy of the License at
;;
;; http://www.apache.org/licenses/LICENSE-2.0
;;
;; Unless required by applicable law or agreed to in writing, software
;; distributed under the License is distributed on an "AS IS" BASIS,
;; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
;; See the License for the specific language governing permissions and
;; limitations under the License.

(defproject org.immutant/immutant-transit "0.2.2"
  :description "Create Transit codecs for Immutant."
  :url "https://github.com/immutant/immutant-transit/"
  :license {:name "Apache Software License - v 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"
            :distribution :repo}
  :dependencies [[com.cognitect/transit-clj "0.8.259"]]
  :profiles {:dev
             {:dependencies [[org.clojure/clojure "1.6.0"]
                             [org.immutant/core "2.x.incremental.296"]]
              :repositories [["Immutant incremental builds"
                              "http://downloads.immutant.org/incremental/"]]}}
  :signing {:gpg-key "BFC757F9"}
  :lein-release {:deploy-via :clojars})
