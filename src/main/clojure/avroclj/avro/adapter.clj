(ns #^{:author "John"
       :doc "used to convert clojure maps into avro model and vice versa"}
  avroclj.avro.adapter
  (:use [avroclj.avro.types])
  (:use [avroclj.avro.locator])
  (:import [org.apache.avro Schema]
           [org.apache.avro.generic GenericData$Record]
           [org.apache.avro.util Utf8]))

(defn not-nil? [x]
  (not (nil? x))
  )

(defn- valid-model? [x]
  (and (not-nil? x)
    (not-nil? (meta x))
    (not-nil? (:avro-type (meta x))))
  )


(defn dash-to-camel [x]
  (let [input (name x)]
    (clojure.string/replace input #"-." (fn [i] (clojure.string/upper-case (second i))))
    )
  )

(defn camel-to-dash [x]
  (apply str (map #(if (Character/isUpperCase %)
                     (str "-" (clojure.string/lower-case %))
                     %)
               x))
  )

(defn to-record [model]
  {:pre [(valid-model? model)]}
  (let [schema (locate-schema model)
        record (GenericData$Record. schema)]
    (doseq [[k v] model]
      (.put record (dash-to-camel k) (avro-value v))
      )
    record
    )
  )

(defn to-model [record model]
  {:pre [(and (not (nil? record)) (not (nil? model)))]}
  (let [schema (locate-schema model)
        fields (map #(.name %) (.getFields schema))
        getter #(.toString (.get record %))
        key-value-mapper #(vector (keyword (camel-to-dash %)) (getter %))]
    (apply merge {} (map key-value-mapper fields))
    )
  )