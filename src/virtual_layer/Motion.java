package virtual_layer;

import simulator.interfaces.GearTypes;

import simulator.interfaces.GearInterface;
import simulator.interfaces.SpeedInterface;

/**
 * Allows retrieval of the current speed, current gear, and previous gear
 * of the vehicle.
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
     * Calls getSpeed() in SpeedInterface, to return the current speed.
     *
     * @return Current speed of the vehicle in m/s, as a double.
     */
    public double getSpeed()
    {
        return SpeedInterface.getSpeed();
    }

    /**
     * Calls getGear() in SpeedInterface, to return the current gear.
     * Updates the instance variables previousGear and currentGear accordingly.
     *
     * @return The current gear of the vehicle.
     */
    public GearTypes getCurrentGear()
    {
        GearTypes temp = GearInterface.getGear();

        if (currentGear != temp)
        {
            previousGear = currentGear;
            currentGear = temp;
        }

        return currentGear;
    }

    /**
     * @return The last gear the vehicle was in.
     */
    public GearTypes getPreviousGear()
    {
        return previousGear;
    }


    /* For unit testing purposes */
    public static void main(String[] args)
    {
        //
    }
}
