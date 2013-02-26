(ns avroclj.avro.locatertest
  (:require [avroclj.avro.locator :as locator])
  (:use clojure.test))


(defn person [fields]
  (with-meta fields {:avro-type :avroclj.model.person.Person})
  )

(deftest should-locate-schema-file
  (is (not (nil? (locator/schema (person {:first-name "John" :middle-name "Pradeep" :last-name "Vincent"})))))
  )



