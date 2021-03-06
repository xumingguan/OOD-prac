Several patterns were used to create the CompanySalesSimulator. Specifically, they are:

	Strategy
	Template
	Composite
	Factory
	Dependency injection
	Observer
	Iterator
Some of these patterns when combined with polymorphism help generate cleaner and easier to test code.



# **Properties (model)**
All forms of properties inherit from a single common class (Property) which is used to reduce the amount of repeated code and data structures required to store their information. 
The linked list of Properties for example is used as a top level directory for all companies and business units in the simulation. 
Since the linked list of properties makes for a convenient collection, the use of the iterator pattern in the form of for-each loops are used in multiple instances throughout the program.

# **Planned events (controller)**
The plans (planned events) of buy and sell incorporate polymorphism via the use of the PerformPlannedEvent class.

The template pattern has been utilised where the concrete classes (Buy and Sell) have their own implementation of the executeEvent( ) method. 
The strategy method could have been used here instead, but the algorithm between Buy and Sell have some common functionality which is shared, thus using the Template pattern allows the individual implementations of Buy and Sell to utilise that functionality.

Having Buy and Sell utilise the template pattern supports the use of a factory. 
Within the factory a field of type PerformPlannedEvent can be assigned to either a Buy or Sell event based on an imported parameter requesting the required event type.
Within the PlannedEventController class, polymorphism of the planned events is used to generate the appropriate event defined by the list read in from file. 
The factory generated the planned event and which is then executed via the executeEvent( ) method that has been defined in the event.
By utilising this method, the responsibility and code for determining the type of planned event to be executed by the simulation is taken out of the controller class.

# **Unplanned events (controller)**
The events (unplanned events) of an increase or decrease of either revenue, wages or value of a property utilise polymorphism by applying the strategy pattern on to the interface PerformEvent.

There are six implementations of the PerformEvent, all of which inherit the PerformEvent interface. 
The PerformEvent interface enforces the abstract method executeEvent( ) method which will have a different concrete implementation in each of the different unplanned event classes.

The factory and controller for unplanned events takes advantage of polymorphism to apply the event information into the simulation. 
The controller can obtain the appropriate unplanned event object by passing its file information to the factory which generates the object. Similarly, to planned events, this was done to maintain a separation of concerns between classes.

# **Unit Testing**

Testing is primarily achieved by the use of dependency injection. 
The controllers for handling the various forms of planned and unplanned events rely on a factory to provide the necessary objects for the simulation. 
By doing so, the hard-coded dependencies are isolated within the factory which are then injected into the controllers. 
For testing, the controllers could be stubbed wherever they request the factory to provide input.

Due to the dependencies being generated inside the factories, a bulk share of the testing is focussed around the factories. 
Here stubbing is made difficult due to collection of objects being generated in one location. By doing so, however, makes stubbing everywhere else in the program much easier.
The use of polymorphism reduces the degree of coupling between classes. Consequently, the potential quantity of testing is reduced with the reduction of necessary classes required to produce a desired effect.