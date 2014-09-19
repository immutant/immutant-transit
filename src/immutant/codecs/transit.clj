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
  (:require [immutant.codecs           :refer [decode-error
                                               make-codec register-codec]]
            [immutant.internal.util    :refer [kwargs-or-map->raw-map
                                               try-resolve
                                               try-resolve-throw]]
            [immutant.internal.options :refer [set-valid-options!
                                               validate-options]])
  (:import [java.io ByteArrayInputStream ByteArrayOutputStream]))

(defn ^:internal ^:no-doc transit-codec
  [{:keys [name content-type type read-handlers write-handlers]
    :or {name :transit type :json}}]
  (let [content-type (or content-type (str "application/transit+"
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
                (try
                  (when data
                    (with-open [in (ByteArrayInputStream. data)]
                      (transit-read
                        (transit-reader in type {:handlers read-handlers}))))
                  (catch Throwable e
                    (throw (decode-error :transit data e))))))))

(defn register-transit-codec
  "Creates and registers a codec that can be used to encode to/decode from Transit.

   To use vanilla Transit, call this wih no options and use :transit
   when passing an encoding to functions that take such.

   If you need to provide custom handlers, you can provide them as
   options. The custom handlers are merged with the default handlers by
   transit-clj itself.

   options can be a map or kwargs, with these valid keys [default]:

   * :name - the name of the encoding [:transit]
   * :type - the type of the underlying format. one of: :json, :json-verbose,
     :msgpack [:json]
   * :content-type - the content type for the encoding
     [\"application/transit+<:type>\"]
   * :write-handlers - the full set of handlers to use for writing. If not
     provided, the built-in Transit defaults are used [nil]
   * :read-handlers the full set of handlers to use for reading. If not
     provided, the built-in Transit defaults are used [nil]"
  [& options]
  (-> options
    kwargs-or-map->raw-map
    (validate-options register-transit-codec)
    transit-codec
    register-codec))

(set-valid-options! register-transit-codec #{:name :content-type :type
                                             :read-handlers :write-handlers})
