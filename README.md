jmext
=====

JMeter Extensions


####ABQ (ArrayBlockingQueue)

#####JMeter interface

`${__ABQCreate(queue_name, capacity, fair_ordering)}` Create BlockingQueue with capacity (integer value) and fair ordering (optional, default is false). Fair ordering make context switch when you use Put / Take methods

`${__ABQPut(queue_name, object)}` Put string-object into blocking queue `queue_name`

`${__ABQTake(queue_name)}` Take string-object from blocking queue `queue_name`

#####Java interface

`me.schiz.engine.ABQSingleton.instance.createQueue(String name, int capacity, boolean far)` Create ABQ with name, capacity and fair
`me.schiz.engine.ABQSingleton.instance.put(String name, Object o)` Put `java.lang.Object` into `name` queue 
`me.schiz.engine.ABQSingleton.instance.take(String name)` Take `java.lang.Object` from `name` queue
`me.schiz.engine.ABQSingleton.instance.size(String name)` Get size of `name` queue
`me.schiz.engine.ABQSingleton.instance.clear(String name)` Clear `name` queue
