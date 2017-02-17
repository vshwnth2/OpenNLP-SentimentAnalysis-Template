package org.apache.predictionio.opennlp.evaluation

import org.apache.predictionio.controller.Engine
import org.apache.predictionio.controller.EngineParams
import org.apache.predictionio.controller.EngineParamsGenerator
import org.apache.predictionio.controller.Evaluation
import org.apache.predictionio.opennlp.engine.Algorithm
import org.apache.predictionio.opennlp.engine.AlgorithmParams
import org.apache.predictionio.opennlp.engine.DataSource
import org.apache.predictionio.opennlp.engine.DataSourceParams
import org.apache.predictionio.opennlp.engine.Preparator
import org.apache.predictionio.opennlp.engine.Serving

class SimpleEvaluation extends Evaluation with EngineParamsGenerator {
  engineParamsList = Seq(
    EngineParams(
      algorithmParamsList = Seq(("algo", AlgorithmParams(300, true))),
      dataSourceParams = DataSourceParams(3, 10)),
    EngineParams(
      algorithmParamsList = Seq(("algo", AlgorithmParams(300, true))),
      dataSourceParams = DataSourceParams(3, 5)),
    EngineParams(
      algorithmParamsList = Seq(("algo", AlgorithmParams(300, true))),
      dataSourceParams = DataSourceParams(3, 2)),
    EngineParams(
      algorithmParamsList = Seq(("algo", AlgorithmParams(500, true))),
      dataSourceParams = DataSourceParams(3, 10)),
    EngineParams(
      algorithmParamsList = Seq(("algo", AlgorithmParams(500, true))),
      dataSourceParams = DataSourceParams(3, 5)),
    EngineParams(
      algorithmParamsList = Seq(("algo", AlgorithmParams(500, true))),
      dataSourceParams = DataSourceParams(3, 2))


  )

  engineMetric = (
    new Engine(
      classOf[DataSource],
      classOf[Preparator],
      Map("algo" -> classOf[Algorithm]),
      classOf[Serving]),
    new AccuracyMetric())
}
