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
        previousTime = -1;
    }

    /**
     *
     * @return
     */
    public ButtonStatus getStatus()
    {
        // update time variables
        // calculate ButtonStatus to return

        return null;
    }

    /* For unit testing purposes */
    public static void main(String[] args)
    {
        //
    }
}
