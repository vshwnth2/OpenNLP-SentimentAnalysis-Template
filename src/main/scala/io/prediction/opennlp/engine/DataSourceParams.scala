package io.prediction.opennlp.engine

import io.prediction.controller.Params

case class DataSourceParams(
  trainingPath: String,
  cutoff: Int,
  testPath: Option[String] = None) extends Params
