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

## Functions

This section shows how to use the functions. For more details on the specific algorithms click
[here](https://github.com/tdebatty/java-string-similarity#overview).

### match_editDistance()

Returns the minimum number of single-character edits (insertions, deletions or substitutions) required to change
one word into the other.

Syntax: ```match_editDistance(<field1|value1>, <field2|value2>)```

#### Example

```sql
select match_editDistance('John A Smith', 'Jonathan A Smith')
--- returns 4
```
