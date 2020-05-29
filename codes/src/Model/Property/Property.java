package Model.Property;

import java.util.*;;

public abstract class Property
{
	private String name;
	private boolean sellable;
	private String owner = null;
	private double value;

	public Property(String inName, String inOwner, double inWorth, boolean inSellable)
	{
		name = inName;
		sellable = inSellable;
		owner = inOwner;
		value = inWorth;
	}

	public String getName()
	{
		return name;
	}

	public String getOwner()
	{
		return owner;
	}

	public double getValue()
	{
		return value;
	}

	private boolean isSellable()
	{
		return sellable;
	}

	public void updateOwner(String newOwner)
	{
		if(!owner.equals(newOwner))
		{
			owner = newOwner;
		}
		else
		{
			throw new IllegalArgumentException(owner + " already owns this property");
		}
	}

	public void setValue(double inValue)
	{
		value = inValue;
	}
}
