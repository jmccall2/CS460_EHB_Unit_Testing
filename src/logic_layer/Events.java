package logic_layer;

import simulator.interfaces.GearTypes;
import virtual_layer.*;

/**
 *
 */
public class Events
{
    Alarm _alarm = new Alarm();
    Button _button = new Button();
    Brake _brake = new Brake();
    Motion _motion = new Motion();

    /**
     *
     * @param event
     * @return
     */
    boolean didEventOccur(Event event)
    {
        //

        switch (event)
        {
            case BUTTON_PUSHED:

                return buttonPushed();

            case BUTTON_HELD:

                return buttonHeld();

            case BRAKE_ENGAGED_PARK:
                return brakeEngagedPark(true);

            case BRAKE_DISENGAGED_PARK:

                return brakeEngagedPark(false);

            case BRAKE_FULLY_ENGAGED:

                return brakeFullyEngaged();
            case BRAKE_FULLY_DISENGAGED:

                return brakeFullyDisEngaged();

            case SPEED_EQUAL_TO_ZERO:

                return speedZero();


            case SPEED_GREATER_THAN_ZERO:

                return !speedZero();

            case TRANSMISSION_SHIFT_IN_PARK:

                return shiftIntoPark();

            case TRANSMISSION_SHIFT_OUT_PARK:

                return !shiftIntoPark();

        }

        return false;
    }

    private boolean speedZero()
    {
        if(_motion.getSpeed() == 0)
        {
            return true;
        }
        return false;
    }
    private boolean shiftIntoPark()
    {
        if (_motion.getCurrentGear() == GearTypes.PARK && _motion.getPreviousGear() != GearTypes.PARK)
        {
            return true;
        }
        return false;
    }
    private boolean brakeEngagedPark(boolean engaged)
    {
        if(engaged)
        {
            if (_brake.isEngaged() && _motion.getCurrentGear() == GearTypes.PARK)
            {
                return true;
            }
            return false;
        }
        else
        {
            if (!_brake.isEngaged() && _motion.getCurrentGear() == GearTypes.PARK)
            {
                return true;
            }
            return false;
        }
    }
    private boolean buttonPushed()
    {
        return _button.getStatus() == ButtonStatus.SHORT_PRESS;
    }
    private boolean buttonHeld()
    {
        return _button.getStatus() == ButtonStatus.LONG_PRESS;
    }
    private boolean brakeFullyEngaged()
    {
        return _brake.isEngaged() && _brake.getPressure() == 100.0;
    }
    private boolean brakeFullyDisEngaged()
    {
        return !_brake.isEngaged();
    }
    /* For unit testing purposes */
    public static void main(String[] args)
    {
        //
    }
}
