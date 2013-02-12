# A Clojure Wrapper for Apache avro serialization/deserialization

## Example

```clojure
(ns avroclj.avrotest
  (require '[avroclj.avro :as avro]))

(defn person [fields]
  (with-meta fields {:avro-type :avroclj-model-person-Person})
  )

;Serializes the map into a byte array
(def bytes (avro/serialize (person {:first-name "John" :middle-name "Pradeep" :last-name "Vincent"})))


;Deserializes the bytes into the map passed as argument
(avro/deserialize bytes (person {}))

```


## Installation


### With Leiningen:

    [yehohanan7/avro-clj "0.1"]

### With Maven:

``` xml
<dependency>
  <groupId>yehohanan7</groupId>
  <artifactId>avro-clj</artifactId>
  <version>0.1</version>
</dependency>
```
