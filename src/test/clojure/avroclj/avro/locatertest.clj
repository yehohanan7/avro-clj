(ns avroclj.avro.locatertest
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



