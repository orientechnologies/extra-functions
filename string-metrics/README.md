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

This section shows how to use the functions. For more details on a specific algorithm see
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
---

### strics_longestCommonSubsequenceDistance()

Finds the longest subsequence common to two (or more) sequences.

Syntax: ```strics_longestCommonSubsequenceDistance(<field|value1>, <field|value2>)```

#### Example

```sql
select strics_longestCommonSubsequenceDistance('AGCAT', 'GAC')
--- returns 4
```
---

### strics_cosineSimilarity()

Works by converting strings into sets of n-grams (sequences of n characters, also sometimes called k-shingles). Useful
for large data sets takes into account the number of occurrences of each shingle.

Syntax: ```strics_cosineSimilarity(<field|value1>, <field|value2>, <charLength>)```

#### Example

```sql
select strics_cosineSimilarity("my string,  \n  my song", "another string, from a song", 2)
--- returns 0.5621826951410452
```
---

### strics_ngramDistance()

Normalized N-Gram distance. Uses affixing with special character `\n` to increase the weight of first characters. The
normalization is achieved by dividing the total similarity score the original length of the longest word. Default
`length` value is `2`.

Syntax: ```strics_ngramDistance(<field|value1>, <field|value2> [, <length> ])```

#### Example

```sql
select strics_ngramDistance("ABCD", "ABTUIO")
--- returns 0.5833333134651184
```
---

### strics_qgramDistance()

The distance between two strings is defined as the L1 norm of the difference of their profiles (the number of
occurrences of each n-gram): SUM( |V1_i - V2_i| ). Default `length` value is `2`.

Syntax: ```strics_qgramDistance(<field|value1>, <field|value2> [, <length> ])```

#### Example

```sql
select strics_qgramDistance("ABCD", "ABCE")
--- returns 2
```
---

### strics_jaccardSimilarity()

Like Q-Gram distance, the input strings are first converted into sets of n-grams but this time the cardinality of each 
n-gram is not taken into account. Default `length` value is `3`.

Syntax: ```strics_jaccardSimilarity(<field|value1>, <field|value2> [, <charSeq> ])```

#### Example

```sql
select strics_jaccardSimilarity("ABCDE", "ABCDF", 2)
--- returns 0.6
```
---

### strics_jaccardDistance()

Computed as `1 - similarity`. Default `length` value is `3`.

Syntax: ```strics_jaccardDistance(<field|value1>, <field|value2> [, <charSeq> ])```

---

### strics_sorensenDiceSimilarity()

It can be considered a semimetric version of the Jaccard similarity. Default `length` value is `3`.

Syntax: ```strics_sorensenDiceSimilarity(<field|value1>, <field|value2> [, <charSeq> ])```

#### Example

```sql
select strics_sorensenDiceSimilarity("ABCDE", "ABCDF", 2)
--- returns 0.75
```
---

### strics_sorensenDiceDistance()

Computed as `1 - similarity`. Default `length` value is `3`.

Syntax: ```strics_sorensenDiceDistance(<field|value1>, <field|value2> [, <charSeq> ])```

---

### strics_sift4Distance() `experimental`

General purpose string distance algorithm inspired by JaroWinkler and Longest Common Subsequence. Developed to produce a
distance measure that matches as close as possible to the human perception of string distance.

Syntax: ```strics_sift4Distance(<field|value1>, <field|value2>)```

#### Example

```sql
select strics_sift4Distance("This is the first string", "And this is another string")
--- returns 9
```
