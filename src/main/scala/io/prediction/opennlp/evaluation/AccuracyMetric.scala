package org.apache.predictionio.opennlp.evaluation

import org.apache.predictionio.controller.EmptyEvaluationInfo
import org.apache.predictionio.controller.Metric
import org.apache.predictionio.opennlp.engine.PredictedResult
import org.apache.predictionio.opennlp.engine.Query
import org.apache.predictionio.opennlp.engine.Sentiment.Sentiment
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

import org.apache.spark.SparkContext._

class AccuracyMetric
  extends Metric[EmptyEvaluationInfo, Query, PredictedResult, Sentiment, Double] {

  override def calculate(
    sc: SparkContext,
    data: Seq[(EmptyEvaluationInfo, RDD[(Query, PredictedResult, Sentiment)])]): Double = {

    val accurate = sc.accumulator(0)
    val inaccurate = sc.accumulator(0)

    data.foreach { set =>
      set._2.foreach { one =>
        val predicted = one._2.sentiment
        val actual = one._3.toString

        if (predicted == actual) {
          accurate += 1
        } else {
          inaccurate += 1
        }
      }
    }

    accurate.value.toDouble / (accurate.value.toDouble + inaccurate.value.toDouble)
  }
}
