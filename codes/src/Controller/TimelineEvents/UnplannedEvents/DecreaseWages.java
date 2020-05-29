package Controller.TimelineEvents.UnplannedEvents;

import java.util.*;
import Model.SimulatorData;
import Model.Property.BusinessUnit;

/*******************************************************************************
 *Represents the decrease in wage event which may occur during the simulation
 ******************************************************************************/
public class DecreaseWages implements PerformEvent
{
    List<BusinessUnit> bUnitList;

    public DecreaseWages(SimulatorData simData)
    {
        bUnitList = simData.getBusinessObservers();
    }

    /***************************************************************************
     *Executes the reduction in wage event
     **************************************************************************/
    public void executeEvent()
    {
        double wages;

        //reduction in wages affects all business units
        for(BusinessUnit bU : bUnitList)
        {
            wages = bU.getWages();
            wages *= REDUCTION_RATE;
            bU.setWages(wages);
        }
    }
}
