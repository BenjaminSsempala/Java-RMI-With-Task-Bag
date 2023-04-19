# Using Java RMI to implement a Task Bag

## **Overview:**
As part of a Distributed Systems Development class, I was tasked with implementing a  system that allowed workers to retrieve tasks and submit results to a taskbag. The system was to be hosted on a server, and multiple workers would be able to connect to the system simultaneously to retrieve tasks and submit results.

## **Approach:**
I used Java RMI (Remote Method Invocation) as the communication protocol between the server and the workers. 

### Steps:
- I created a TaskBag interface that defined the methods for adding tasks, retrieving tasks, and submitting results.
- I then created a TaskBagImpl class that implemented the TaskBag interface, which contained the actual logic for adding tasks, retrieving tasks, and submitting results. The TaskBagImpl class also maintained data structures in form of maps that held the tasks and results.
- To ensure the system was thread-safe and did not allow race conditions, I implemented synchronization on the methods that accessed the data structure holding the tasks and results. This ensured that only one thread could access the data structure at a time and prevented multiple workers from accessing the same task simultaneously.



## How task maps onto the Task Bag operations in both worker and master
The following Task Bag operations were used in both the Worker and Master classes to implement the above functionalities:

`pairOutTask(String key, int id)`:
This operation is used by the Master to place a new task in the Task Bag, with its corresponding task ID.

`pairOutData(int id, int[] data )`:
This operation is used by the Master to place data for the new task in the Task Bag using its ID.

`pairInTask(String key)`:
This operation is used by the Worker to take a task from the Task Bag and retrieve its corresponding ID. In this implementation, the Worker had to take a task with a specific key ("NextTask").

`pairInData(int taskId)`:
This operation is used by the Worker to retrieve the data associated with a task ID. 

`pairOutResult(String key, int[] result)`:
This operation is used by the Worker to place the result of a task in the Task Bag.  The key used for results was “results”.

`pairInResult(String key)`:
This operation is used by the Master to retrieve the results of tasks from the Task Bag using the key.

`printTasks()`:
This operation is used by the Master to print the tasks in the Task Bag in the console.

`printResults()`:
This operation is used by the Master to print the results of tasks in the Task Bag.

`wait()`:
This operation is used by the Master to suspend its execution until it receives a notification from a Worker that a task has been completed.        


## The synchronization problem
There are two approaches that could be used in implementing this project: polling and callbacks.
* Polling would require the Master to constantly poll the Task Bag for Result Pairs and Workers to poll for Task Pairs. Both the Master and Workers would continually poll after some specified amount of time, say 2 seconds, before polling again. 

* In the callback approach, the Task Bag maintains information about which workers are available for work and their "busy" state, whenever a task is put into the task bag from the worker. When a worker finishes a task, it notifies the Task Bag, which then triggers a callback to the master informing it of the new available result. This reduces the need for constant polling and ensures that the Task Bag data is retrieved as soon as possible.

### To implement this approach in Java RMI,
* The client creates a remote object that implements an interface containing a function to be invoked by the Task Bag.    
* Clients can register their remote interfaces with the Task Bag, which then records them in a list. For instance, a worker can simply join the worker group and notify the Task Bag that it is available
* Whenever there is a task in the Task Bag, the Task Bag object notifies the clients using these remote interfaces.  
* Similarly, when a worker finishes a task, it notifies the Task Bag, which then checks the list of registered callbacks and triggers the appropriate method on the master's callback object. 

### However, callbacks have several advantages over polling:

* Efficiency: With polling, the client repeatedly sends requests to the server to check for updates. This can be inefficient, especially if there are no updates available. With callbacks, the server sends notifications to the client only when there are updates, saving bandwidth and reducing latency.
* Real-time updates: Callbacks allow for real-time updates, as the server can immediately notify the client when an event occurs. Polling, on the other hand, may introduce a delay between when the event occurs and when the client receives the update.
* Scalability: Callbacks can be more scalable than polling, especially for large-scale systems with many clients. With polling, the server can become overloaded with requests, whereas with callbacks, the server only needs to send notifications to clients that have registered for updates.
* Reduced network traffic: Callbacks can reduce network traffic by avoiding unnecessary requests for updates. With polling, clients may repeatedly request updates even when there are no new events, leading to wasted bandwidth.
* Reduced server load: Callbacks can reduce the load on the server, as the server only needs to send notifications to clients that have registered for updates. With polling, the server may need to handle many requests for updates, even if there are no new events to report.      

## **Conclusion:**
Overall, the system was successfully implemented using Java RMI and synchronization. It provided a reliable and scalable way for workers to retrieve tasks and submit results, and the system could handle multiple workers simultaneously without any interference.                                                                                                                                                                                                                                                                                                                                                    
