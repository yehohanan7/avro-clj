(ns avroclj.avro.adaptertest
  (:use avroclj.avro)
  (:use avroclj.avro.locator)
  (:use avroclj.avro.adapter)
  (:use clojure.test))



(deftest should-convert-dashes-to-camel-case
  (is (= (dash-to-camel "first-name") "firstName"))
  (is (= (dash-to-camel "first-name-extended") "firstNameExtended"))
  )







