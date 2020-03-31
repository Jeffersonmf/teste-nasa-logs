package config

object Environment {
  val conf = com.typesafe.config.ConfigFactory.load()

  def getSourceLocalFolder(): String = {
    val configValue = s"Configuration.SourceLocalFolder"
    conf.getString(configValue)
  }

  def getSourceRemoteFiles(): String = {
    val configValue = s"Configuration.SourceRemoteFiles"
    conf.getString(configValue)
  }

  def isRunningLocalMode(): Boolean = {
    conf.getBoolean("Configuration.LocalMode")
  }
}