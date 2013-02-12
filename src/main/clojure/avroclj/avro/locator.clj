(ns #^{:author "John"
       :doc "Locates The Avro schema for a particular clojure map"}
  avroclj.avro.locator
  (:import [org.apache.avro Schema]))


(set! *warn-on-reflection* true)


(defn- with-extension [path]
  (str path ".avsc")
  )

(defn- ^java.io.File locate-file [model]
  (let [model-name (name (:avro-type (meta model)))
        path (with-extension (clojure.string/replace model-name #"-" "/"))]
    (clojure.java.io/as-file (clojure.java.io/resource path))
    )
  )

(defn- locate-schema-file [model]
  (Schema/parse (locate-file model))
  )

(def locate-schema (memoize locate-schema-file))