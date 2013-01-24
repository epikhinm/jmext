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

package me.schiz.functions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


import me.schiz.engine.ABQSingleton;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;


/**
* Create ABQ JMeter function
* @author schizophrenia
*/

public class ABQCreateFunction extends AbstractFunction {
    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__ABQCreate";
    
    static {
        desc.add("Queue name");
        desc.add("Capacity");
        desc.add("Fair ordering (default is false)");
    }
    
    private Object[] values;

    public ABQCreateFunction() {}

    public synchronized String execute(SampleResult previousResult, Sampler currentSampler)
            throws InvalidVariableException {
        String name = ((CompoundVariable) values[0]).execute();
        int capacity = Integer.parseInt(((CompoundVariable) values[1]).execute().replaceAll("[^0-9]+", ""));
        boolean fair = false;
        if(values.length == 3)
            fair = Boolean.parseBoolean(((CompoundVariable) values[2]).execute());
        ABQSingleton.instance.createQueue(name, capacity, fair);
        return name;
    }

    public synchronized void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkMinParameterCount(parameters, 2);
        values = parameters.toArray();
    }
    
    public String getReferenceKey() {
        return KEY;
    }

    public List<String> getArgumentDesc() {
        return desc;
    }

}
