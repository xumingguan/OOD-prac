package Controller.FileReaders;

import Model.SimulatorData;
import Controller.FileReaders.*;
import java.io.*;

/*******************************************************************************
 * Reads in the user specified files which define the intial state and sequence
 * of events to carry out of the simulation.
 ******************************************************************************/
public class InputFileReader
{
	public InputFileReader()
	{

	}

	/*
	 * Reads in the property, event and plan files to establish the initial
	 * state of the simulation. The state is then stored in the SimulatorData
	 * object.
	 */
	public SimulatorData readInputFiles(String propertyFile, String eventFile, String planFile) throws IOException,
		NullPointerException, IllegalArgumentException
	{
		SimulatorData data = new SimulatorData();
		PropertyReader propertyReader = new PropertyReader();
		EventReader eventReader = new EventReader();
		PlanReader planReader = new PlanReader();

		//obtains the initial state of the simulator
		propertyReader.readPropertyFile(propertyFile, data);

		//obtains the sequence of unplanned events which occur annually
		eventReader.readEventFile(eventFile, data);

		//obtains the sequence of events which the user wishes to carry out
		planReader.readPlanFile(planFile, data);

		return data;
	}
}
