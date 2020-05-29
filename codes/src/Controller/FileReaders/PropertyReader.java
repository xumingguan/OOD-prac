package Controller.FileReaders;

import Model.Property.*;
import Model.SimulatorData;
import java.util.*;
import java.io.*;


/*******************************************************************************
 * Reads in a property file given its file name. It then processes its contents
 * and returns a list of properties with the first entry being the primary
 * company and all subsequent entries being companies and business units with
 * unnamed owners. The property file is assumed to be in a CSV format.
 ******************************************************************************/
public class PropertyReader
{
	public PropertyReader()
	{

	}

	/***************************************************************************
	 * Handles the flow of events from reading in the property file to exporting
	 * the list of properties
	 **************************************************************************/
	public void readPropertyFile(String fileName, SimulatorData simData) throws IOException,
		NullPointerException, IllegalArgumentException
	{
		String line = null;
		String[] parts;
		//stores the sequence of read in companies whose owner is unknown.
		//is appended with a list of unclaimed business units once the property
		//file has been read.
		List<Property> propertyList = new LinkedList<Property>();
		//stores a list of business units who are owned by a company that is
		//either unnamed or has yet to be read in from file.
		List<BusinessUnit> backlog = new LinkedList<BusinessUnit>();

		try
		{
			BufferedReader file = new BufferedReader(new FileReader(fileName));

			//discard header
			line = file.readLine();

			//begin with primaryCompany as null (no primary company exists yet)
			readFileRecursive(file, null, propertyList, backlog, simData);

			//add any left over business units to the property list
			if(backlog != null)
			{
				propertyList.addAll(backlog);
			}
			file.close();
		}
		catch(FileNotFoundException eFNF)
		{
			throw new IOException("Property file not found");
		}
		catch(IOException eIO)
		{
			throw new IOException("Property file could not be read");
		}
		catch(NullPointerException eNP)
		{
			throw new NullPointerException("Error while reading property file");
		}

		//insert the list of properties into the SimulatorData model
		simData.insertPropertyData(propertyList);
	}

	/***************************************************************************
	 * Recursively reads through the property file and decides where to place
	 * the read in company or business unit
	 **************************************************************************/
	private void readFileRecursive(BufferedReader file, Company primaryCompany,
		List<Property> propertyList, List<BusinessUnit> backlog, SimulatorData simData) throws
		IOException, IllegalArgumentException
	{
		String line;
		String[] parts = null;

		try
		{
			line = file.readLine();

			//determines if the end of file ahs been reached. Will break if
			//there is an empty line in the file.
			if(line != null)
			{
				parts = line.split(",");

				//if the property has no owner, it is assigned a default unnamed
				//owner
				if(parts[2].equals(""))
				{
					parts[2] = "unnamed owner";
				}

				//if item read is a company
				if(parts[1].equals("C"))
				{
					addNewCompany(parts, primaryCompany, propertyList, backlog, simData);
				}
				//if the item is a business unit
				else if(parts[1].equals("B"))
				{
					addNewBusinessUnit(parts, primaryCompany, propertyList, backlog, simData);
				}
				//otherwise item has an invalid type
				else
				{
					throw new IllegalArgumentException("Unknown property type");
				}

				//sets the primaryCompany if at least one company has already
				// been read into the state.
				if(!propertyList.isEmpty())
				{
					primaryCompany = (Company)propertyList.get(0);
				}

				//continue reading file
				readFileRecursive(file, primaryCompany, propertyList, backlog, simData);
			}
		}
		catch(NumberFormatException eNF)
		{
			throw new IllegalArgumentException("Incorrect property file entry format");
		}
	}

	/***************************************************************************
	 * Adds a new company to its nominated owner in addition to adding any
	 * business units it may own in the backlog
	 **************************************************************************/
	private void addNewCompany(String[] parts, Company primaryCompany,
		List<Property> propertyList, List<BusinessUnit> backlog, SimulatorData simData) throws
		NumberFormatException
	{
		Company newCompany = new Company(parts[0], parts[2], Double.parseDouble(parts[3]), true);

		//add company to interest event oberservers list
		simData.addInterestObserver(newCompany);
		simData.addValueObserver(newCompany);

		//check if a primary company already exists, if not then this company will become the primary company
		if(primaryCompany == null)
		{
			primaryCompany = newCompany;

			//if the backlog of business units is not empty, then check if any belong to this company
			checkBacklog(primaryCompany, backlog);
			propertyList.add(primaryCompany);
		}
		else
		{
			//check if any units in backlog belong to this company
			checkBacklog(newCompany, backlog);

			//find the owner of this company and assign this to it
			Company propertyOwner = primaryCompany.findOwner(parts[2]);
			if(propertyOwner == null)
			{
				propertyList.add(newCompany);
			}
			else
			{
				propertyOwner.addCompany(newCompany.getName(), newCompany);
			}
		}
	}

	/***************************************************************************
	 *checks the backlog of business units owned by a company and adds them to
	 * the company if they exist.
	 **************************************************************************/
	private void checkBacklog(Company company, List<BusinessUnit> backlog)
	{
		if(backlog != null);
		{
			for(BusinessUnit bU : backlog)
			{
				if((bU.getOwner()).equals(company.getName()))
				{
					//assigns the business unit to the company which owns it
					company.addBusinessUnit(bU.getName(), bU);

					//removes the business unit from the backlog to prevent
					//duplication
					backlog.remove(bU);
				}
			}
		}
	}

	/***************************************************************************
	 * Creates a new business unit and then adds it to the company which owns
	 * it.
	 **************************************************************************/
	private void addNewBusinessUnit(String[] parts, Company primaryCompany,
		List<Property> propertyList, List<BusinessUnit> backlog, SimulatorData simData) throws
		NumberFormatException
	{
		//name, type, worth, owner, sellable, revenue, wages
		BusinessUnit newBUnit;
		Company propertyOwner = null;

		//creates a new business unit with the read in paramenters.
		//may throw a NumberFormatException if the read in parameters do not
		//convert cleanly to real  numbers.
		newBUnit = new BusinessUnit(parts[0], parts[2], Double.parseDouble(parts[3]), true, Double.parseDouble(parts[4]), Double.parseDouble(parts[5]));

		//add business unit to observers for wage changes
		simData.addBusinessObserver(newBUnit);
		simData.addValueObserver(newBUnit);

		if(primaryCompany != null)
		{
			//iterates through the list of properties to find the owner of
			//the business unit.
			for(Property c : propertyList)
			{
				//checks if the owner of the business has been found yet
				if(propertyOwner == null)
				{
					//find owner of the unit
					propertyOwner = ((Company)c).findOwner(newBUnit.getOwner());
				}
			}

			//add unit to the backlog if its owner cannot be found
			if(propertyOwner == null)
			{
				//add unit to the backlog until its owner is created
				backlog.add(newBUnit);
			}
			//add unit to its owner
			else
			{
				propertyOwner.addBusinessUnit(newBUnit.getName(), newBUnit);
			}
		}
		else
		{
			//add unit to the backlog until its owner is created
			backlog.add(newBUnit);
		}

	}
}
