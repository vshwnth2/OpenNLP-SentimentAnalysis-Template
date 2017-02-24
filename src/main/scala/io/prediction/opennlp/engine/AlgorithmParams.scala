package org.apache.predictionio.opennlp.engine

import org.apache.predictionio.controller.Params

case class AlgorithmParams(iteration: Int, smoothing: Boolean) extends Params
