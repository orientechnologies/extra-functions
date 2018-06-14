# string-metrics
This library wraps [java-string-similarity](https://github.com/tdebatty/java-string-similarity)
to provide different string similarity and distance measures as SQL functions.

## Install
* Clone or download this repository
* Run `gradle build shadowJar`
* Copy the file `string-metrics/build/libs/string-metrics-all.jar` in the OrientDB Server `lib` folder
* Add the following `function` configuration to `config/custom-sql-functions.json` in the OrientDB Server

```json
{
  "prefix": "match",
  "class": "com.orientechnologies.extra.functions.stringmetrics.StringMetrics"
}
```

* Restart OrientDB

