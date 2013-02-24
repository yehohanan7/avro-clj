(ns #^{:author "John"
       :doc "Basic utilities required for avro serialization"}
  avroclj.avro.util
  (:import [java.io File]))




(defn create-and-write [path content]
  (let [f (File. path)]
    (println (.getAbsolutePath f))
    )
  )