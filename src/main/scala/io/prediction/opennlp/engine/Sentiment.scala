package org.apache.predictionio.opennlp.engine

object Sentiment extends Enumeration{
  type Sentiment = Value
  val Negative = Value(0)
  val SomewhatNegative = Value(1)
  val Neutral = Value(2)
  val SomewhatPositive = Value(3)
  val Positive = Value(4)
}

