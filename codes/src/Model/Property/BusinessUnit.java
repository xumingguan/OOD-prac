package Model.Property;

import Model.Property.Property;

/*******************************************************************************
 *Represents a business unit which generates profit for a company.
 ******************************************************************************/
public class BusinessUnit extends Property
{
	private double revenue;
	private double wages;

	public BusinessUnit(String inName, String inOwner, double inWorth, boolean inSellable, double inRevenue, double inWages)
	{
		super(inName, inOwner, inWorth, inSellable);
		revenue = inRevenue;
		wages = inWages;
	}

	public double getRevenue()
	{
		return revenue;
	}

	public double getWages()
	{
		return wages;
	}

	public void setRevenue(double inRevenue)
	{
		revenue = inRevenue;
	}

	public void setWages(double inWages)
	{
		wages = inWages;
	}
}
