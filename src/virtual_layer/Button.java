package virtual_layer;

import simulator.interfaces.ButtonInterface;

/**
 * Virtualizes the ButtonInterface, allowing the logic layer classes to
 * determine if the button has been pressed for a long time, a short time,
 * or is not pressed. This state is kept here, and uses the ButtonStatus enum.
 */
public class Button
{
    private double deltaT; // how long the button has been pressed
    private double currentTime;
    private double previousTime;

    /**
     * Constructor for a Button object.
     */
    public Button()
    {
        deltaT = -1;
        previousTime = -1;
        currentTime = System.currentTimeMillis();
    }

    /**
     * Updates the time instance variables, calculates deltaT between them,
     * and returns a ButtonStatus accordingly.
     *
     * @return Either LONG_PRESS, SHORT_PRESS, or NOT_PRESSED.
     */
    public ButtonStatus getStatus()
    {
        // update time variables
        previousTime = currentTime;
        currentTime = System.currentTimeMillis();

        // calculate deltaT
        if (!ButtonInterface.isDown()) // button not pressed
            deltaT = 0;
        else
            deltaT = (currentTime - previousTime) / 1000;

        // return status, based on deltaT
        if (deltaT == 0)
            return ButtonStatus.NOT_PRESSED;
        else if (0 < deltaT && deltaT < 2 )
            return ButtonStatus.SHORT_PRESS;
        else
            return ButtonStatus.LONG_PRESS;
    }

    /* For unit testing purposes */
    public static void main(String[] args)
    {
        //
    }
}
