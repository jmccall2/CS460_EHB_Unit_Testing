package virtual_layer;

import simulator.interfaces.ButtonInterface;

/**
 * Virtualizes the ButtonInterface, allowing the logic layer classes to
 * determine if the button has been pressed for a long time, a short time,
 * or is not pressed. This state is kept here, and uses the ButtonStatus enum.
 */
public class Button
{

    /**
     * Constructor for a Button object.
     */
    public Button()
    {

    }

    /**
     * Check the status of the button interface
     *
     * @return PRESSED or NOT_PRESSED.
     */
    public ButtonStatus getStatus()
    {
        if (!ButtonInterface.isDown()) return ButtonStatus.NOT_PRESSED;
        return ButtonStatus.PRESSED;

    }

    /* For unit testing purposes */
    public static void main(String[] args)
    {
        //
    }
}
