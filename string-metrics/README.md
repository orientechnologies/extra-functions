# string-metrics
This library wraps [java-string-similarity](https://github.com/tdebatty/java-string-similarity)
to provide different string similarity and distance measures as SQL functions.

## Install
* Clone or download this repository
* Run `gradle build shadowJar`
* Copy the file `string-metrics/build/libs/string-metrics-all.jar` in the OrientDB Server `lib` folder
* Add the following `functions` configuration to `config/custom-sql-functions.json` in the OrientDB Server

```json
{
  "prefix": "strics",
  "class": "com.orientechnologies.extra.functions.stringmetrics.StringMetrics"
}
```

* Restart OrientDB

## Functions

This section shows how to use the functions. For more details on the specific algorithms click
[here](https://github.com/tdebatty/java-string-similarity#overview).

### strics_editDistance()

Returns the minimum number of single-character edits (insertions, deletions or substitutions) required to change
one word into the other.

Syntax: ```strics_editDistance(<field|value1>, <field|value2>)```

#### Example

```sql
select strics_editDistance('John A Smith', 'Jonathan A Smith')
--- returns 4
```
---

### strics_editDistanceSimilarity()

Returns the normalized edit distance similarity: a value always in the interval between `0` (no match) and `1.0`
(perfect match).

Syntax: ```strics_editDistanceSimilarity(<field|value1>, <field|value2>)```

#### Example

```sql
select strics_editDistanceSimilarity('John A Smith', 'Jonathan A Smith')
--- returns 0.75
```
---

### strics_damerauDistance()

Similar to edit distance with transposition of of two adjacent characters counted as single operation.

Syntax: ```strics_damerauDistance(<field|value1>, <field|value2>)```

#### Example

```sql
select strics_damerauDistance('John A Smith', 'Jonathan A Smiht')
--- returns 5 (edit distance would return 6)
```
---

### strics_optimalStringAlignmentDistance()

Similar to edit distance with the condition that no substring is edited more than once.

Syntax: ```strics_optimalStringAlignmentDistance(<field|value1>, <field|value2>)```
---

### strics_jaroWinklerSimilarity()

Variation of damerau, developed in the area of record linkage (deduplication), where the substitution of 2 close
characters is considered less important then the substitution of 2 characters that a far from each other. The last
parameter (default to 0.7) specifies the threshold when Winkler bonus should be used. 

Syntax: ```strics_jaroWinklerSimilarity(<field|value1>, <field|value2> [, <threshold> ])```

#### Example

```sql
select strics_jaroWinklerSimilarity('John A Smith', 'Jonathan A Smith')
--- returns 0.8298611342906952
```


