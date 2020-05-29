package Controller.TimelineEvents.UnplannedEvents;

import java.util.*;
import Model.SimulatorData;
import Model.Property.*;

/*******************************************************************************
 *Represents the decrease in value event which may occur during the simulation
 ******************************************************************************/
public class DecreaseValue implements PerformEvent
{
    //contains the list of observers for the values event
    List<Property> propertyList;
    String propertyName;

    public DecreaseValue(SimulatorData simData, String inName)
    {
        propertyList = simData.getValueObservers();
        propertyName = inName;
    }

    /***************************************************************************
     *Executes the reduction in value event
     **************************************************************************/
    public void executeEvent()
    {
        String name;
		double newValue;

        //searches through the list of properties
        for(Property property : propertyList)
        {
            name = property.getName();
            if(name.equals(propertyName))
            {
                newValue = property.getValue();
				newValue *= REDUCTION_RATE;
                property.setValue(newValue);
            }
        }
    }
}
