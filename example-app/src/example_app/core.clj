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

(ns example-app.core
  (:require [immutant.messaging :as msg]
            [immutant.caching :as cache]
            [immutant.codecs.transit :as it]
            [cognitect.transit :as transit]))

(defrecord Square [x y size])

(def queue (msg/queue "a-sample-queue" :durable? false))

(def cache (cache/cache "transit-example"))

(defn -main []
  ;; enable a vanilla transit codec
  (it/register-transit-codec)

  (msg/listen queue (partial println "received:"))

  ;; use transit with messaging
  (msg/publish queue {:ham :biscuit} :encoding :transit)

  ;; use the cache, decorated with a transit encoding
  (def transit-cache (cache/with-codec cache :transit))

  (.put transit-cache :a-key {:ham :biscuit})

  ;; see the decoded value
  (get transit-cache :a-key)

  ;; access the base cache and see the encoded value (we have to use
  ;; the encoded key)
  (String. (get cache (.getBytes "[\"~#'\",\"~:a-key\"]")))


  ;; enable a transit codec with custom read/write handlers
  (it/register-transit-codec
    :name :transit-custom
    ;; only required if you are also using a vanilla transit codec, or
    ;; want to be clear this one isn't vanilla
    :content-type "application/transit-custom+json"
    ;; optional, defaults to :json already
    :type :json
    ;; specify read/write handlers
    :read-handlers (transit/record-read-handlers Square)
    :write-handlers (transit/record-write-handlers Square))

  ;; use the new codec with messaging
  (msg/publish queue (->Square 1 2 3) :encoding :transit-custom)

  ;; use the new codec with caching
  (def transit-cache (cache/with-codec cache :transit-custom))

  (.put transit-cache :a-key {:ham (->Square 1 2 3)})

  ;; see the decoded value
  (get transit-cache :a-key)

  ;; access the base cache and see the encoded value (we have to use
  ;; the encoded key)
  (String. (get cache (.getBytes "[\"~#'\",\"~:a-key\"]"))))
