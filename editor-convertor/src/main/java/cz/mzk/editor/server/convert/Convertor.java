/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */

package cz.mzk.editor.server.convert;

import java.util.Arrays;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinRouter;

/**
 * @author Jiri Kremser
 * @version 19.4.2012
 */
public class Convertor {

    public static void convert() {
        ActorSystem system = ActorSystem.create("Server");

        //TODO: move to configuration
        ActorRef worker1 = system.actorFor("akka://Workers@195.113.155.50:2552/user/worker");
        ActorRef worker2 = system.actorFor("akka://Workers@195.113.155.46:2552/user/worker");
        Iterable<ActorRef> routees = Arrays.asList(new ActorRef[] {worker1, worker2});

        ActorRef router =
                system.actorOf(new Props(Worker.class).withRouter(RoundRobinRouter.create(routees)));
        List<ConvertTask> tasks =
                Arrays.asList(new ConvertTask("1", "2", "3"), new ConvertTask("11", "22", "33"));
        router.tell(new ConvertTaskBatch(tasks));
        router.tell(new ConvertTaskBatch(tasks));
        router.tell(new ConvertTaskBatch(tasks));
        system.shutdown();
    }

    public static void main(String... args) {
        Convertor.convert();
    }
}
