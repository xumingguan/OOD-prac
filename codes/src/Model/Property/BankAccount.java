package Model.Property;

import Model.Property.Property;

/*******************************************************************************
 *Tracks the liquidatable monetary capital of a company.
 ******************************************************************************/
public class BankAccount extends Property
{
	public BankAccount(String inName, String inOwner, boolean inSellable)
	{
		//bank accounts begin with no money
		super(inName, inOwner, 0.0, inSellable);
	}
}
