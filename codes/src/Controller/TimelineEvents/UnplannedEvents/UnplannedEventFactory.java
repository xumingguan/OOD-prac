package Controller.TimelineEvents.UnplannedEvents;


import Model.SimulatorData;
import Model.Event.*;
import Controller.TimelineEvents.UnplannedEvents.*;

/*******************************************************************************
 * Generates PerformEvents based on the imported equivelent obtained from
 * file
 ******************************************************************************/
public class UnplannedEventFactory
{
    SimulatorData sData;

    public UnplannedEventFactory(SimulatorData simData)
    {
        sData = simData;
    }

    /***************************************************************************
     * Generates the PerformEvent.
     **************************************************************************/
    public PerformEvent getPerformEvent(UnplannedEvent currentEvent)
    {
        PerformEvent doEvent = null;
        String event;
        char eventType, eventChange;

        event = currentEvent.getEvent();
        eventType = event.charAt(0);
        eventChange = event.charAt(1);

        switch(eventType)
        {
            case 'W': //generate wage event
                switch(eventChange)
                {
                    case '+'://increase wages
                        doEvent = new IncreaseWages(sData);
                        break;
                    case '-'://decrease wages
                        doEvent = new DecreaseWages(sData);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid wage change event");
                }
                break;
            case 'R'://generate revenue event
                switch(eventChange)
                {
                    case '+'://increase revenue
                        doEvent = new IncreaseRevenue(sData, currentEvent.getPropertyName());
                        break;
                    case '-'://decrease revenue
                        doEvent = new DecreaseRevenue(sData, currentEvent.getPropertyName());
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid revenue change event");
                }
                break;
            case 'V'://value event
                switch(eventChange)
                {
                    case '+'://increase value
                        doEvent = new IncreaseValue(sData, currentEvent.getPropertyName());
                        break;
                    case '-'://decrease value
                        doEvent = new DecreaseValue(sData, currentEvent.getPropertyName());
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid value change event");
                }
                break;
            default://if an unknown event type has been read in from file
                event = "unknown event type";
                break;
        }
        return doEvent;
    }
}
