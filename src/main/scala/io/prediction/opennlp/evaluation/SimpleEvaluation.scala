package io.prediction.opennlp.evaluation

import io.prediction.controller.Engine
import io.prediction.controller.EngineParams
import io.prediction.controller.EngineParamsGenerator
import io.prediction.controller.Evaluation
import io.prediction.opennlp.engine.Algorithm
import io.prediction.opennlp.engine.AlgorithmParams
import io.prediction.opennlp.engine.DataSource
import io.prediction.opennlp.engine.DataSourceParams
import io.prediction.opennlp.engine.Preparator
import io.prediction.opennlp.engine.Serving

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
