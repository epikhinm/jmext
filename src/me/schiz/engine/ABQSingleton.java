/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package me.schiz.engine;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * @author schizophrenia
 * Manager of ABQs (Array Blocking Queue)
 */

public class ABQSingleton {
    private static final Logger log = LoggingManager.getLoggerForClass();
    public final static ABQSingleton instance = new ABQSingleton();
    public ConcurrentHashMap<String, ArrayBlockingQueue<Object>> data;
    
    public ABQSingleton() {
        data = new ConcurrentHashMap<String, ArrayBlockingQueue<Object>>();
    }
    
    public boolean createQueue(String name, int capacity, boolean fair) {
        if (data.containsKey(name)) {
            log.error("Queue `" + name + "` already has been created");
            return false;
        } else {
            data.put(name, new ArrayBlockingQueue<Object>(capacity, fair));
        }
        return true;
    }
    public boolean createQueue(String name, int capacity) {
        return this.createQueue(name, capacity, false);
    }
    public boolean put(String name, Object o) {
        if (!data.containsKey(name)) {
            log.error("Queue `" + name + "`not found");
            return false;
        }
        try {
            data.get(name).put(o);
        } catch (InterruptedException e) {
            log.error("InterruptedException on put object to queue `" + name + "`", e);
        }
        return true;
    }
    public Object take(String name) {
        if (!data.containsKey(name)) {
            log.error("queue `" + name + "`not found");
        } else {
            try {
                return data.get(name).take();
            } catch (InterruptedException e) {
                log.error("InterruptedException on take Object", e);
            }
        }
        return null;
    }
    public int size(String name) {
        if (!data.containsKey(name)) {
            log.error("queue `" + name + "`not found");
            return 0;
        }
        return this.data.get(name).size();
    }
    public void clear(String name) {
        if (!data.containsKey(name))
            log.error("queue `" + name + "`not found");
        this.data.get(name).clear();
    }
}