# CSC1101 Assignment - Description and Submission

## Problem specification:
A farm is open 24 hours a day, 365 days a year. The farm has multiple fields: pigs, cows, sheep, llamas, and chickens.

Time in the farm is measured in ticks. There are 1000 ticks in a day. Every 100 ticks (on average) a delivery is made of
10 animals, with a random number of animals for each of the above categories (totaling 10) e.g., 4 for pigs, 2 for cows,
1 for sheep, 2 for llamas, etc.

When a delivery of animals arrives, they are put into an enclosure, where a farmer who works in the farm can put (stock)
the animals into their respective fields (assuming the farmer is not already busy). Only one person (farmer) can take
animals from the enclosure at a time, and once taken cannot return them to the enclosure. Once they are finished another
farmer can then take animals from the enclosure. Each farmer can move up to 10 animals at once, which could be a mix of
any 10 animals e.g. 5 pigs, 3 sheep and 2 chickens. Only one farmer can be stocking a particular field at any one time
(e.g. pigs). Once a farmer is stocking a field, any other arriving farmer(s) wishing to stock that field will need to
wait. If a buyer arrives while a field is being stocked, they will need to wait for the farmer to finish stocking that
field. A farmer may partly stock a field e.g. they only stock 3 of the 5 animals they are moving in that field (because
it is now full). It takes a buyer 1 tick to take an animal from a field.

Every 10 ticks (on average) a buyer will buy an animal from one of the fields (randomly). If the field is empty, the
buyer will wait until an animal for that field becomes available. This means there may be times where a particular field
does not contain any animals, and the buyer will wait until an animal for that field is available.

It takes a farmer 10 ticks to walk from where the deliveries arrive (the enclosure) to a particular field (e.g., to the
field of cows), and 1 tick extra for every animal they are moving to that field. Additionally, for every animal they put
in the field, it takes 1 tick. In this example, it would take 20 ticks for a farmer to move 10 animals to the field of
cows, and another 10 ticks to stock that field with all 10 animals. If they return to the delivery area (the enclosure)
where animals arrive from any field, it will take 10 ticks, and 1 tick extra for every animal they are still moving
(i.e. animals they have not yet put into a field).

If a farmer is moving animals to stock multiple fields, it will take them 10 ticks to walk from one field to another
field to begin stocking that field plus 1 tick for every remaining animal (to be stocked) that they are moving. For
example, they may stock some cows first, and then move to another field. When they are finished, they return to the
delivery area (the enclosure) to see if there are more animals to be stocked, if not, they wait. The journey from any
field back to the delivery area where animals arrive takes 10 ticks. You can assume that it takes 0 ticks for a farmer
to take animals from the enclosure.

Note: Given that 100 animals arrive (on average) per day to the farm, and 100 animals will be bought (on average) per
day by buyers, there will be times where some fields may not contain any animals. In these instances, buyers will need
to wait for the animal to be available for that field.


## Task:
Design a software system in Java to simulate the concurrent operation of the farm. Assume that animals are the
resources, and the different activities are conducted in threads e.g., farmers, buyers, etc.

**Example of output:**
```
...
<Tick_count> <Thread_ID> Deposit_of_animals : pigs=7 cows=2
<Tick_count> <Thread_ID> buyer=7 collected_from_field=cows waited_ticks=50
<Tick_count> <Thread_ID> farmer=1 collected_animals waited_ticks=110: pigs=4 cows=3
<Tick_count> <Thread_ID> farmer=1 began_stocking_field : pigs=4
<Tick_count> <Thread_ID> farmer=3 finished_stocking_field : cows=3
<Tick_count> <Thread_ID> farmer=1 finished_stocking_field : pigs=3
<Tick_count> <Thread_ID> farmer=1 moved_to_field=clothing : chickens=3
...
```

This output should be printed to the terminal window.

Ensure that it is clear and easy to follow. Interesting/noteworthy excerpts of this should be discussed in your video
(see below).

## Grading
Before moving from the minimal to good, or good to excellent, ensure that you have all functionality working for the
previous level (minimal / good) e.g. features from the excellent project category will not be considered unless the
specification for good is fully implemented.

### A minimal version of the project:
**To pass the assignment, it could be created following these assumptions:**
- A delivery of 10 animals is made with a probability of .01 every tick. 
- Capacity is unlimited within each field
- 1 farm farmer (threaded) / multiple buyers (threaded)
- 5 fields e.g., pigs, cows, sheep, llamas, and chickens.
- Real-time reporting (as above)
- A variable defined in your code (that I can adjust) that determines how long in milliseconds a tick is (e.g. 100ms)

### A good project:
**The specifications above are a minimum requirement to pass this assignment. For good work, I expect:**
- More than 1 farmer 
- Prioritising stocking animals in certain fields based on the number of buyers waiting/empty fields (to minimise buyer
wait time)
- Fields with a limited capacity e.g., each field may only hold 10 animals
- Configurable parameters (e.g., number of farmers, number of fields, control of probabilities of buyer purchase
behaviour, etc.) 
- Breaks taken by farmers (e.g., every 200-300 ticks a farmer will take a break of 150 ticks).

### An excellent project:
**The project would need to contain higher forms of creativity that go beyond a good project e.g.,**
- Analysis of tradeoffs between different statistics like average waiting for buyers vs amount of time each farmer spent
working for different approaches to the problem - (comparative simulation analysis with evidence)
- ...

## Some other points:
- If a delivery of 10 animals arrives every 100 ticks, the probability that a delivery of animals will arrive in 1 tick
is 1/100. You can simulate this using one of Java’s random functions e.g.:
```
Random randgen = new Random(seed); //  set a seed to replicate behaviour
…
your_wait_ticks ( 2 * randgen.nextDouble() * 100 )*
```
*The idea here is that there will be differing times between animal deliveries, but the average wait time will converge
to 100 (on average).*

- You can assume each field of the store begins with 5 animals e.g., chickens=5, pigs=5, cows=5, ....
- Remember: A key purpose of this assignment is for individuals to demonstrate they understand issues related to concurrency, and how they can be addressed. Use this as an opportunity to showcase what you have learned.
- Code should be commented.
- Suitable annotations should be used e.g., @GuardedBy, @Override, etc 

## FAQs
**What is the mapping between time versus ticks?**
The idea of using ticks ( e.g., instead of seconds, minutes, etc), is that the mapping can be adjusted.
For example, if you want to wait 10 ticks, you could Thread.sleep(10 * TICK_TIME_SIZE), where TICK_TIME_SIZE = .1
seconds. By changing TICK_TIME_SIZE, you can change the speed the program runs at, and hence the speed the output is
produced at. The minimum TICK_TIME_SIZE I will use is 50ms.

**If a delivery arrives every 100 ticks (on average), and the probability that an animal arrives on any single tick is
1/100, does this mean that it is possible two deliveries could be made consecutively one after the other?**
Yes.

**Should the farmer(s) / buyer(s) be threads?**
Yes.

**Can there be multiple buyers on the farm or at a field at the same time?**
Yes.

**Will a buyer only buy one animal and then leave?**
Yes.