(ns avroclj.avrotest
  (:use avroclj.avro)
  (:require [avroclj.avro.locator :as locator])
  (:require [avroclj.avro.adapter :as adapter])
  (:use clojure.test))


(defn person [fields]
  (with-meta fields {:avro-type :avroclj.model.person.Person})
  )

(deftest should-locate-schema-file
  (is (not (nil? (locator/locate-schema (person {:first-name "John" :middle-name "Pradeep" :last-name "Vincent"})))))
  )

(deftest should-serialize-maps
  (let [input (person {:first-name "John" :middle-name "Pradeep" :last-name "Vincent"})]
    (is (not (nil? (serialize input))))
    )
  )


(deftest should-de-serialize-a-record
  (let [model (person {:first-name "John" :middle-name "Pradeep" :last-name "Vincent"})
        serialized (serialize model)
        deserialized (deserialize serialized (person {}))]
    (is (not (nil? deserialized)))
    )
  )

(def location-schema {:namespace :avroclj.model.location
                      :name :Location
                      :type "record"
                      :doc "Represnts a Location"
                      :fields [
                                {:name "id"
                                 :type "string"
                                 }
                                {:name "name"
                                 :type "string"
                                 }
                                ]
                      })

(deftest should-also-consider-dynamic-schemas
  (let [_ (add-schema location-schema)
        model (with-meta {:name "India" :id "IND"} {:avro-type :avroclj.model.location.Location})
        serialized (serialize model)
        deserialized (deserialize serialized (with-meta {:avro-type :avroclj.model.location.Location} {}))]
    (are [x y] (= x y)
      (:name model) (:name deserialized)
      (:id model) (:id deserialized)
      )
    )
  )




