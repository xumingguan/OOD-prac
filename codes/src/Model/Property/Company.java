package Model.Property;

import Model.Property.*;
import java.util.*;

/*******************************************************************************
 *Represents a company which can own subsidary companies and/or business units
 ******************************************************************************/
public class Company extends Property
{
	private Map<String, BusinessUnit> ownedBusinessUnits;
	private Map<String, Company> ownedCompanies;
	private BankAccount bankAccount;

	public Company(String inName, String inOwner, double inWorth, boolean inSellable)
	{
		super(inName, inOwner, inWorth, inSellable);
		ownedBusinessUnits = new HashMap<String, BusinessUnit>();
		ownedCompanies = new HashMap<String, Company>();
		bankAccount = new BankAccount(inName + "\'s account", inName, false);
	}

	//accesses its bank account to obtain the liquidatable monetary capitcal
	public double getBankBalance()
	{
		return bankAccount.getValue();
	}

	public void setBankBalance(double value)
	{
		bankAccount.setValue(value);
	}

	public void addBusinessUnit(String inBName, BusinessUnit inBUnit)
	{
		ownedBusinessUnits.put(inBName, inBUnit);
	}

	public void addCompany(String inCName, Company inCompany)
	{
		ownedCompanies.put(inCName, inCompany);
	}

	public Company findOwner(String ownerKey)
	{
		Company owner = null;
		owner = findOwnerRecursive(ownerKey, owner);
		return owner;
	}

	//recursively find the owner from all owned sub-Company
	public Company findOwnerRecursive(String ownerKey, Company owner)
	{
		if(owner == null)
		{
			//owner is this company
			if(super.getName().equals(ownerKey))
			{
				owner = this;
			}
			//owner is one of the companies owned by this company
			else if(ownedCompanies.containsKey(ownerKey))
			{
				owner = ownedCompanies.get(ownerKey);
			}
			//owner may be a company owned by owned companies
			else
			{
				for(Company c : ownedCompanies.values())
				{
					owner = c.findOwner(ownerKey);
				}
			}
		}
		return owner;
	}

	public Property getProperty(String name)
	{
		Property property = null;

		if(ownedCompanies.containsKey(name))
		{
			property = ownedCompanies.get(name);
		}
		else if(ownedBusinessUnits.containsKey(name))
		{
			property = ownedBusinessUnits.get(name);
		}
		return property;
	}

	public void removeProperty(Property inProperty)
	{
		String key = inProperty.getName();

		if(inProperty instanceof Company)
		{
			ownedCompanies.remove(key);
		}
		else
		{
			ownedBusinessUnits.remove(key);
		}
	}

	public Map<String,BusinessUnit> getBusinesses()
	{
		return ownedBusinessUnits;
	}

	public Map<String,Company> getCompanies()
	{
		return ownedCompanies;
	}
}
