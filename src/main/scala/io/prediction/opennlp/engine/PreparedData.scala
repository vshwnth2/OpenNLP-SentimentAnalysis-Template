package io.prediction.opennlp.engine

import opennlp.model.DataIndexer

case class PreparedData(dataIndexer: DataIndexer) extends Serializable
