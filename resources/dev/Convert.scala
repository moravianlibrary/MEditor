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


class Worker extends Actor {
  val prefix = sys.env("CONVERT_HOME")

  def receive = {
    case ConvertTask(home, input, output) ⇒ {
	println("DEBUG: " + prefix + "/compress.sh " + home + " " + input + " " + output)
	//(prefix + "/compress.sh " + home + " " + input + " " + output)!
	val conversion = Process(prefix + "/compress.sh " + home + " " + input + " " + output)
	val exitCode = conversion.!
        println(exitCode)
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



  //val context = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(2))
  //val system = ActorSystem("Creation", config.getConfig("creation"))
  ActorSystem("Creation", ConfigFactory.load(basicConf))
  val system = ActorSystem("Creation", ConfigFactory.load(customConf))




  def startup = {
    val log = Logging(system, "")
    log.info("Deploy")


    //val addresses = Seq(Address("akka", "workers", "editor-devel.mzk.cz", 2552))
    val addresses = Seq(AddressFromURIString("akka://othersys@anotherhost:1234"))

    val routerRemote = system.actorOf(Props[Worker].withRouter(RemoteRouterConfig(RoundRobinRouter(5), addresses)))


    val worker1 = system.actorFor("akka://workers@editor-devel.mzk.cz:2552/user/workers")
    //val worker2 = system.actorFor("akka://workers@editor.mzk.cz:2552/user/workers")
    //val routees = Vector[ActorRef](worker1, worker2)
    //val router = system.actorOf(Props[Worker].withRouter(RoundRobinRouter(routees = routees)))
    //router ! ConvertTask("11","22","33")
    routerRemote ! ConvertTask("11","22","33")
    //worker1 ! ConvertTask("11","22","33")


    //system.actorOf(Props[Worker], "workers") ! ConvertTask("asdf","sadf","sdf")
    //system.actorFor("akka://workers@editor-devel.mzk.cz:2552/user/workers") ! ConvertTask("asdf","sadf","sdf")
  }

  def shutdown = {
    system.shutdown()
  }
}



