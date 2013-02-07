jmext
=====

JMeter Extensions

####WeightedSet

#####JMeter interface
`{_wsPut(set_name, object, weight)}` Put `object` with `weight` into weighted set `set_name`.

`{_wsPut(set_name)}` Get `object` from weighted set `set_name`.

`{_wsPop(set_name)}` Pop `object` from weighted set `set_name`.

`{_wsClear(set_name)}` Clear weighted set `set_name`.

#####Java interface
`me.schiz.engine.WeightedSetSingleton.instance.putObject(String name, Object o, int w)` put object `o` into set `name` with weight `w`

`me.schiz.engine.WeightedSetSingleton.instance.getObject(String name)` get object from set `name`

`me.schiz.engine.WeightedSetSingleton.instance.popObject(String name)` pop object from set `name`

`me.schiz.engine.WeightedSetSingleton.instance.clear(String name)`clear set `name`

`me.schiz.engine.WeightedSetSingleton.instance.clearAll()`clear all sets

`me.schiz.engine.WeightedSetSingleton.instance.put(String name, String o, int w)` put string `i` into set `name` with weight `w`

`me.schiz.engine.WeightedSetSingleton.instance.get(String name)` get String from set `name`

`me.schiz.engine.WeightedSetSingleton.instance.pop(String name)` pop String from set `name`


####ABQ (ArrayBlockingQueue)

#####JMeter interface

`${__ABQCreate(queue_name, capacity, fair_ordering)}` Create BlockingQueue with capacity (integer value) and fair ordering (optional, default is false). Fair ordering make context switch when you use Put / Take methods.

`${__ABQPut(queue_name, object)}` Put string-object into blocking queue `queue_name`.

`${__ABQTake(queue_name)}` Take string-object from blocking queue `queue_name`.

#####Java interface

`me.schiz.engine.ABQSingleton.instance.createQueue(String name, int capacity, boolean far)` Create ABQ with name, capacity and fair

`me.schiz.engine.ABQSingleton.instance.put(String name, Object o)` Put `java.lang.Object` into `name` queue 

`me.schiz.engine.ABQSingleton.instance.take(String name)` Take `java.lang.Object` from `name` queue

`me.schiz.engine.ABQSingleton.instance.size(String name)` Get size of `name` queue

`me.schiz.engine.ABQSingleton.instance.clear(String name)` Clear `name` queue
