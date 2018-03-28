package logic_layer;

import virtual_layer.Brake;
import virtual_layer.Button;
import virtual_layer.ButtonStatus;
import virtual_layer.Motion;

/**
 * Provides the functionality to check if an event has occurred through its didEventOccur() method.
 * Acts as an interface between the Controller on the control logic layer and the virtual components
 * that contain getters on the virtual layer.
 */
public class Events
{
    // References to the virtual components
    private Button _button;
    private Brake _brake;
    private Motion _motion;

    // Used to determine if EHB button was released
    private boolean lastStatus;

    /**
     * Constructor
     *
     * @param motion virtual component initialized by parent
     * @param brake virtual component initialized by parent
     * @param button virtual component initialized by parent
     */
    public Events(Motion motion, Brake brake, Button button)
    {
        _button = button;
        _motion = motion;
        _brake = brake;

        lastStatus = false;
    }
    /**
     * Given a value from the Event enum we check if the event has occurred by querying the virtual components,
     * which in turn query the drivers
     *
     * @param event the event we are checking
     * @return true if event occurred
     */
    boolean didEventOccur(Event event)
    {
        switch (event)
        {
            case BRAKE_FORCE_FULLY_ENGAGED:
                return brakeFullyEngaged();

            case BUTTON_PRESSED_SPEED_STOP:
                return buttonPressedSpeed(0);

            case BUTTON_PRESSED_SPEED_LOW:
                return buttonPressedSpeed(1);

            case BUTTON_PRESSED_SPEED_MED:
                return buttonPressedSpeed(2);

            case BUTTON_PRESSED_SPEED_HIGH:
                return buttonPressedSpeed(3);

            case BUTTON_RELEASED:
                return buttonReleased();

            case NON_EVENT:
                return true;

            default:
                System.out.println("Events didEventOccur(): Invalid event!");
        }

        return false;
    }

    /**
     * Checks to see if the button was pressed to engage the brake with the given braking mode
     * Helper method
     *
     * @param mode the initial braking mode (hi/med/lo) or "stopped"
     * @return true if button was pressed under the specified braking mode
     */
    private boolean buttonPressedSpeed(int mode)
    {
        boolean pressed = _button.getStatus() == ButtonStatus.PRESSED;
        double speed = _motion.getSpeed()/0.448;
        switch (mode)
        {
            case 0:
                return pressed && speed  == 0.0;
            case 1:
                return pressed && speed <= 40;
            case 2:
                return  pressed && speed > 40 && speed < 80;
            case 3:
                return  pressed && speed >= 80;
        }

        return false;
    }

    /**
     * Helper method
     *
     * @return true if the brake is engaged and the pressure is at 100%
     */
    private boolean brakeFullyEngaged()
    {
        return _brake.isEngaged() && _brake.getPressure() == 100.0;
    }

    /**
     *  Checks to see if the brake was released
     *  Helper method
     *
     *  @return true when the user clicks the brake button (i.e. releases it) while the break is engaging/engaged
     */
    private boolean buttonReleased()
    {
        boolean down = _button.getStatus() == ButtonStatus.PRESSED;

        if(lastStatus != down)
        {
            lastStatus = down;
            return true;
        }
        return false;
    }

    /* For unit testing purposes */
    public static void main(String[] args)
    {
        //
    }
}
