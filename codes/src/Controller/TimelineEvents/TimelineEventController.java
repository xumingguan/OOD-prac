package Controller.TimelineEvents;

import Model.*;
import View.*;
import Controller.TimelineEvents.InterestCalculator.*;
import Controller.TimelineEvents.UnplannedEvents.*;
import Controller.TimelineEvents.PlannedEvents.*;
import Controller.TimelineEvents.ProfitCalculator.*;

/*******************************************************************************
 *Handles the general flow of events carried out by the simulation
 ******************************************************************************/
public class TimelineEventController
{

    public TimelineEventController()
    {

    }

    /***************************************************************************
     *Executes the flow of events for the time frame specified for the
     *simulation
     **************************************************************************/
    public void performSimulation(SimulatorData simulatorData, int startYear, int endYear)
    {
        InterestController interestController = new InterestController(simulatorData);
		UnplannedEventController unplannedController = new UnplannedEventController(simulatorData);
        PlannedEventController plannedController = new PlannedEventController(simulatorData);
		ProfitController profitController = new ProfitController(simulatorData);

        //cycle through all the years specified for the simulation
        for(int currentYear = startYear; currentYear < endYear; currentYear++)
        {
			Display.printYear(currentYear);

            //calculate interest
            interestController.calculateInterest();

            //print company balances
			Display.printBankBalances(simulatorData);

            //execute unplanned events
			unplannedController.executeEvents(currentYear);

            //execute planned events
            plannedController.executeEvents(currentYear);

			//calculate profit
			profitController.calculateProfit();
        }
    }
}
