(ns #^{:author "John"
       :doc "Locates The Avro schema for a particular clojure map"}
  avroclj.avro.locator
  (:require [clojure.data.json :as json])
  (:import [org.apache.avro Schema]))


(set! *warn-on-reflection* true)

(def ^:private schemas (atom {}))

(defmacro process-values [m keys & fns]
  `(let [values# ((juxt ~@keys) ~m)]
     ((apply comp (reverse (list ~@fns))) values#)
     )
  )

(defn- with-extension [path]
  (str path ".avsc")
  )

(defn- schema-name [model]
  (when-let [avro-type (:avro-type (meta model))]
    (with-extension (clojure.string/replace (name avro-type) #"\." "/"))
    )
  )

(defn- ^java.io.File locate-file [schema-name]
  (clojure.java.io/as-file (clojure.java.io/resource schema-name))
  )

(defn- schema [model]
  (or
    (get @schemas (schema-name model))
    (Schema/parse (locate-file (schema-name model))))
  )

(def locate-schema (memoize schema))


(defn add-schema [x]
  (let [schema-name (process-values x [:namespace :name ] #(map name %) #(interpose "." %) #(apply str "/" %))
        json-text (json/write-str x)
        schema (Schema/parse json-text)]
    (swap! schemas assoc schema-name schema)
    )
  )