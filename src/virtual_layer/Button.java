package virtual_layer;

import simulator.interfaces.ButtonInterface;

/**
 *
 */


/**
 *
 */
public class Button
{

    private double deltaT;
    private double currentTime;
    private double previousTime;

    /**
     * Constructor for a Button object.
     */
    public Button()
    {
        deltaT = -1;
        currentTime = -1;
        previousTime = System.currentTimeMillis();
    }

    /**
     *
     * @return
     */
    public ButtonStatus getStatus()
    {
        // update time variables
        // calculate ButtonStatus to return
        if (ButtonInterface.isDown() == false)
            deltaT = 0;
        else
        {
            currentTime = System.currentTimeMillis();
            deltaT = (currentTime - previousTime)/1000;
        }
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
