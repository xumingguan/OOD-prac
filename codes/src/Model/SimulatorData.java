package Model;

import java.util.*;
import Model.Property.*;
import Model.Event.*;

/*******************************************************************************
 *Contains and tracks the state of the simulation
 ******************************************************************************/
public class SimulatorData
{
	//state data
	List<Property> propertyData;
	List<UnplannedEvent> eventData;
	List<PlannedEvent> planData;

	//observer lists
	List<Company> interestObservers;
	List<BusinessUnit> businessObervers;//used for revenue and wage events
	List<Property> valueObservers;

	public SimulatorData()
	{
		propertyData = null;
		eventData = null;
		planData = null;
		interestObservers = new LinkedList<Company>();
		businessObervers = new LinkedList<BusinessUnit>();
		valueObservers= new LinkedList<Property>();
	}

	public List<Property> getProperties()
	{
		return propertyData;
	}

	public List<UnplannedEvent> getEvents()
	{
		return eventData;
	}

	public List<PlannedEvent> getPlans()
	{
		return planData;
	}

	public List<Company> getInterestObservers()
	{
		return interestObservers;
	}

	public List<BusinessUnit> getBusinessObservers()
	{
		return businessObervers;
	}

	public List<Property> getValueObservers()
	{
		return valueObservers;
	}

	public void insertPropertyData(List<Property> properties)
	{
		propertyData = properties;
	}

	public void insertEventData(List<UnplannedEvent> events)
	{
		eventData = events;
	}

	public void insertPlanData(List<PlannedEvent> plans)
	{
		planData = plans;
	}

	public void addInterestObserver(Company newObserver)
	{
		interestObservers.add(newObserver);
	}

	public void addBusinessObserver(BusinessUnit newObserver)
	{
		businessObervers.add(newObserver);
	}

	public void addValueObserver(Property newObserver)
	{
		valueObservers.add(newObserver);
	}
}
