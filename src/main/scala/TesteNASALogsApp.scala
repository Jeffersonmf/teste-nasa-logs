package teste

import akka.actor.ActorSystem
import colossus.IOSystem
import colossus.core.InitContext
import colossus.protocols.http.server.{HttpServer, Initializer}
import core.ParserEngine
import routers.HttpRouterHandler

object NASALogsApp extends App {

  implicit val actorSystem = ActorSystem()
  implicit val ioSystem = IOSystem()

  ParserEngine.initSparkSession()

  HttpServer.start("Scala-Colossus", 9000){ context => new HandlerInitializer(context) }
}

class HandlerInitializer(context: InitContext) extends Initializer(context) {
  override def onConnect = context => new HttpRouterHandler(context)
}