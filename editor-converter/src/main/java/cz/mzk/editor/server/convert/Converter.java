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

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import akka.routing.RoundRobinPool;
import cz.mzk.editor.server.config.EditorConfiguration;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author Jiri Kremser
 * @version 19.4.2012
 */
public class Converter {

    private ActorRef router;
    private ActorSystem system;
    private ActorRef master;
    private Map<String, ConvertTask> taskPool = new ConcurrentHashMap<String, ConvertTask>();
    private Map<String, ConvertTask> finishedWithError = new ConcurrentHashMap<String, ConvertTask>();
    private volatile boolean someoneIsIn = false;
    private static int MAX_CONVERSION_TIME = 1000 * 15;

    private static Converter instance = null;

    private Converter() {

    }

    public static synchronized Converter getInstance() {
        if (instance == null) {
            instance = new Converter();
        }
        return instance;
    }

    public synchronized void start(String[] ips) {
        system = ActorSystem.create("Server");
        List<ActorRef> routees = new ArrayList<ActorRef>();
        for (String ip : ips) {
            routees.add(system.actorFor("akka://Workers@" + ip + "/user/worker"));
        }
        if (routees.isEmpty()) {
            // perhaps throw some ConvertException and convert the images in the old fashioned way
            throw new IllegalStateException("there are no akka worker nodes set in the config");
        }
//        router = system.actorOf(Props.create(Worker.class).withRouter(RoundRobinRouter.create(routees)));
        router = system.actorOf(new RoundRobinPool(2).props(Props.create(Worker.class)));
        master = system.actorOf(Props.create(Master.class));
        someoneIsIn = true;
    }

    public synchronized boolean isRunning() {
        return someoneIsIn;
    }

    public synchronized void stop() {
        system.shutdown();
        someoneIsIn = false;
    }

    public boolean convert(String input, String output) {
        if (system == null || system.isTerminated() || router == null || master == null) {
            throw new IllegalStateException("run init method first");
        }
        String uuid = UUID.nameUUIDFromBytes((input + "##" + output).getBytes()).toString();
        ConvertTask task = new ConvertTask(uuid, input, output);
        router.tell(task, master);
        taskPool.put(uuid, task);
        synchronized (task) {
            try {
                task.wait(MAX_CONVERSION_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return finishedWithError.containsKey(uuid);
    }

    public synchronized void finishTask(String uuid) {
        ConvertTask task = taskPool.remove(uuid);
        synchronized (task) {
            task.notifyAll();
        }
    }

    public synchronized boolean allFinished() {
        return taskPool.isEmpty();
    }

    public synchronized void finishWithError(String uuid) {
        ConvertTask task = taskPool.remove(uuid);
        finishedWithError.put(uuid, task);
        // do st with the task
        synchronized (task) {
            task.notifyAll();
        }
    }

    public static void main(String... args) {
        Converter convertor = Converter.getInstance();
        String inputPrefix = "/home/meditor/input/monograph/test/";
        String outputPrefix = "/home/meditor/imageserver/unknown/test/";
        DecimalFormat nf = new DecimalFormat("0000");
        for (int i = 1; i <= 10; i++) {
            String input = inputPrefix + nf.format(i) + ".jpg";
            String output = outputPrefix + i + ".jp2";
            convertor.convert(input, output);
        }
    }
}
