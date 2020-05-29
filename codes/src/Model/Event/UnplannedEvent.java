package Model.Event;

/*******************************************************************************
 *Represents an unplanned event which could be an increase or decrease variant
 *of revenue, wage or value of a property
 ******************************************************************************/
public class UnplannedEvent
{
    private int year;
    private String property;
    private String event;

    public UnplannedEvent(int inYear, String inEvent, String inProperty)
    {
        year = inYear;
        property = inProperty;
        setEvent(inEvent);
    }

    public int getYear()
    {
        return year;
    }

    public String getPropertyName()
    {
        return property;
    }

    public String getEvent()
    {
        return event;
    }

    public void setEvent(String inEvent)
    {
        if(validateEvent(inEvent))
        {
            event = inEvent;
        }
        else
        {
            throw new IllegalArgumentException("Invalid unplanned event in file");
        }
    }

    private boolean validateEvent(String inEvent)
    {
        boolean isValid = false;
        char eventType = inEvent.charAt(0);
        char eventChange = inEvent.charAt(1);

        //checks if the event contains only two characters
        if(inEvent.length() == 2 )
        {
            //checks if the event is either revenue("R"), value("V") or wages("W")
            if(eventType == 'R' || eventType == 'V' || eventType == 'W')
            {
                //checks if the event either
                if(eventChange == '-' || eventChange == '+')
                {
                    isValid = true;
                }
            }

        }
        return isValid;
    }
}
