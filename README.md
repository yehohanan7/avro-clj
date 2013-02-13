# A Clojure Wrapper for Apache avro serialization/deserialization

## Supported Features
At the moment, avro-clj provides support only for flat clojure maps, it will be extended to support heirarchical maps shortly


## Example

```clojure
(ns avroclj.avrotest
  (require '[avroclj.avro :as avro]))

;The avro schema to use for serializing/desrializing should be attached to the
;clojure map using the meta key :avro-type

;in the below example, avroclj will look for the avro schema file:"avroclj/model/person/Person.avsc" in
;the class path to serialize/deserialize


(defn person [fields]
  (with-meta fields {:avro-type :avroclj-model-person-Person})
  )

;Serializes the map into a byte array
(def bytes (avro/serialize (person {:first-name "John" :middle-name "Pradeep" :last-name "Vincent"})))


;Deserializes the bytes into the map passed as argument
(avro/deserialize bytes (person {}))

```


### avroclj/model/person/Person.avsc

```json
{
  "namespace": "avroclj.model.person",
  "type" : "record",
  "name" : "Person",
  "doc" : "Represents a Person",
  "fields" : [
    {"name" : "firstName", "type" : "string"},
    {"name" : "middleName", "type" : "string"},
    {"name" : "lastName", "type" : "string"}
  ]
}
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
