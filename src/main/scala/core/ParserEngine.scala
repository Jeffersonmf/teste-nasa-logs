package core

import java.nio.charset.StandardCharsets

import config.Environment
import exceptions.ParseLogException
import org.apache.spark.rdd.RDD
import org.apache.spark.sql._
import org.apache.spark.sql.functions.{asc, desc}

object ParserEngine  {

  def parseLocalSourceData(str: String): Unit = {}

  private def sparkContextInitialize(): SparkSession = {
    SparkSession.builder
      .master("local")
      .appName("NASA Logs Teste")
      .config("spark.some.config.option", true).getOrCreate()
  }

  private def getSparkContext(): SparkSession = {
    val spark = sparkContextInitialize()
    spark.conf.set("spark.driver.memory", "4g")
    spark.conf.set("spark.executor.memory", "4g")
    spark.conf.set("spark.driver.maxResultSize","4g")
    spark.conf.set("spark.cores.max", "4")
    spark
  }

  def initSparkSession()= {
    getSparkContext()
  }

  val spark = initSparkSession()

  final case class DependencyException(private val message: String = "", private val cause: Throwable = None.orNull)
    extends Exception(message, cause)

  def chargerNASALogs(logsRDD: RDD[String]): RequestLogResult = {
    import org.apache.spark.sql.functions._

    try {
      logsRDD.cache()

      val totalRows = logsRDD.count()

      val dfParsedLines = RequestLog.loadContents(logsRDD, spark.sparkContext)
      dfParsedLines.createOrReplaceTempView("table_NASA_Logs")

      // 1 - Número​ ​ de​ ​ hosts​ ​ únicos.
      val totalHosts = spark.sql("SELECT distinct(host) FROM table_NASA_Logs")
        .toDF().count()

      // 2 - O​ ​total​ ​ de​ ​ erros​ ​ 404.
      val requestsNotFound = dfParsedLines.filter(dfParsedLines.col("code").equalTo("404")).count()

      // 3 - Os​ ​ 5 ​ ​ URLs​ ​ que​ ​ mais​ ​ causaram​ ​ erro​ ​ 404.
      val top5Urls = compute5TopURL()

      // 4 - Total 404 errors by day
      val errorsByDays = computeErrorsByDay()

      // 5 - O​ ​ total​ ​ de​ ​ bytes​ ​ retornados.
      val totalBytes = dfParsedLines.select(col("bytes")).rdd.map(_(0).asInstanceOf[Long]).reduce(_+_)

      RequestLogResult(totalRows, totalHosts, requestsNotFound, top5Urls, errorsByDays, totalBytes, true)

    } catch {
      case e: Exception => {
        throw new ParseLogException("Retorno do Processamento.: ".concat(false.toString).concat(" \n\nProblema no carregamento dos Logs da NASA...  Detalhes:".concat(e.getMessage)))
      }
    }
  }

  private def processInLocalStorage(path: String): Boolean = {
    false
  }

  def loadDataset(): RDD[String] = {
    val encoding = StandardCharsets.US_ASCII.name()
    val logAug95 = spark.read.option("encoding", encoding).textFile(Environment.getSourceLocalFolder() + "/NASA_access_log_Aug95.gz")
    val logJul95 = spark.read.option("encoding", encoding).textFile(Environment.getSourceLocalFolder() + "/NASA_access_log_Jul95.gz")
    logAug95.union(logJul95).rdd
  }

  def loadRemoteDataset(): RDD[String] = {
    val encoding = StandardCharsets.US_ASCII.name()

    import org.apache.spark.SparkFiles

    spark.sparkContext.addFile("ftp://ita.ee.lbl.gov/traces/NASA_access_log_Jul95.gz")
    spark.sparkContext.addFile("ftp://ita.ee.lbl.gov/traces/NASA_access_log_Aug95.gz")

    val fileName1 = SparkFiles.get("NASA_access_log_Jul95.gz")
    val fileName2 = SparkFiles.get("NASA_access_log_Aug95.gz")

    spark.read.option("encoding", encoding).textFile(fileName1).union(spark.read.option("encoding", encoding).textFile(fileName2)).rdd
  }

  def computeErrorsByDay() = {
    val filteredByDate = spark.sql("SELECT host, code, substr(timestamp,0, 11) AS dia FROM table_NASA_Logs where code = '404'").toDF()

    val errorsByDate = filteredByDate
      .groupBy("dia")
      .count
      .orderBy(asc("dia")).collect().map(row => (row.getString(0), row.getLong(1)))

    errorsByDate
  }

  def compute5TopURL() = {
    val dfNotFoundRequests = spark.sql("SELECT host, code FROM table_NASA_Logs where code = '404'")
      .toDF()

    val fiveMostURLError = dfNotFoundRequests
      .groupBy("host")
      .count
      .orderBy(desc("count"))
      .take(5).map(row => (row.getString(0), row.getLong(1)))

    fiveMostURLError
  }
}