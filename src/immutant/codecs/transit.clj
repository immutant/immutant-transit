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

(ns immutant.codecs.transit
  "Provides support for using Transit as an Immutant codec."
  (:require [immutant.codecs           :refer [make-codec register-codec]]
            [immutant.internal.util    :refer [kwargs-or-map->raw-map
                                               try-resolve
                                               try-resolve-throw]]
            [immutant.internal.options :refer [set-valid-options!
                                               validate-options]])
  (:import [java.io ByteArrayInputStream ByteArrayOutputStream]))

(defn transit-codec
  "Creates an Immutant codec that can be used to encode to/decode from Transit.

   options can be a map or kwargs, with these valid keys [default]:

   * :name [:transit]
   * :type [:json]
   * :content-type [\"application/transit+<:type>\"]
   * :write-handlers [nil]
   * :read-handlers [nil]"
  [& options]
  (let [{:keys [name content-type type read-handlers write-handlers]
         :or {name :transit type :json}} (-> options
                                           kwargs-or-map->raw-map
                                           (validate-options transit-codec))
         content-type (or content-type (str "application/transit+"
                                         (name type)))
         transit-write (try-resolve-throw `cognitect.transit/write
                         "add com.cognitect/transit-clj to your dependencies.")
         transit-writer (try-resolve 'cognitect.transit/writer)
         transit-read (try-resolve 'cognitect.transit/read)
         transit-reader (try-resolve 'cognitect.transit/reader)]
    (make-codec
      :name name
      :content-type content-type
      :type :bytes
      :encode (fn [data]
                (with-open [out (ByteArrayOutputStream. 2048)]
                  (transit-write
                    (transit-writer out type {:handlers write-handlers})
                    data)
                  (.toByteArray out)))
      :decode (fn [data]
                (when data
                  (with-open [in (ByteArrayInputStream. data)]
                    (transit-read
                      (transit-reader in type {:handlers read-handlers}))))))))

(set-valid-options! transit-codec #{:name :content-type :type
                                    :read-handlers :write-handlers})

(defn register-transit-codec
  "Creates and registers a Transit-based codec in one step.

   Takes the same options as {{transit-codec}}."
  [& options]
  (register-codec (apply transit-codec options)))
