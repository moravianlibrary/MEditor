package cz.mzk.editor.server.convert

import akka.actor.{ Actor, ActorSystem, Props, ActorRef, Address, AddressFromURIString }
import akka.kernel.Bootable
import akka.event.Logging
import akka.routing.{ RoundRobinRouter, RemoteRouterConfig }
//import akka.routing._
import akka.dispatch.ExecutionContext 
import scala.sys.process.Process
import com.typesafe.config.ConfigFactory
import java.util.concurrent.Executors
import scala.collection.JavaConversions._


case class ConvertTask(home: String, input: String, output: String)

case class ConvertTaskBatch(tasks: java.util.List[ConvertTask])

case class Ok()

case class NotOk()


class Worker extends Actor {
  val prefix = sys.env("CONVERT_HOME")

  def receive = {
    case ConvertTaskBatch(tasks) ⇒ {
        tasks.foreach {
            task ⇒ {
                println("DEBUG: " + prefix + "/compress.sh " + task.home + " " + task.input + " " + task.output)
                val conversion = Process(prefix + "/compress.sh " + task.home + " " + task.input + " " + task.output)
                val exitCode = conversion.!
                println(exitCode)
            }
        }
    }
  }
}


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
    val worker1 = system.actorFor("akka://Workers@195.113.155.50:2552/user/worker")
    val worker2 = system.actorFor("akka://Workers@195.113.155.46:2552/user/worker")
    val routees = Vector[ActorRef](worker1, worker2)
    val router = system.actorOf(Props[Worker].withRouter(RoundRobinRouter(routees = routees)))
    val list1 = new java.util.ArrayList[ConvertTask]()
    list1.add(ConvertTask("1","2","3"))
    list1.add(ConvertTask("11","22","33"))
    router ! ConvertTaskBatch(list1)
    router ! ConvertTaskBatch(List(ConvertTask("111","222","333"), ConvertTask("1111","2222","3333")))
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


