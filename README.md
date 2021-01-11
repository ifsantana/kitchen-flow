# kitchen-flow - the CSS challenge

## How to run application

blablabla

## How to run tests

blablabla

## Problem Description

Challenge Prompt

Create a real-time system that emulates the fulfillment of delivery orders for a kitchen. The kitchen should receive 2 delivery orders per second. 
The kitchen should instantly cook the order upon receiving it, and then place the order on the best-available shelf (see ​Shelves section​) to await pick up by a courier.
Upon receiving an order, the system should dispatch a courier to pick up and deliver that ​specific​ order. The courier should arrive ​randomly​ between 2-6 seconds later. 
The courier should instantly pick up the order upon arrival. Once an order is picked up, the courier should instantly deliver it.

You can use any programming language, framework, and IDE you’d like; ​however​, we ​strongly discourage​ the use of microservices, kafka, REST APIs, RPCs, DBs, etc due to time constraints.

Orders

You can download a JSON file of food order structures from ​http://bit.ly/css_dto_orders​. Orders must be parsed from the file and ingested into your system at a rate of 2 orders per second. You are expected to make your order ingestion rate configurable, so that we can test your system’s behavior with different ingestion rates. Each order should only be ingested once; when all orders have been consumed, your system should terminate.

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
If the overflow shelf is full, an existing order of your choosing on the overflow should be moved to an allowable shelf with room​.​ 
If no such move is possible, a random order from the overflow shelf should be discarded as waste (and will not be available for a courier pickup). 

The following table details the kitchen’s shelves:

Name             Allowable Temperatures    Capacity

Hot shelf        hot                       10

Cold shelf       cold                      10

Frozen shelf     frozen                    10

Overflow shelf   any temperature           15

Shelf Life

Orders have an inherent value that will deteriorate over time, based on the order’s ​shelfLife​ and decayRate​ fields. 
Orders that have reached a value of zero are considered wasted: they should never be delivered and should be removed from the shelf. 
Please display the current order value when displaying an order in your system’s output.

value = (s​helfLife​ - ​decayRate *​​​ orderAge * shelfDecayModifier)​ / shelfLife
   
Important: shelfDecayModifier​ is 1​ ​forsingle-temperature shelves and ​2​ for the overflow shelf.


## Problem Solution

Producer/Consumer Problem

blablabla

## Design, Architecture and Application Structure

Domain-Driven Design

Design Patterns

SOLID Principles

## Application Layers

* Application

Layer responsible for the starts the application. 

* Configs

Layer responsible for bind application configs/properties dynamically.

* Consumers

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

* Repositories

Here we have all interfaces/contracts to be implemented for access/persistence of the appropriate data sources.

Example: To read the data file I used the OrderRepository interface, implemented in the repositories layer by the OrderServiceImpl.class.

* Services

Here we have all interfaces/contracts to be implemented for processing and business rules.

Example: For processing data extracted from the file, I used the OrderService interface, which implements the processOrders() method by the OrderServiceImpl.class.

* Util

Common utilities layer.






