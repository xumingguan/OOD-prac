package Controller.TimelineEvents.UnplannedEvents;

import Model.SimulatorData;
import Model.Event.*;
import java.util.*;
import Controller.TimelineEvents.UnplannedEvents.*;

/*******************************************************************************
 * Executes the unplanned events based on the current year.
 ******************************************************************************/
public class UnplannedEventController
{
	List<UnplannedEvent> eventList;
	UnplannedEventFactory factory;

	public UnplannedEventController(SimulatorData simData)
	{
		eventList = simData.getEvents();
		factory = new UnplannedEventFactory(simData);
	}

	/***************************************************************************
	 * Executes the events for a given year
	 **************************************************************************/
	public void executeEvents(int currentYear)
	{
		String event;
		char eventType;
		char eventChange;
		PerformEvent doEvent = null;

		//iterate through the list of events to execute for a given year
		for(UnplannedEvent currentEvent : eventList)
		{
			//check if the event is applicable to the current year
			if(currentEvent.getYear() == currentYear)
			{
				//generate the event
				doEvent = factory.getPerformEvent(currentEvent);
				if(doEvent == null)
				{
					throw new NullPointerException("Error: No event to execute");
				}
				//execute the event
				doEvent.executeEvent();
			}
		}
	}
}
