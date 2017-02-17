package org.apache.predictionio.opennlp.engine

import java.io.File
import java.io.FileInputStream
import java.util.zip.GZIPInputStream

import org.apache.predictionio.controller.IPersistentModel
import org.apache.predictionio.controller.IPersistentModelLoader
import opennlp.maxent.io.GISModelReader
import opennlp.maxent.io.SuffixSensitiveGISModelWriter
import opennlp.model.AbstractModel
import opennlp.model.PlainTextFileDataReader
import org.apache.spark.SparkContext

case class Model(gis: AbstractModel) extends IPersistentModel[AlgorithmParams] with Serializable {

  override def save(id: String, params: AlgorithmParams, sc: SparkContext): Boolean = {
    val writer = new SuffixSensitiveGISModelWriter(gis, new File(s"/tmp/gis-$id"))
    writer.persist()

    true
  }
}

object Model extends IPersistentModelLoader[AlgorithmParams, Model] {


  override def apply(id: String, params: AlgorithmParams, sc: Option[SparkContext]): Model = {

    val inputStream = new FileInputStream(new File(s"/tmp/gis-$id"))
    val modelReader = new PlainTextFileDataReader(inputStream)
    val gis = new GISModelReader(modelReader).getModel

    Model(gis)
  }
}
