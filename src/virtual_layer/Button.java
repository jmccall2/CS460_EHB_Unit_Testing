package virtual_layer;

import simulator.interfaces.ButtonInterface;

/**
 * Virtualizes the ButtonInterface, allowing the logic layer classes to
 * determine if the button has been pressed for a long time, a short time,
 * or is not pressed. This state is kept here, and uses the ButtonStatus enum.
 */
public class Button
{
    private double deltaT;       // how long the button has been pressed
    private double currentTime;  // current system time, when getStatus() is called
    private double previousTime; // last system time when getStatus() was called

    /**
     * Constructor for a Button object.
     */
    public Button()
    {
        deltaT = 0;
        previousTime = -1;
        currentTime = -1;
    }

    /**
     * Updates the time instance variables, calculates deltaT between them,
     * and returns a ButtonStatus accordingly.
     *
     * @return PRESSED or NOT_PRESSED.
     */
    public ButtonStatus getStatus()
    {
//        currentTime = System.currentTimeMillis();
//
//        // calculate deltaT
//        if (!ButtonInterface.isDown())
//            deltaT = 0;
//        else
//            deltaT += (currentTime - previousTime) / 1000;
//
//        previousTime = currentTime;
//
//        // return status, based on deltaT
//        if (deltaT == 0) return ButtonStatus.NOT_PRESSED;
//        else return ButtonStatus.PRESSED;
        if (!ButtonInterface.isDown()) return ButtonStatus.NOT_PRESSED;
        return ButtonStatus.PRESSED;

    }

    /* For unit testing purposes */
    public static void main(String[] args)
    {
        //
    }
}
