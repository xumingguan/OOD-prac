package Controller.TimelineEvents.ProfitCalculator;

import Model.Property.*;
import Model.SimulatorData;
import java.util.*;

/*******************************************************************************
 * Carries out the profit calculations on the properties in the simulation
 ******************************************************************************/
public class ProfitController
{
    private List<Property> propertyList;

    public ProfitController(SimulatorData simData)
    {
        propertyList = simData.getProperties();
    }

    /***************************************************************************
     * Recursively calculates the profit on every company in the simulation
     **************************************************************************/
    public void calculateProfit()
    {
        for(Property property : propertyList)
        {
			//skips any business units as they will not be generating any
            //profit for a company
			if(property instanceof Company)
			{
	            calculateProfitRecursive((Company)property);
			}
        }
    }

	/***************************************************************************
	 * Recursively calculates the profit earned by a company by determining the
	 * individual profits of the business units it owns and the profit passed
	 * up by the businss units owned by companies which it owns.
	 **************************************************************************/
	private double calculateProfitRecursive(Company inCompany)
	{
		double profitEarned = 0.0;
		Map<String, BusinessUnit> ownedBusinesses;
		Map<String, Company> ownedCompanies;

		ownedBusinesses = inCompany.getBusinesses();
		ownedCompanies = inCompany.getCompanies();

        //obtain the profit obtained from all the businesses owned by this
        //company
		for(BusinessUnit bUnit : ownedBusinesses.values())
		{
			profitEarned += (bUnit.getRevenue() - bUnit.getWages());
		}

        //calculates the profit obtained from companies owned by this company
		for(Company ownedCompany : ownedCompanies.values())
		{
			profitEarned += calculateProfitRecursive(ownedCompany);
		}

        //updates the bank account of the company with the profit earned
		updateBankBalance(inCompany, profitEarned);

		return profitEarned;
	}

    /***************************************************************************
     *Adds the profit earned by a company into its bank account. If the profit
     *earned is positive, then the company is only able to obtain half of the
     *profits. If the profit is negative, then the company must take on all
     *the deficit made by the properties owned by the company.
     **************************************************************************/
	private void updateBankBalance(Company inCompany, double profit)
	{
		double balance;

		if(profit > 0)
		{
			//companies can only earn half of what their business units generate
			profit *= 0.5;
		}//else companies incur all of the debt that their business units generate

		//update property's bank balance with profit earned
		balance = inCompany.getBankBalance();
		balance += profit;
		inCompany.setBankBalance(balance);
	}

}
