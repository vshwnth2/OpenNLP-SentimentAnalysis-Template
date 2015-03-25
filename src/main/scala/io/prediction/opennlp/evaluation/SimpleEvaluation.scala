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
      dataSourceParams = DataSourceParams("./data/train.txt", 10, Some("./data/test.txt"))),
    EngineParams(
      algorithmParamsList = Seq(("algo", AlgorithmParams(300, true))),
      dataSourceParams = DataSourceParams("./data/train.txt", 5, Some("./data/test.txt"))),
    EngineParams(
      algorithmParamsList = Seq(("algo", AlgorithmParams(300, true))),
      dataSourceParams = DataSourceParams("./data/train.txt", 2, Some("./data/test.txt"))),
    EngineParams(
      algorithmParamsList = Seq(("algo", AlgorithmParams(300, true))),
      dataSourceParams = DataSourceParams("./data/train.txt", 1, Some("./data/test.txt"))),
    EngineParams(
      algorithmParamsList = Seq(("algo", AlgorithmParams(300, true))),
      dataSourceParams = DataSourceParams("./data/train.txt", 20, Some("./data/test.txt")))

  )

  engineMetric = (
    new Engine(
      classOf[DataSource],
      classOf[Preparator],
      Map("algo" -> classOf[Algorithm]),
      classOf[Serving]),
    new AccuracyMetric())
}
