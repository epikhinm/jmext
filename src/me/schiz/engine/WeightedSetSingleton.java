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

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * Manager class for WeightedSets
 * @author schizophrenia
 */
public class WeightedSetSingleton {
    private static final Logger log = LoggingManager.getLoggerForClass();
    public final static WeightedSetSingleton instance = new WeightedSetSingleton();
    private ConcurrentHashMap<String, WeightedSet> data;
    
    public WeightedSetSingleton() {
        this.data= new ConcurrentHashMap<String, WeightedSet>();
    }
    
    public void putObject(String name, Object o, int w) {
        WeightedSet s = this.data.get(name);
        if(s == null) {
            s = new WeightedSet();
            this.data.put(name, s);
        }
        s.put(o, w);
    }
    public Object getObject(String name) {
        WeightedSet s = this.data.get(name);
        if(s != null)	return s.get();
        log.error("not found element from `" + name + "` weight set");
        return null;
    }
    public Object popObject(String name) {
        WeightedSet s = this.data.get(name);
        if(s != null)	return s.pop();
        log.error("not found element from `" + name + "` weight set");
        return null;
    }
    public void clear(String name) {
        WeightedSet s = this.data.get(name);
        if(s != null)	s.clear();
	}
    public void clearAll() {
        for(Entry<String, WeightedSet> ws : data.entrySet())
            ws.getValue().clear();
    }
    public void put(String name, String o, int w) {
        this.putObject(name, o, w);
    }
    public String get(String name) {
        Object o= this.getObject(name);
        if(o == null)   return new String();
        return o.toString();
    }
    public String pop(String name) {
        Object o= this.popObject(name);
        if(o == null)   return new String();
        return o.toString();
    }
}
