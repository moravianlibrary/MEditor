package cz.mzk.editor.convert

import akka.actor.{ Actor, ActorSystem, Props, ActorRef, Address, AddressFromURIString }
import akka.kernel.Bootable
import akka.event.Logging
import akka.routing.{ RoundRobinRouter, RemoteRouterConfig }
//import akka.routing._
import akka.dispatch.ExecutionContext 
import scala.sys.process.Process
import com.typesafe.config.ConfigFactory
import java.util.concurrent.Executors


case class ConvertTask(home: String, input: String, output: String)

case class ConvertTaskBatch(tasks: List[ConvertTask])


class Worker extends Actor {
  val prefix = sys.env("CONVERT_HOME")

  def receive = {
    case ConvertTaskBatch(tasks) ⇒ {
        tasks.foreach {
            task ⇒ {
                println("DEBUG: " + prefix + "/compress.sh " + home + " " + input + " " + output)
                val conversion = Process(prefix + "/compress.sh " + home + " " + input + " " + output)
                val exitCode = conversion.!
                println(exitCode)
            }
        }
        
    }
  }
}


class Convert extends Bootable {
val customConf = ConfigFactory.parseString("""
creation { 
  akka { 
    actor {
      deployment {
        /workers {
          remote = "akka://remoteworkers@editor-devel.mzk.cz:2552"
        }
      }
    }

    remote.netty.port = 2552
    remote.netty.hostname = "editor-devel.mzk.cz" 
  } 
}

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
     # enable function of LoggingReceive, which is to log any received message at
     # DEBUG level
     receive = on
     lifecycle = on
     fsm = on
     event-stream = on
   }
 }


 remote {
   # If this is "on", Akka will log all outbound messages at DEBUG level, if off then they are not logged
   log-sent-messages = on
   log-received-messages = on
 }

  remote {
    transport = "akka.remote.netty.NettyRemoteSupport"
    netty {
      hostname = "127.0.0.1"
      port = 2552
    }
  }

}
      """)



val basicConf = ConfigFactory.parseString("""
akka {
 log-config-on-start = on

 event-handlers = ["akka.event.Logging$DefaultLogger"]
 # Options: ERROR, WARNING, INFO, DEBUG
 loglevel = "DEBUG"
} 

""")

  ActorSystem("Creation", ConfigFactory.load(basicConf))
  val system = ActorSystem("Creation", ConfigFactory.load(customConf))




  def startup = {
  val log = Logging(system, "")
    log.info("Deploy")
    // 195.113.155.50 -> editor-devel.mzk.cz
    // 195.113.155.46 -> editor.mzk.cz
    val worker1 = system.actorFor("akka://Workers@195.113.155.50:2552/user/worker")
    val worker2 = system.actorFor("akka://Workers@195.113.155.46:2552/user/worker")
    val routees = Vector[ActorRef](worker1, worker2)
    val router = system.actorOf(Props[Worker].withRouter(RoundRobinRouter(nrOfInstances = 2, routees = routees)))
    router ! ConvertTaskBatch(List(ConvertTask("1","2","3"), ConvertTask("11","22","33")))
    router ! ConvertTaskBatch(List(ConvertTask("111","222","333"), ConvertTask("1111","2222","3333")))    
  }

  def shutdown = {
    system.shutdown()
  }
}



