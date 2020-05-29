package Controller.FileReaders;

import Model.Event.*;
import Model.SimulatorData;
import java.util.*;
import java.io.*;

/*******************************************************************************
 * Reads the plan file which contains the sequence of planned events which
 * can occur during the simulation.
 * The reader only takes the string values read from the file, performs the
 * necessary convertions and finally saves them in a linked list of planned
 * events.
 *
 * The list of unplanned vents are then stored in the simulatorData model.
 ******************************************************************************/
public class PlanReader
{
    public PlanReader()
    {

    }

    /***************************************************************************
     * Performs the reading of the events file by taking in its file pointer.
     * Assumes the file is a comma separated values formatted. Otherwise an
     * exception is thrown and the progam terminates.
     *
     * Creates a linked list of unplanned events which is then finally stored
     * in the SimulatorData object.
     **************************************************************************/
    public void readPlanFile(String fileName, SimulatorData simData) throws IOException,
		NullPointerException, IllegalArgumentException
    {
        String line = null;
		String[] parts;
        //stores the sequence of planned events in chronological order
		List<PlannedEvent> planList = new LinkedList<PlannedEvent>();
        PlannedEvent eventTemp;
        int prevEventYear = 0;
        int nextEventYear;
        char eventType;

        try
		{
			BufferedReader file = new BufferedReader(new FileReader(fileName));

			//discard header
			line = file.readLine();

            line = file.readLine();

            do
            {
                //split the line using comma(",") as the regex and while
                //including any trailing spaces
                parts = line.split(",", -1);
                nextEventYear = Integer.parseInt(parts[0]);
                eventType = parts[1].charAt(0);
                eventTemp = new PlannedEvent(nextEventYear, eventType, parts[2]);

                //checks to see if the newly created event maintains chronological order
                if(validateEventYear(prevEventYear, nextEventYear))
                {
                    planList.add(eventTemp);
                    prevEventYear = nextEventYear;
                }
                else
                {
                    throw new IllegalArgumentException("File entries are not in chronological order");
                }
                line = file.readLine();
            }while(line != null); //continue until end of file or end of a properly formatted file

            file.close();
		}
		catch(FileNotFoundException eFNF)
		{
			throw new IOException("Event file not found");
		}
		catch(IOException eIO)
		{
			throw new IOException("Event file could not be read");
		}
        catch(NumberFormatException eNF)
        {
            throw new IllegalArgumentException("Incorrect event file entry format");
        }
		catch(NullPointerException eNP)
		{
			throw new NullPointerException("Error while reading event file");
		}

        //add list of planned events to the SimulatorData model
		simData.insertPlanData(planList);
    }

    /***************************************************************************
     * checks for chronological order of read in events. Chronological order is
     * maintained when the event year is equal to or greater than the previously
     * read in event year.
     **************************************************************************/
    private boolean validateEventYear(int prevEventYear, int nextEventYear)
    {
        boolean isValid = false;

        if(prevEventYear <= nextEventYear)
        {
            isValid = true;
        }
        return isValid;
    }
}
