# A Clojure Wrapper for Apache avro serialization/deserialization


Note: At the moment, avro-clj doesn't support Nested maps, which will be added in next release.


## Example

```clojure
(ns sample
  (:require [avroclj.avro :as avro]))

;The avro schema to use for serializing/desrializing should be attached to the
;clojure map using the meta key :avro-type

;in the below example, avroclj will look for the avro schema file:"avroclj/model/person/Person.avsc" in
;the class path to serialize/deserialize


(defn person [fields]
  (with-meta fields {:avro-type :avroclj-model-person-Person})
  )

;Serializes the map into a byte array
(def byteArray (avro/serialize (person {:first-name "John" :middle-name "Pradeep" :last-name "Vincent"})))


;Deserializes the bytes into the map passed as argument
(avro/deserialize byteArray (person {}))

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

### Artifact
avro-clj is published in [clojars](https://clojars.org/yehohanan7/avro-clj) 

## Installation 


### With Leiningen:

    [yehohanan7/avro-clj "0.2"]

### With Maven:

``` xml
<dependency>
  <groupId>yehohanan7</groupId>
  <artifactId>avro-clj</artifactId>
  <version>0.2</version>
</dependency>
```
