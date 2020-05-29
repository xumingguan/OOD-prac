package Controller.TimelineEvents.PlannedEvents;

import Model.Property.*;
import Model.SimulatorData;
import java.util.*;

/*******************************************************************************
 * Represents an event instance. Can be instantiated as either the buy or sell
 * class.
 ******************************************************************************/
public abstract class PerformPlannedEvent
{
	String propertysName;
	List<Property> propertyList;

	public PerformPlannedEvent(SimulatorData simData, String inProperty)
	{
		propertyList = simData.getProperties();
		propertysName = inProperty;
	}

	public String getPropertyName()
	{
		return propertysName;
	}

	public List<Property> getPropertyList()
	{
		return propertyList;
	}

	/***************************************************************************
	 *Searches through the property list to find a property given its name
	 **************************************************************************/
	public Property findProperty(String name)
	{
		Property propertyToFind = null;

		//if the name of the company is unnamed owner, then the company being
		//sought is not known in the simulation
		if(name.equals("unnamed owner"))
		{
			propertyToFind = new Company("unnamed owner", "unnamed owner", 0.0, false);
		}
		else
		{
			//for every property in the list, recurse through to find the
			//property being sought
			for(Property p : propertyList)
			{
				//if the property being sought has not yet been found
				if(propertyToFind == null)
				{
					//property is found
					if((p.getName()).equals(name))
					{
						propertyToFind = p;
					}
					//continue recursising through companies owned by this
					//property
					else if(p instanceof Company)
					{
						propertyToFind = findPropertyRecursive(name, (Company)p, propertyToFind);
					}
				}
			}
		}

		//if the property does not exist in the simulataion then terminate
		if(propertyToFind == null)
		{
			throw new NullPointerException("Cannot find property: " + name);
		}

		return propertyToFind;
	}

	/***************************************************************************
	 *recurses through a nominated company to search through all of the
	 *business units and companies it owns.
	 **************************************************************************/
	private Property findPropertyRecursive(String name, Company inCompany, Property propertyToFind)
    {
        Map<String, Company> companies = inCompany.getCompanies();
        Map<String, BusinessUnit> businesses = inCompany.getBusinesses();

		if(propertyToFind == null)
		{
			//checks through owned comapanies for the property name
	        if(companies.containsKey(name))
	        {
	            propertyToFind = companies.get(name);
	        }
			//if property cannot be found in owned companies, then check businesses
	        else if(businesses.containsKey(name))
	        {
	            propertyToFind = businesses.get(name);
	        }
			//if it cannot be found in the owned properties of the company then
			//check all companies owned by this one.
	        else
	        {
	            for(Company c : companies.values())
	            {
	                propertyToFind = findPropertyRecursive(name, c, propertyToFind);
	            }
	        }
		}
		return propertyToFind;
    }

    public abstract void executeEvent();
}
