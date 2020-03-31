package core

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SQLContext}
import util._

import scala.util.Either

case class RequestLog(host: String, timestamp: String, url: String, code: String, bytes: Long)

case class RequestLogResult(totalRegistros: Long, qtdeHost: Long, qtdeRequests404: Long, cincoTopUrlErros: Array[(String,Long)], qtdeErroPorDia: Array[(String,Long)], qtdeBytes: Long, status: Boolean)

object RequestLog {

  private val PATTERN = buildLogPattern()

  def loadContents(logsRDD: RDD[String], sc: SparkContext): DataFrame = {

    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val pmap = logsRDD.map ( line => line.split ("-"))

    val parsedLines = logsRDD.map(RequestLog.parseLine _)

    val requestLogDF = parsedLines.filter(_.isRight).map(_.right.get)
    requestLogDF.toDS().toDF()
  }

  private def parseLine(line: String): Either[String, RequestLog] = {
    line match {
      case PATTERN(host, ts, req, code, b) =>
        val url = Utils.extractURLFromRequest(req)
        val bytes = Utils.convertToLong(b)
        Right(RequestLog(host, ts, url.getOrElse(req), code, bytes.getOrElse(0)))
      case _ => Left(line)
    }
  }

  private def buildLogPattern() = {
    """^(\S+) - - \[(.*?)\] "(.*?)" (\d{3}) (\S+)""".r
  }

}