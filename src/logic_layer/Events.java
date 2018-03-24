package logic_layer;

import virtual_layer.*;

/**
 *
 */
public class Events
{
    Button _button;
    Brake _brake;
    Motion _motion;

    public Events(Motion motion, Brake brake, Button button)
    {
        _button = button;
        _motion = motion;
        _brake = brake;
    }
    /**
     *
     * @param event
     * @return
     */
    boolean didEventOccur(Event event)
    {
        System.out.println(" **** Entered didEventOccur()");

        switch (event)
        {
            case BRAKE_FORCE_FULLY_ENGAGED:
                return brakeFullyEngaged();

            case NON_EVENT:
                return true;

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
                return true;

            default:
                System.out.println("Events didEventOccur(): Invalid event!");

        }

        return false;
    }

    private boolean buttonPressedSpeed(int mode)
    {
      boolean pressed = _button.getStatus() == ButtonStatus.LONG_PRESS || _button.getStatus() == ButtonStatus.SHORT_PRESS;
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
