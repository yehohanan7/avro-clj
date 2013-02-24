(ns #^{:author "John"
       :doc "A utility to serialize/deserialize clojure map to Avro bytes and vice versa"}
  avroclj.avro
  (:require [avroclj.avro.locator :as locator])
  (:use [avroclj.avro.adapter])
  (:use [clojure.java.io])
  (:import [org.apache.avro Schema]
           [org.apache.avro.generic GenericDatumWriter GenericDatumReader GenericData GenericData$Record]
           [org.apache.avro.io BinaryEncoder EncoderFactory DecoderFactory BinaryDecoder]
           [java.io ByteArrayOutputStream]
           [org.apache.avro.util Utf8]))


(set! *warn-on-reflection* true)


(defprotocol FlexiDataSerializer
  (serialize [this])
  )

(defprotocol FlexiDataDeserializer
  (deserialize [this model])
  )

(extend-type java.lang.Object FlexiDataSerializer
  (serialize [model]

    )
  )

(extend-type clojure.lang.IPersistentMap FlexiDataSerializer
  (serialize [model]
    (let [record (to-record model)
          stream (ByteArrayOutputStream.)
          schema (locator/locate-schema model)
          writer (GenericDatumWriter. schema)
          encoder (.binaryEncoder (EncoderFactory/get) stream nil)
          _ (.write writer record encoder)
          _ (.flush encoder)
          _ (.close stream)]
      (.toByteArray stream)
      )
    )
  )

(extend-type (class (byte-array 0)) FlexiDataDeserializer
  (deserialize [bytes model]
    (let [schema (locator/locate-schema model)
          reader (GenericDatumReader. schema)
          decoder (.binaryDecoder (DecoderFactory/get) bytes nil)
          record (.read reader nil decoder)
          deserialized-model (to-model record model)]
      deserialized-model
      )
    )
  )


(defn add-schema [schema-map]
  (locator/add-schema schema-map)
  )

