(ns avroclj.avro.adaptertest
  (:require [avroclj.avro.adapter :as adapter])
  (:use clojure.test))



(deftest should-convert-dashes-to-camel-case
  (is (= (adapter/dash-to-camel "first-name") "firstName"))
  (is (= (adapter/dash-to-camel "first-name-extended") "firstNameExtended"))
  )







