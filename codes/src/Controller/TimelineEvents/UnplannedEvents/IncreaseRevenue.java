package Controller.TimelineEvents.UnplannedEvents;

import java.util.*;
import Model.SimulatorData;
import Model.Property.BusinessUnit;

/*******************************************************************************
 *Represents the increase in revenue event which may occur during the simulation
 ******************************************************************************/
public class IncreaseRevenue implements PerformEvent
{
    List<BusinessUnit> bUnitList;
    String propertyName;

    public IncreaseRevenue(SimulatorData simData, String inName)
    {
        bUnitList = simData.getBusinessObservers();
        propertyName = inName;
    }

    /***************************************************************************
     * Executes the increase in revenue event
     **************************************************************************/
    public void executeEvent()
    {
        double revenue;

        //iterates through the list of observers to find a specific unit
        //affected by the reduction in revenue event
        for(BusinessUnit bU : bUnitList)
        {
            //determines if this business unit is the business unit affected by
            //the event
            if((bU.getName()).equals(propertyName))
            {
                revenue = bU.getRevenue();
                revenue *= INCREASE_RATE;
                bU.setRevenue(revenue);
            }
        }
    }
}
