package Controller.TimelineEvents.UnplannedEvents;

/*******************************************************************************
 *Interface class for all unplanned events. Enforces the executeEvent abstract
 *method
 ******************************************************************************/
public interface PerformEvent
{
    //reduction events will decrease attribute by 5% if the event is triggered
    //on it
    public static final double REDUCTION_RATE = 0.95;
    //increase events will increase attribute by 5% if the event is triggered
    //on it
    public static final double INCREASE_RATE = 1.05;

    public void executeEvent();
}
