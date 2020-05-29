package Model.Event;

/*******************************************************************************
 *Represents a planned event whuch can either be a buy or sell
 ******************************************************************************/
public class PlannedEvent
{
    private int year;//year the event occurs
    private char event;//the type of event (buy or sell)
    private String property;//property the event affects

    public PlannedEvent(int inYear, char inEvent, String inProperty)
    {
        year = inYear;
        property = inProperty;
        setEvent(inEvent);
    }

    public int getYear()
    {
        return year;
    }

    public char getEvent()
    {
        return event;
    }

    public String getPropertyName()
    {
        return property;
    }

    public void setEvent(char inEvent)
    {
        if(validateEvent(inEvent))
        {
            event = inEvent;
        }
        else
        {
            throw new IllegalArgumentException("Invalid planned event");
        }
    }

    private boolean validateEvent(char inEvent)
    {
        boolean isValid = false;

        if(inEvent == 'B' || inEvent == 'S')
        {
            isValid = true;
        }
        return isValid;
    }
}
