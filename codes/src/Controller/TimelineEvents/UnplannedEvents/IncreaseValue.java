package Controller.TimelineEvents.UnplannedEvents;

import java.util.*;
import Model.SimulatorData;
import Model.Property.*;

/*******************************************************************************
 *Represents the increase in value event which may occur during the simulation
 ******************************************************************************/
public class IncreaseValue implements PerformEvent
{
    List<Property> propertyList;
    String propertyName;

    public IncreaseValue(SimulatorData simData, String inName)
    {
        propertyList = simData.getProperties();
        propertyName = inName;
    }

    /***************************************************************************
     *Executes the increase in value event
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
				newValue *= INCREASE_RATE;
				property.setValue(newValue);
            }
        }
    }
}
