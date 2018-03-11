package virtual_layer;

import simulator.interfaces.BrakeInterface;

/**
 *
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
     *
     * @param pressure
     */
    public void setPressure(double pressure)
    {
        // check bounds for pressure argument
        // update currentPressure
        // call setPressure in BrakeInterface
    }

    /**
     *
     * @return
     */
    public double getPressure()
    {
        // return currentPressure

        return -1;
    }

    /**
     *
     * @return
     */
    public boolean isEngaged()
    {
        // call isEngaged() from BrakeInterface
        // (I don't think this method exists; need to add it, or keep state in some other way)

        return false;
    }

    /* For unit testing purposes */
    public static void main(String[] args)
    {
        //
    }
}
