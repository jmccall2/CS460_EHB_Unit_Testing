package simulator.interfaces;

import java.net.URL;

import javafx.scene.media.AudioClip;
import simulator.simulation.SimGlobals;
import simulator.simulation.engine.Engine;
import simulator.simulation.engine.Message;
import simulator.simulation.engine.MessageHandler;

/**
 * NOTE :: Almost no button state is maintained by this class. That burden
 *         is left to the designers of the EHB software. For example, if
 *         you receive a value of 'true' from the function isDown(), the EHB
 *         software must update itself to reflect that state change.
 *
 * The button interface gives selective control over certain
 * properties of the "activate hand brake" button. Such properties
 * include setting its color and playing a sound to signify a change
 * in state.
 *
 * In addition to this, it exposes a single getter method, isDown(),
 * which reports 'true' if the button has been pressed down and 'false'
 * otherwise.
 */
public class ButtonInterface
{
    private static boolean _isDown;
    private static boolean _isPressed;
    {
        Helper helper = new Helper();
        Engine.getMessagePump().signalInterest(SimGlobals.ACTIVATE_BRAKE, helper);
        Engine.getMessagePump().signalInterest(SimGlobals.DEACTIVATE_BRAKE,helper);
//        kevin added the two bellow
        Engine.getMessagePump().signalInterest(SimGlobals.IS_DOWN,helper);
        Engine.getMessagePump().signalInterest(SimGlobals.IS_UP,helper);
//        This is new inorder to revert later
        Engine.getMessagePump().signalInterest(SimGlobals.IS_PRESSED,helper);

    }

    /**
     * Sets the color of the hand brake button. This change will take effect immediately
     * and become visible to the user.
     * @param c a button type from the enum ButtonColorTypes
     */
    static public void setColor(ButtonColorTypes c)
    {
        Engine.getMessagePump().sendMessage(new Message(SimGlobals.SET_BUTTON_COLOR, c));
    }

    /**
     * Plays a sound to signify some change in state of the EHB system. This sound
     * will be played immediately and exactly one time.
     * @param s sound type from the enum ButtonSoundTypes
     */
    static public void play(ButtonSoundTypes s)
    {
        URL url = ButtonInterface.class.getResource(s.toString());
        AudioClip sound = new AudioClip(url.toExternalForm());
        sound.play(1, 0, 1, 0, 1);
    }

    /**
     * Checks if the button is currently pressed down.
     *
     * @return true if the button is currently pressed down and false otherwise
     */
    public static boolean isDown()
    {
        return _isDown;
    }
    public static boolean isPressed(){return _isPressed;}

    private class Helper implements MessageHandler
    {
        @Override
        public void handleMessage(Message message)
        {
            switch (message.getMessageName())
            {
//                Kevin set this to is_up and is_down rather than activate brake and deactivate brake
                case SimGlobals.IS_DOWN:
                    _isDown = true;
                    _isPressed = false;
                    //System.out.println("is down");
                    break;
                case SimGlobals.IS_UP:
                    _isDown = false;
                    _isPressed = false;
                    //System.out.println("is up");
                    break;
//                    Changed to revert
                case SimGlobals.IS_PRESSED:
                    _isDown = true;
                    _isPressed = true;
                    //System.out.println("pressed");
                    break;
            }
        }
    }

}
