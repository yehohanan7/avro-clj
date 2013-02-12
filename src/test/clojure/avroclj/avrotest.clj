(ns avroclj.avrotest
  (:use avroclj.avro)
  (:use avroclj.avro.locator)
  (:use avroclj.avro.adapter)
  (:use clojure.test))


(defn person [fields]
  (with-meta fields {:avro-type :avroclj-model-person-Person})
  )

(deftest should-locate-schema-file
  (is (not (nil? (locate-schema (person {:first-name "John" :middle-name "Pradeep" :last-name "Vincent"})))))
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




