package Controller.TimelineEvents.PlannedEvents;

import Model.SimulatorData;
import Model.Event.*;
import java.util.*;
import Controller.TimelineEvents.PlannedEvents.*;

/*******************************************************************************
 * Executes the planned events based on the current year.
 ******************************************************************************/
public class PlannedEventController
{
	List<PlannedEvent> eventList;
	PlannedEventFactory factory;

	public PlannedEventController(SimulatorData simData)
	{
		eventList = simData.getPlans();
		factory = new PlannedEventFactory(simData);
	}

	/***************************************************************************
	 * Executes the events for a given year
	 **************************************************************************/
	public void executeEvents(int currentYear)
	{
		String event;
		char eventType;
		char eventChange;
		PerformPlannedEvent doEvent = null;

		//iterates through the list of events to execute for a specified year
		for(PlannedEvent currentEvent : eventList)
		{
			//check if the event is applicable to the current year
			if(currentEvent.getYear() == currentYear)
			{
				//generate the event
				doEvent = factory.getPerformPlannedEvent(currentEvent);
				if(doEvent == null)
				{
					throw new NullPointerException("No event to execute");
				}
				//execute the event
				doEvent.executeEvent();
			}
		}
	}
}
