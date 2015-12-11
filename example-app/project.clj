;; Copyright 2014-2015 Red Hat, Inc, and individual contributors.
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

(defproject example-app "0.1.0-SNAPSHOT"
  :description "Immntant-transit example app."
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.immutant/immutant "2.1.1"]
                 [org.immutant/immutant-transit "0.2.3"]]
  :main example-app.core)
