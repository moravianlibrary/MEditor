package cz.mzk.editor.server.convert

import akka.actor.{Actor, ActorRef, ActorSystem, Address, AddressFromURIString, Props}
import akka.kernel.Bootable
import akka.event.Logging
import akka.routing.RoundRobinPool
import akka.remote.routing.RemoteRouterConfig

import scala.sys.process.Process
import com.typesafe.config.ConfigFactory
import java.util.UUID.randomUUID

trait ConvertMsg

case class ConvertTask(uuid: String, input: String, output: String) extends ConvertMsg

case class Ok(uuid :String) extends ConvertMsg

case class NotOk(uuid :String) extends ConvertMsg

class Worker extends Actor {
  val prefix = sys.env("CONVERT_HOME")

  def receive = {
    case ConvertTask(uuid, input, output) ⇒ {
      println("DEBUG: " + prefix + "bin/compress.sh " + prefix + " " + input + " " + output)
      val conversion = Process(prefix + "bin/compress.sh " + prefix + " " + input + " " + output)
      val exitCode = conversion.!
      println(exitCode)
      if (exitCode == 0) 
        sender ! Ok(uuid)
      else 
        sender ! NotOk(uuid)
    }
  }
}

/**
 * This class is used for testing purposes only
 */
class Server extends Bootable {

  val basicConf = ConfigFactory.parseString("""
akka {
 log-config-on-start = on

 event-handlers = ["akka.event.Logging$DefaultLogger"]
 # Options: ERROR, WARNING, INFO, DEBUG
 loglevel = "DEBUG"
} 

""")

  ActorSystem("Log", ConfigFactory.load(basicConf))
  val system = ActorSystem("Server")

  def startup = {
    val log = Logging(system, "")
    log.info("Deploy")
    // 195.113.155.50 -> editor-devel.mzk.cz
    // 195.113.155.46 -> editor.mzk.cz
    //val worker1 = system.actorFor("akka://Workers@195.113.155.50:2552/user/worker")
    //val worker2 = system.actorFor("akka://Workers@195.113.155.46:2552/user/worker")
    //val routees = Vector[ActorRef](worker1, worker2)


    //val router = system.actorOf(Props[Worker].withRouter(RoundRobinRouter(routees = routees)))
    val router = system.actorOf(RoundRobinPool(2).props(Props[Worker]))
    val master = system.actorOf(Props[Master])

    val inputPrefix = "/home/meditor/input/monograph/test/"
    val outputPrefix = "/home/meditor/imageserver/unknown/test/"
    router.tell(ConvertTask(randomUUID.toString, inputPrefix + "0001.jpg", outputPrefix + "1.jp2"), master);
    router.tell(ConvertTask(randomUUID.toString, inputPrefix + "0002.jpg", outputPrefix + "2.jp2"), master);
    router.tell(ConvertTask(randomUUID.toString, inputPrefix + "0003.jpg", outputPrefix + "3.jp2"), master);
    router.tell(ConvertTask(randomUUID.toString, inputPrefix + "0004.jpg", outputPrefix + "4.jp2"), master);
    router.tell(ConvertTask(randomUUID.toString, inputPrefix + "0005.jpg", outputPrefix + "5.jp2"), master);
    router.tell(ConvertTask(randomUUID.toString, inputPrefix + "0006.jpg", outputPrefix + "6.jp2"), master);
    router.tell(ConvertTask(randomUUID.toString, inputPrefix + "0007.jpg", outputPrefix + "7.jp2"), master);
    router.tell(ConvertTask(randomUUID.toString, inputPrefix + "0008.jpg", outputPrefix + "8.jp2"), master);
    router.tell(ConvertTask(randomUUID.toString, inputPrefix + "0009.jpg", outputPrefix + "9.jp2"), master);
    router.tell(ConvertTask(randomUUID.toString, inputPrefix + "0010.jpg", outputPrefix + "10.jp2"), master);
    router.tell(ConvertTask(randomUUID.toString, inputPrefix + "001.jpg", outputPrefix + "11.jp2"), master);
  }

  def shutdown = {
    system.shutdown()
  }
}

class Client extends Bootable {
  val customConf = ConfigFactory.parseString("""

akka {
 log-config-on-start = on

 event-handlers = ["akka.event.Logging$DefaultLogger"]
 # Options: ERROR, WARNING, INFO, DEBUG
 loglevel = "DEBUG"

  actor {
    provider = "akka.remote.RemoteActorRefProvider"
  }

 actor {

   debug {
     receive = on
     lifecycle = on
     fsm = on
     event-stream = on
   }
 }

 remote {
   log-sent-messages = on
   log-received-messages = on
 }

}
      """)

  val system = ActorSystem("Workers", ConfigFactory.load(customConf))

  def startup = {
    system.actorOf(Props[Worker], name = "worker")
  }

  def shutdown = {
    system.shutdown()
  }
}


