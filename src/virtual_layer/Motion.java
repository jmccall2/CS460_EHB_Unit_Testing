package virtual_layer;

import simulator.interfaces.GearTypes;

import simulator.interfaces.GearInterface;
import simulator.interfaces.SpeedInterface;

/**
 *
 */
public class Motion
{
    GearTypes currentGear;
    GearTypes previousGear;

    /**
     * Constructor for a Motion object.
     */
    public Motion()
    {
        currentGear = null;
        previousGear = null;
    }

    /**
     *
     * @return
     */
    public double getSpeed()
    {
        // call getSpeed() from SpeedInterface
        // return speed in m/s

        return SpeedInterface.getSpeed();
    }

    /**
     *
     * @return
     */
    public GearTypes getCurrentGear()
    {
        // call getGear() from GearInteface
        // update currentGear/previousGear
        // return current gear

        if (currentGear != GearInterface.getGear())
        {
            previousGear = currentGear;
            currentGear = GearInterface.getGear();
        }

        return currentGear;
    }

    /**
     *
     * @return
     */
    public GearTypes getPreviousGear()
    {
        // return previousGear

        return previousGear;
    }


    /* For unit testing purposes */
    public static void main(String[] args)
    {
        //
    }
}
