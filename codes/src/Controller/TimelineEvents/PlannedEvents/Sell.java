package Controller.TimelineEvents.PlannedEvents;

import Model.Property.*;
import Model.SimulatorData;
import java.util.*;


/*******************************************************************************
 * Performs the selling planned event. Executed whenever the primary company
 * wished to sell a property unit which it owns.
 ******************************************************************************/
public class Sell extends PerformPlannedEvent
{
    public Sell(SimulatorData simData, String inProperty)
    {
        super(simData, inProperty);
    }

    /***************************************************************************
     * Handles the flow of events
     **************************************************************************/
	public void executeEvent()
	{
		Company primaryCompany = null;
		Property propertyToSell = null;
		double propertyValue;
		double primaryBank;
		String propertyToSellsName;
		List<Property> propertyList;

        //obtain the name of the property being sold
		propertyToSellsName = super.getPropertyName();

        //find the primary company from the list of properties
		propertyList = super.getPropertyList();
		primaryCompany = (Company)propertyList.get(0);

        //obtain the property to be sold from the properties which the primary
        //company owns
		propertyToSell = primaryCompany.getProperty(propertyToSellsName);
        if(propertyToSellsName.equals(primaryCompany.getName()))
        {
            throw new IllegalArgumentException("Primary company cannot sell itself");
        }

        //if the property is found amongst the properties owned by the primary
        //company, then proceed with the selling algorithm
		if(propertyToSell != null)
		{
            //obtain the value of the property being sold and add its value
            //to the primary company's ank account
			propertyValue = propertyToSell.getValue();
			primaryBank = primaryCompany.getBankBalance();
			primaryBank += propertyValue;
			primaryCompany.setBankBalance(primaryBank);

            //remove the property from the ownership of the primary company
			primaryCompany.removeProperty(propertyToSell);

            //all sold properties go to an unnamed owner
			propertyToSell.updateOwner("unnamed owner");
			propertyList.add(propertyToSell);
		}
	}
}
