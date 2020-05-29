import java.util.*;
import Controller.FileReaders.*;
import Controller.TimelineEvents.*;
import Model.SimulatorData;
import View.*;

/*******************************************************************************
 * Simulates economic consequences of buy and sell decisions of a company with
 * respect to the companies and businesses which it owns.
 ******************************************************************************/
public class CompanySalesSimulator
{
	/***************************************************************************
	 * Takes in five command line parameters: property file, unplanned events
	 * file, planned events file, a starting year and an end year.
	 *
	 * Also acts as the bridge between the initialisation of the simulator's
	 * state and execution of the simulation.
	 **************************************************************************/
	public static void main(String[] args)
	{
		SimulatorData simulatorData;
		InputFileReader inputReader;
		TimelineEventController eventController;
		int startYear = 0;
		int endYear = 0;

		try
		{
			inputReader = new InputFileReader();
			eventController = new TimelineEventController();

			//check that the command line parameters contain the correct number
			//of inputs required
			if(args.length == 5)
			{
				//obtains the initial state of the simulator
				simulatorData = inputReader.readInputFiles(args[0], args[1], args[2]);

				try
				{
					startYear = Integer.parseInt(args[3]);
					endYear = Integer.parseInt(args[4]);
				}
				catch(NumberFormatException eNF)
				{
					throw new IllegalArgumentException("Error: Invalid year input(s)");
				}

				//performs the execution of the simulation
				eventController.performSimulation(simulatorData, startYear, endYear);
			}
			else
			{
				throw new IllegalArgumentException("Run program as java -jar CompanySalesSimulator.jar [file1.csv] [file2.csv] [file3.csv] [start year] [end year]");
			}
		}
		//catches all error conditions in the simulation and terminates the
		//program
		catch(Exception e)
		{
			Display.printError(e);
		}
		System.exit(0);
	}
}
