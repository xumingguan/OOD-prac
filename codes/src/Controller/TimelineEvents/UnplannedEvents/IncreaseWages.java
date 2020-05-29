package Controller.TimelineEvents.UnplannedEvents;

import java.util.*;
import Model.SimulatorData;
import Model.Property.BusinessUnit;

/*******************************************************************************
 *Represents the increase in wage event which may occur during the simulation
 ******************************************************************************/
public class IncreaseWages implements PerformEvent
{
    List<BusinessUnit> bUnitList;

    public IncreaseWages(SimulatorData simData)
    {
        bUnitList = simData.getBusinessObservers();
    }

    /***************************************************************************
     *Executes the increase in wage event
     **************************************************************************/
    public void executeEvent()
    {
        double wages;

        //reduction in wages affects all business units
        for(BusinessUnit bU : bUnitList)
        {
            wages = bU.getWages();
            wages *= INCREASE_RATE;
            bU.setWages(wages);
        }
    }
}
