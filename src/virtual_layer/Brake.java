package virtual_layer;

import simulator.interfaces.BrakeInterface;

import java.text.BreakIterator;

/**
 * Virtualizes the BrakeInterface; exposes methods to get pressure,
 * set pressure, and ascertain if the brake is currently engaged.
 */
public class Brake
{
    private double currentPressure;

    /**
     * Constructor for a Brake object.
     */
    public Brake()
    {
        currentPressure = -1;
    }

    /**
     * Checks bounds for the pressure argument, possibly converting it
     * to 0.0 <= pressure <= 100.0, updates the instance variable
     * currentPressure, then calls setPressure in BrakeInterface.
     *
     * @param pressure A double between 0.0 and 100.0.
     */
    public void setPressure(double pressure)
    {
        if (pressure < 0.0)
            currentPressure = 0.0;
        else if (pressure > 100.0)
            currentPressure = 100.0;
        else
            currentPressure = pressure;

        BrakeInterface.setPressure(currentPressure);
    }

    /**
     * @return The current pressure being applied.
     */
    public double getPressure()
    {
        return currentPressure;
    }

    /**
     * @return True if the brake is currently engaged; false otherwise.
     */
    public boolean isEngaged()
    {
        // Had to add the method isEngaged() to BrakeInterface, as it didn't exist.

        return BrakeInterface.isEngaged();
    }

    /* For unit testing purposes */
    public static void main(String[] args)
    {
        //
    }
}
