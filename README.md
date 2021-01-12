# kitchen-flow - the CSS challenge

## How to run application

* InteliJ Idea

1) Menu -> Run -> Edit Configurations...

2) Click in "+" button and Add New Configuration with type "Application"

3) Set "Name": run

4) Set "Main Class": br.com.italo.santana.challenge.prompt.Application

5) Set "Use class module": challenge.prompt

6) Use JRE 8 or 10

7) Click on "Apply" to save changes

8) Select "run" configuration and click on "run" button

* CLI

mvn package
mvn install
mvn springboot:run

## How to run tests

* CLI

mvn test

## Problem Description

Challenge Prompt

Create a real-time system that emulates the fulfillment of delivery orders for a kitchen. The kitchen should receive 2 delivery orders per second. 
The kitchen should instantly cook the order upon receiving it, and then place the order on the best-available shelf (see Shelves section) to await pick up by a courier.
Upon receiving an order, the system should dispatch a courier to pick up and deliver that specific order. The courier should arrive randomly between 2-6 seconds later. 
The courier should instantly pick up the order upon arrival. Once an order is picked up, the courier should instantly deliver it.

You can use any programming language, framework, and IDE you’d like; however, we strongly discourage the use of microservices, kafka, REST APIs, RPCs, DBs, etc due to time constraints.

Orders

You can download a JSON file of food order structures from http://bit.ly/css_dto_orders. Orders must be parsed from the file and ingested into your system at a rate of 2 orders per second. You are expected to make your order ingestion rate configurable, so that we can test your system’s behavior with different ingestion rates. Each order should only be ingested once; when all orders have been consumed, your system should terminate.

Example:

"order.json"

{

    {
        "id": "a8cfcb76-7f24-4420-a5ba-d46dd77bdffd",
        "name": "Banana Split",
        "temp": "frozen", ​// Preferred shelf storage temperature
        "shelfLife": 20, /​/ Shelf wait max duration (seconds)
        "decayRate": 0.63 /​/ Value deterioration modifier 
    }
}

Shelves

The kitchen pick-up area has multiple shelves to hold cooked orders at different temperatures. Each order should be placed on a shelf that matches the order’s temperature. 
If that shelf is full, an order can be placed on the overflow shelf. 
If the overflow shelf is full, an existing order of your choosing on the overflow should be moved to an allowable shelf with room. 
If no such move is possible, a random order from the overflow shelf should be discarded as waste (and will not be available for a courier pickup). 

The following table details the kitchen’s shelves:

Name             Allowable Temperatures    Capacity

Hot shelf        hot                       10

Cold shelf       cold                      10

Frozen shelf     frozen                    10

Overflow shelf   any temperature           15

Shelf Life

Orders have an inherent value that will deteriorate over time, based on the order’s shelfLife and decayRate fields. 
Orders that have reached a value of zero are considered wasted: they should never be delivered and should be removed from the shelf. 
Please display the current order value when displaying an order in your system’s output.

value = (shelfLife - decayRate * orderAge * shelfDecayModifier) / shelfLife
   
Important: shelfDecayModifier is 1 for single-temperature shelves and 2 for the overflow shelf.


## Problem / Solution

### Problem

* Producer/Consumer Problem [1]

### Solution

* Producer-Consumer Pattern [2]

Producer consumer pattern is everywhere in real life and depicts coordination and collaboration. Like one person is preparing food (Producer) while the other one is serving food (Consumer), both will use a shared table for putting food plates and taking food plates. 

The producer which is the person preparing food will wait if the table is full and Consumer (Person who is serving food) will wait if the table is empty. the table is a shared object here. On Java library, the Executor framework itself implement Producer Consumer design pattern be separating responsibility of addition and execution of the task.

It's indeed a useful design pattern and used most commonly while writing multi-threaded or concurrent code. here
is few of its benefit:

1) Producer Consumer Pattern simple development. you can Code Producer and Consumer independently and Concurrently, they just need to know shared objects.

2) Producer doesn't need to know about who is consumer or how many consumers are there. Same is true with Consumer.

3) Producer and Consumer can work with different speed. There is no risk of Consumer consuming half-baked item.
In fact by monitoring consumer speed one can introduce more consumer for better utilization.

4) Separating producer and Consumer functionality result in more clean, readable and manageable code.

* Producer-Consumer Problem in Multi-threading

Producer-Consumer Problem is also a popular java interview question where interviewer ask to implement producer consumer design pattern so that Producer should wait if Queue or bucket is full and Consumer should wait if queue orbucket is empty. This problem can be implemented or solved by different ways in Java, classical way is using wait and notify method to communicate between Producer and Consumer thread and blocking each of them on individual condition like full queue and empty queue. 

With the introduction of BlockingQueue Data Structure in Java 5 Its now much simpler because BlockingQueue provides this control implicitly by introducing blocking methods put() and take(). 

Now you don't require to use wait and notify to communicate between Producer and Consumer. 

In this challenge I've use BlockingQueue offer() methods will block if Queue is full in case of Bounded Queue and pool() will block if Queue is empty.

* How and why I chose to handle moving orders to and from the overflow shelf

Each order should be placed on a shelf that matches the order’s temperature.
If that shelf is full, an order can be placed on the overflow shelf.
If no such move is possible, a random order from the overflow shelf should be discarded as waste (will not be available for a courier pickup)
If the overflow shelf is full, an existing order of your choosing on the overflow should be moved to an allowable shelf with room and the current order must be placed on the overflow shelf replacing the previously discarded order.

## Design, Architecture and Application Structure

* Domain-Driven Design

All the layers of the application were modeled on the best practices imposed by Eric Evans, with Domain layer being the heart of the solution. 

I also tried to use ubiquitous language as much as possible.

* Design Patterns

Factory, Generic Builder, Repository, Producer-Consumer...

* SOLID Principles

* ForkJoin Thread Pool [3]

The ForkJoinPool is the heart of the ForkJoin framework. 

It is an implementation of the ExecutorService that manages worker threads and provides us with tools to get information about the thread pool state and performance.

Worker threads can execute only one task at a time, but the ForkJoinPool doesn’t create a separate thread for every single subtask. 

Instead, each thread in the pool has its own double-ended queue (or deque, pronounced deck) which stores tasks.

This architecture is vital for balancing the thread’s workload with the help of the work-stealing algorithm.

In this project I've use the ForkJoin thread Pool to manage the orders parallelism threshold in a custom thread pool just to processing a give number of orders in a give time space.

## Application Layers

* Application

Layer responsible for starts the application. 

* Configs

Layer responsible for bind application configs/properties dynamically from application.properties file.

* Consumers

Layer responsible to abstract Consumers of the Producer-Consumer Pattern.

* Domain

The domain layer is the most complex of the application, since it had abstraction of all the entities raised in the proposed problem.

Here are all entities in the application domain, their methods and behaviors.

* Enums

Enumeration classes layer.

* Factories

Layer responsible for factory pattern implementations.

* Interfaces

Abstraction contracts layer. 

* Producers

Layer responsible to abstract Producers of the Producer-Consumer Pattern.

* Repositories

Here we have all interfaces/contracts to be implemented for access/persistence of the appropriate data sources.

Example: To read the data file I used the OrderRepository interface, implemented in the repositories layer by the OrderServiceImpl.class.

* Services

Here we have all interfaces/contracts to be implemented for processing and business rules.

Example: For processing data extracted from the file, I used the OrderService interface, which implements the processOrders() method by the OrderServiceImpl.class.

* Util

Common utilities layer.

* Unit Tests

Due to time constraints due to the high demand in my current position, in the development of unit tests I've prioritized only the main flows.


## References:

1) Producer Consumer Problem - https://en.wikipedia.org/wiki/Producer–consumer_problem

2) Producer Consumer Design Pattern with Blocking Queue Example in Java - https://javarevisited.blogspot.com/2012/02/producer-consumer-design-pattern-with.html

3) Guide to the Fork/Join Framework in Java - https://www.baeldung.com/java-fork-join

