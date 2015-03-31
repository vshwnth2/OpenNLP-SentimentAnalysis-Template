package io.prediction.opennlp.engine

import edu.stanford.nlp.trees.Tree
import io.prediction.controller.{EmptyEvaluationInfo, EmptyParams, PDataSource}
import io.prediction.data.storage.Storage
import io.prediction.opennlp.engine.Sentiment.Sentiment
import opennlp.maxent.BasicEventStream
import opennlp.model.OnePassDataIndexer
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

import scala.collection.JavaConversions._
import scala.util.Random

class DataSource(val dsp: DataSourceParams) extends PDataSource[
  TrainingData,
  EmptyEvaluationInfo,
  Query,
  Sentiment] {

  val Separator = " "

  override def readTraining(sc: SparkContext): TrainingData = {
    val trainingTreeStrings = allTreeStrings(sc)
    treeStringsToTrainingData(trainingTreeStrings)
  }

  override def readEval(
    sc: SparkContext): Seq[(TrainingData, EmptyEvaluationInfo, RDD[(Query, Sentiment)])] = {
    val shuffledTreeStrings = Random.shuffle(allTreeStrings(sc))
    val (trainingTreeStrings, testingTreeStrings) =
      shuffledTreeStrings.splitAt((shuffledTreeStrings.size*0.9).toInt)

    val trainingData = treeStringsToTrainingData(trainingTreeStrings)

    val qna = testingTreeStrings.map { line =>
      val lastSpace = line.lastIndexOf(Separator)
      (Query(line.substring(0, lastSpace)), Sentiment(line.substring(lastSpace + 1).toInt))
    }

    Seq((trainingData, EmptyParams(), sc.parallelize(qna)))
  }

  private def allTreeStrings(sc: SparkContext): Seq[String] = {
    val events = Storage.getPEvents().find(appId = dsp.appId, entityType = Some("tree"))(sc)

    events.flatMap { event =>
      val tree = event.properties.get[String]("tree")
      treeToList(tree)
    }.collect().toSeq
  }

  private def treeStringsToTrainingData(treeStrings: Seq[String]) = {
    val eventStream = new BasicEventStream(new SeqDataStream(treeStrings), Separator)
    val dataIndexer = new OnePassDataIndexer(eventStream, dsp.cutoff)

    TrainingData(dataIndexer)
  }

  private def treeToList(treeString: String) = {
    val tree = Tree.valueOf(treeString)

    tree.subTreeList().map { subtree =>
      if (!subtree.isLeaf) {
        val stringBuilder = new StringBuilder()

        subtree.subTreeList().foreach { subsubtree =>
          if (subsubtree.isLeaf)
            stringBuilder.append(s"${subsubtree.label().value()} ")
        }

        stringBuilder.append(subtree.label().value())

        stringBuilder.toString()
      } else null
    }.filter(_ != null).toSeq

  }
}

