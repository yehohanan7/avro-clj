(ns #^{:author "John"
       :doc "A collection of all avro types"}
  avroclj.avro.types
  (:import [org.apache.avro.util Utf8])
  (:import [org.apache.avro.generic GenericArray])
  )


(defprotocol ClojureType
  (avro-value [this])
  )

(extend-type java.lang.Object ClojureType
  (avro-value [this]
    (.toString this)
    )
  )

(extend-type clojure.lang.IPersistentCollection ClojureType
  (avro-value [this] this)
  )

(extend-type clojure.lang.IPersistentMap ClojureType
  (avro-value [this]
    (into {} (for [[k v] this] [(name k) (avro-value v)]))
    )
  )


(defprotocol AvroType
  (clj-value [this])
  )

(extend-type nil AvroType
  (clj-value [this] nil)
  )

(extend-type java.lang.Object AvroType
  (clj-value [this] this)
  )

(extend-type org.apache.avro.util.Utf8 AvroType
  (clj-value [this] (.toString this))
  )

(extend-type org.apache.avro.generic.GenericData$Array AvroType
  (clj-value [this]
    (map #(clj-value %) this)
    )
  )

(extend-type org.apache.avro.generic.GenericData$Array AvroType
  (clj-value [this]
    (map #(clj-value %) this)
    )
  )

(extend-type java.util.HashMap AvroType
  (clj-value [this]
    (into {} (for [[k v] this] [(keyword (.toString k)) (clj-value v)]))
    )
  )


