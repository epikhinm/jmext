jmext
=====

JMeter Extensions


####ABQ (ArrayBlockingQueue)

`${__ABQCreate(queue_name, capacity, fair_ordering)}` Create BlockingQueue with capacity (integer value) and fair ordering (optional, default is false). Fair ordering make context switch when you use Put / Take methods

`${__ABQPut(queue_name, object)}` Put string-object into blocking queue `queue_name`

`${__ABQTake(queue_name)}` Take string-object from blocking queue `queue_name`
