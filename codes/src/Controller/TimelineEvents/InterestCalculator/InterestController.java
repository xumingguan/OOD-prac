package Controller.TimelineEvents.InterestCalculator;

import Model.Property.Company;
import Model.SimulatorData;
import java.util.*;

/*
 * Takes in and maintains a reference to a list of companies to calculate
 * the interest of their bank accounts.
 */
public class InterestController
{
    //interest rate is set at 5%
    private static final double INTEREST_RATE = 0.05;
    private List<Company> companyObservers;

    public InterestController(SimulatorData simData)
    {
        companyObservers = simData.getInterestObservers();
    }

    //performs the interest calculation for each company in the simulation
    public void calculateInterest()
    {
        double interestEarned;
        double balance;

        for(Company company : companyObservers)
        {
            //obtains the amount of money the company currently has in their
            //bank account
            balance = company.getBankBalance();

            //apply the interest rate to the bank balance
            interestEarned = balance * INTEREST_RATE;

            //add the interest to the bank account
            balance += interestEarned;
            company.setBankBalance(balance);
        }
    }
}
