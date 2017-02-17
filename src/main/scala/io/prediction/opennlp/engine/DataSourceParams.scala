package org.apache.predictionio.opennlp.engine

import org.apache.predictionio.controller.Params

case class DataSourceParams(
  appId: Int,
  cutoff: Int) extends Params
