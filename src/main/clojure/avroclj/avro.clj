(ns #^{:author "John"
       :doc "A utility to serialize/deserialize clojure map to Avro bytes and vice versa"}
  avroclj.avro
  (:use [avroclj.avro.locator])
  (:use [avroclj.avro.adapter])
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
          schema (locate-schema model)
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
    (let [schema (locate-schema model)
          reader (GenericDatumReader. schema)
          decoder (.binaryDecoder (DecoderFactory/get) bytes nil)
          record (.read reader nil decoder)
          deserialized-model (to-model record model)]
      deserialized-model
      )
    )
  )

