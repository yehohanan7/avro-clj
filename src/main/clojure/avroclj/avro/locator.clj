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

(defn- dot-to-slash [x]
  (clojure.string/replace (name x) #"\." "/")
  )

(defn- schema-name [model]
  (when-let [avro-type (:avro-type (meta model))]
    (with-extension (dot-to-slash avro-type))
    )
  )

(defn- ^java.io.File locate-file [file-name]
  (clojure.java.io/as-file (clojure.java.io/resource file-name))
  )

(def ^:private file (memoize locate-file))

(defn schema [model]
  (or
    (get @schemas (schema-name model))
    (Schema/parse (file (schema-name model))))
  )


(defn add-schema [x]
  (let [schema-name (process-values x [:namespace :name ] #(map name %) #(interpose "/" %) #(apply str %) #(dot-to-slash %) #(with-extension %))
        json-text (json/write-str x)
        schema (Schema/parse json-text)]
    (swap! schemas assoc schema-name schema)
    )
  )