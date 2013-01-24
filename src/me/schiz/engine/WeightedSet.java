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

/**
* Class for use set with weighted objects
* @author schizophrenia
*/
import java.util.LinkedList;
import java.util.Random;

public class WeightedSet {
    private LinkedList<Object> data = new LinkedList<Object> ();
    private LinkedList<Integer> index = new LinkedList<Integer> ();
    private Random random = new Random(); //use threadlocal random?:)
    
    public WeightedSet() {}

    public synchronized void clear() {
        this.data.clear();
        this.index.clear();
        random = new Random();
    }
    
    public synchronized void put(Object o, int w) {
        for(Object i : data) {
            if(i == o) {
                for(int j=0;j<w;++j)
                    this.index.add(data.indexOf(i));
                return;
            }
        }
        this.data.add(o);
        for(int j=0;j<w;++j)    this.index.add(data.indexOf(o));
    }
    public synchronized Object get() {
        if(this.index.size() == 0) return null;
        return this.data.get(this.index.get(random.nextInt(this.index.size())));
    }
    public synchronized Object pop() {
        if(this.index.size() == 0)  return null;
        int victimIndex = random.nextInt(this.index.size());
        Object victim = this.data.get(this.index.get(victimIndex));
        this.data.remove(this.index.get(victimIndex));
        this.index.remove(victimIndex);
        return victim;
    }
}