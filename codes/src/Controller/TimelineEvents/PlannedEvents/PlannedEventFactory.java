package Controller.TimelineEvents.PlannedEvents;


import Model.SimulatorData;
import Model.Event.*;
import Controller.TimelineEvents.PlannedEvents.*;

/*******************************************************************************
 * Generates PerformPlannedEvents based on the imported equivelent obtained from
 * file
 ******************************************************************************/
public class PlannedEventFactory
{
    SimulatorData sData;

    public PlannedEventFactory(SimulatorData simData)
    {
        sData = simData;
    }

    /***************************************************************************
     * Generates the PerformPlannedEvent.
     **************************************************************************/
    public PerformPlannedEvent getPerformPlannedEvent(PlannedEvent currentEvent)
    {
        PerformPlannedEvent doEvent = null;
        char eventType;

        eventType = currentEvent.getEvent();

        switch(eventType)
        {
            case 'B'://generate buy event
                doEvent = new Buy(sData, currentEvent.getPropertyName());
                break;
            case 'S'://generate sell event
                doEvent = new Sell(sData, currentEvent.getPropertyName());
                break;
            default:
                break;
        }
        return doEvent;
    }
}
