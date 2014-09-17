(ns example-app.core
  (:require [immutant.messaging :as msg]
            [immutant.caching :as cache]
            [immutant.codecs.transit :as it]
            [cognitect.transit :as transit]))

(comment
  (defrecord Square [x y size])

  (def queue (msg/queue "a-sample-queue" :durable false))

  (def cache (cache/cache "transit-example"))

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
  (String. (get cache (.getBytes "[\"~#'\",\"~:a-key\"]")))

  )
