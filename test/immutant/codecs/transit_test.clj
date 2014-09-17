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

(ns immutant.codecs.transit-test
  (:require [immutant.codecs.transit :refer :all]
            [immutant.codecs         :refer :all]
            [clojure.test            :refer :all]
            [cognitect.transit       :as transit]))

(defrecord Point [x y])

(defrecord Circle [c r])

(defn register-transit []
  (register-transit-codec)

  (register-transit-codec
    :name :transit-msgpack
    :type :msgpack)

  (register-transit-codec
    :name :transit-shapes
    :content-type "application/transit-shapes+json"
    :read-handlers (transit/record-read-handlers Point Circle)
    :write-handlers (transit/record-write-handlers Point Circle)))

(use-fixtures :once
  (fn [f] (register-transit)
    (f)))

(defn test-codec [object & [encoding]]
  (let [encoding (or encoding :transit)
        encoded (encode object encoding)]
    (is (= object (decode encoded encoding)))))

(deftest basic-transit-should-work
  (test-codec {:a :b}))

(deftest basic-transit-with-msgpack-should-work
  (test-codec {:a :b} :transit-msgpack))

(deftest transit-with-custom-handlers-should-work
  (test-codec {:point (->Point 0 0)} :transit-shapes))
