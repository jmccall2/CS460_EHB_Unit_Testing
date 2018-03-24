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
//    boolean didEventOccur(Event event)
//    {
//        //
//
//        switch (event)
//        {
//            case BUTTON_PUSHED:
//
//                return buttonPushed();
//
//            case BUTTON_HELD:
//
//                return buttonHeld();
//
//            case BRAKE_ENGAGED_PARK:
//                return brakeEngagedPark(true);
//
//            case BRAKE_DISENGAGED_PARK:
//
//                return brakeEngagedPark(false);
//
//            case BRAKE_FULLY_ENGAGED:
//
//                return brakeFullyEngaged();
//            case BRAKE_FULLY_DISENGAGED:
//
//                return brakeFullyDisEngaged();
//
//            case SPEED_EQUAL_TO_ZERO:
//
//                return speedZero();
//
//
//            case SPEED_GREATER_THAN_ZERO:
//
//                return !speedZero();
//
//            case TRANSMISSION_SHIFT_IN_PARK:
//
//                return shiftIntoPark();
//
//            case TRANSMISSION_SHIFT_OUT_PARK:
//
//                return !shiftIntoPark();
//
//        }
//
//        return false;
//    }

    boolean didEventOccur(Event event)
    {

        switch (event)
        {


            case BRAKE_FORCE_FULLY_ENGAGED:

                return brakeFullyEngaged();

            case TIMER_TICK:

            case BUTTON_PRESSED_SPEED_STOP:
                return buttonPressedSpeed(0);

            case BUTTON_PRESSED_SPEED_LOW:
                return buttonPressedSpeed(1);


            case BUTTON_PRESSED_SPEED_MED:

                return buttonPressedSpeed(2);

            case BUTTON_PRESSED_SPEED_HIGH:

                return buttonPressedSpeed(3);

            case BUTTON_PRESSED:

                return buttonPressed();

            case INIT_EVENT:




        }

        return false;
    }

    private boolean buttonPressedSpeed(int mode)
    {
      boolean pressed = _button.getStatus() == ButtonStatus.LONG_PRESS;
      double speed = _motion.getSpeed();
        switch (mode)
        {
            case 0:
                return pressed && speed  == 0.0;
            case 1:
                return pressed && speed <= 40.0;

            case 2:
                return  pressed && speed > 40.0 && speed < 80.0;

            case 3:
                return  pressed && speed >= 80.0;


        }

        return false;
    }
    private boolean brakeFullyEngaged()
    {
        return _brake.isEngaged() && _brake.getPressure() == 100.0;
    }

    private boolean buttonPressed(){return _button.getStatus() == ButtonStatus.LONG_PRESS || _button.getStatus() == ButtonStatus.SHORT_PRESS;}

    /* For unit testing purposes */
    public static void main(String[] args)
    {
        //
    }
}
