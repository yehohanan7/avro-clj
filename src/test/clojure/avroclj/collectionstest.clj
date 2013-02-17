(ns avroclj.collectionstest
  (:use avroclj.avro)
  (:use avroclj.avro.locator)
  (:use avroclj.avro.adapter)
  (:use clojure.test))



(defn person []
  (with-meta {:first-name "John" :middle-name "Pradeep" :last-name "Vincent" :hobbies ["music" "hacking"]}
    {:avro-type :avroclj-model-person-Person})
  )

(defn person-with-skills []
  (merge (person) {:skills {:clj "clojure" :cljs "clojure script"}})
  )


(deftest should-serialize-arrays
  (let [p (person)
        serialized (serialize p)]
    (is (not (nil? serialized)))
    )
  )


(deftest should-de-serialize-record-with-arrays
  (let [p (person)
        serialized (serialize p)
        model (deserialize serialized (with-meta {} {:avro-type :avroclj-model-person-Person}))]
    (is (= (:first-name model) "John"))
    (is (= (:middle-name model) "Pradeep"))
    (is (= (:last-name model) "Vincent"))
    (is (= (:hobbies model) ["music" "hacking"]))
    )
  )

(deftest should-de-serialize-record-with-maps
  (let [p (person-with-skills)
        serialized (serialize p)
        model (deserialize serialized (with-meta {} {:avro-type :avroclj-model-person-Person}))]
    (is (= (:first-name model) "John"))
    (is (= (:middle-name model) "Pradeep"))
    (is (= (:last-name model) "Vincent"))
    (is (= (:hobbies model) ["music" "hacking"]))
    (is (= (:skills model) {:clj "clojure" :cljs "clojure script"}))
    )
  )



