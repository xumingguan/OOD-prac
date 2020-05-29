package Controller.TimelineEvents.PlannedEvents;

import Model.Property.*;
import Model.SimulatorData;
import java.util.*;


/*******************************************************************************
 * Performs the buying planned event. Executed whenever the primary company
 * wished to purchase a property unit.
 ******************************************************************************/
public class Buy extends PerformPlannedEvent
{
    public Buy(SimulatorData simData, String inProperty)
    {
        super(simData, inProperty);
    }

    /***************************************************************************
     * Handles the flow of the buying event
     **************************************************************************/
    public void executeEvent()
    {
        Company primaryCompany = null;
        Company sellingCompany = null;
        Property propertyToBuy = null;
        //default forces the simulation to output obvious and errornous values
        //when something goes wrong
        double valueOfProperty = Double.MAX_VALUE;
        double primaryBank;
        double sellingBank;
		String propertyToBuysName;
		List<Property> propertyList;

        //obtain the name of the property the primary company wishes to buy
		propertyToBuysName = super.getPropertyName();
        //find the property to buy in the property list
        propertyToBuy = findProperty(propertyToBuysName);

        //obtain the list of properties in the simulation
		propertyList = super.getPropertyList();

        //obtain a reference to the primary company
        primaryCompany = (Company)propertyList.get(0);
        //obtain a reference to the primary comapany's bank account
        primaryBank = primaryCompany.getBankBalance();

        //check to see if the primary company is attempting to buy itself
        if(propertyToBuy.getName().equals(primaryCompany.getName()))
        {
            throw new IllegalArgumentException("Primary company cannot buy itself");
        }

        //obtains the value of the property being bought and reduce the bank
        //acount of the primary comapny b its value
        valueOfProperty = propertyToBuy.getValue();
        primaryBank -= valueOfProperty;
        primaryCompany.setBankBalance(primaryBank);

        //find the owner of the property being bought. If one is found, the
        //value of the property is added to the owner.
        sellingCompany = (Company)(findProperty(propertyToBuy.getOwner()));
        if(sellingCompany != null)
        {
            sellingBank = sellingCompany.getBankBalance();
            sellingBank += valueOfProperty;
            sellingCompany.setBankBalance(sellingBank);
			sellingCompany.removeProperty(propertyToBuy);
			propertyToBuy.updateOwner(primaryCompany.getName());
        }
        else
        {
            throw new IllegalArgumentException("Property being bought has no owner");
        }

        //add the property to the primary company based on its type
        if(propertyToBuy instanceof Company)
        {
            primaryCompany.addCompany(propertyToBuysName, (Company)propertyToBuy);
        }
        else//property must be a business unit
        {
            primaryCompany.addBusinessUnit(propertyToBuysName, (BusinessUnit)propertyToBuy);
        }
    }
}
