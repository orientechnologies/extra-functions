@file:JvmName("StringMetrics")
package com.orientechnologies.extra.functions.stringmetrics

import aballano.kotlinmemoization.memoize
import info.debatty.java.stringsimilarity.*
import info.debatty.java.stringsimilarity.experimental.Sift4

private val levenshtein = Levenshtein()
fun editDistance(s1: String, s2: String): Double = levenshtein.distance(s1, s2)

private val normalizedLevenshtein = NormalizedLevenshtein()
fun editDistanceSimilarity(s1: String, s2: String): Double = normalizedLevenshtein.similarity(s1, s2)

private val damerau = Damerau()
fun damerauDistance(s1: String, s2: String): Double = damerau.distance(s1, s2)

private val optimalStringAlignment = OptimalStringAlignment()
fun optimalStringAlignmentDistance(s1: String, s2: String) = optimalStringAlignment.distance(s1, s2)

private val jaroWinkler = { threshold: Double -> JaroWinkler(threshold) }.memoize(5)
@JvmOverloads fun jaroWinklerSimilarity(s1: String, s2: String, threshold: Double = .7): Double =
        jaroWinkler(threshold).similarity(s1, s2)

private val longestCommonSubsequence = LongestCommonSubsequence()
fun longestCommonSubsequenceDistance(s1: String, s2: String) = longestCommonSubsequence.distance(s1, s2)

private val cosine = { charLength: Int -> Cosine(charLength) }.memoize(5)
fun cosineSimilarity(s1: String, s2: String, charLength: Int) = cosine(charLength).similarity(s1, s2)

private val ngram = { length: Int -> NGram(length) }.memoize(5)
@JvmOverloads fun ngramDistance(s1: String, s2: String, length: Int = 2) = ngram(length).distance(s1, s2)

private val qgram = { length: Int -> QGram(length) }.memoize(5)
@JvmOverloads fun qgramDistance(s1: String, s2: String, length: Int = 2) = qgram(length).distance(s1, s2)

private val jaccard = { charSeq: Int -> Jaccard(charSeq) }.memoize(5)
@JvmOverloads fun jaccardDistance(s1: String, s2: String, charSeq: Int = 3) = jaccard(charSeq).distance(s1, s2)
@JvmOverloads fun jaccardSimilarity(s1: String, s2: String, charSeq: Int = 3) = jaccard(charSeq).similarity(s1, s2)

private val sorensenDice = { charSeq: Int -> SorensenDice(charSeq) }.memoize(5)
@JvmOverloads fun sorensenDiceDistance(s1: String, s2: String, charSeq: Int = 3) = sorensenDice(charSeq).distance(s1, s2)
@JvmOverloads fun sorensenDiceSimilarity(s1: String, s2: String, charSeq: Int = 3) = sorensenDice(charSeq).similarity(s1, s2)

private val sift4 = Sift4()
fun sift4Distance(s1: String, s2: String) = sift4.distance(s1, s2)
