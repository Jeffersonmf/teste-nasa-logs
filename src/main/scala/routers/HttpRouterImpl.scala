package routers

import config.Environment
import core.{ParserEngine, RequestLogResult}
import core.ParserEngine.{loadDataset, loadRemoteDataset}

object HttpRouterImpl {

  def parserLogs(): String = {

    val logsRDD = Environment.isRunningLocalMode() match {
      case true =>  loadDataset()
      case false => loadRemoteDataset()
    }

    printResults(ParserEngine.chargerNASALogs(logsRDD))
  }

  def parserRemoteLogs(): String = {
    val logsRDD = loadRemoteDataset()
    printResults(ParserEngine.chargerNASALogs(logsRDD))
  }

  private def printResults(results: RequestLogResult): java.lang.String = {

    // Creating StringBuilder
    var resultado = new String()

    resultado += s"RESULTADOS COLETADOS DA ANALISE:\n\n"

    resultado += s"1 - NUMERO DE HOSTS UNICOS: ${results.qtdeHost}\n\n"

    resultado += s"2 - O TOTAL DE ERROS 404: ${results.qtdeRequests404}\n\n"

    resultado += s"3 - AS 5 URLS QUE MAIS CAUSARAM ERRO 404\n\n"
    for ( r <- results.cincoTopUrlErros ){
      resultado += s"URL: ${r._1}  QTDE: ${r._2}\n"
    }

    resultado += s"\n4 - QUANTIDADE DE ERROS 404 POR DIA.\n"

    for ( r <- results.qtdeErroPorDia ){
      resultado += s"DIA: ${r._1} QTDE: ${r._2}\n"
    }

    resultado += s"\n5 - O TOTAL DE BYTES RETORNADOS: ${results.qtdeBytes} Bytes\n\n"

    resultado
  }

}