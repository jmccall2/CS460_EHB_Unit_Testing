package virtual_layer;

import simulator.interfaces.ButtonInterface;
import simulator.interfaces.ButtonColorTypes;
import simulator.interfaces.ButtonSoundTypes;

/**
 *
 */
public class Alarm
{
    /**
     * Constructor for an Alarm object.
     */
    public Alarm()
    {
        //
    }

    /**
     *
     * @param color
     */
    public void setColor(ButtonColorTypes color)
    {
        // call setColor() in ButtonInterface
        if (color == ButtonColorTypes.BLUE)
        {
            ButtonInterface.setColor(ButtonColorTypes.BLUE);
        }
        else if (color == ButtonColorTypes.RED)
        {
            ButtonInterface.setColor(ButtonColorTypes.RED);
        }
        else if (color == ButtonColorTypes.GREEN)
        {
            ButtonInterface.setColor(ButtonColorTypes.GREEN);
        }
        else if (color == ButtonColorTypes.ORANGE)
        {
            ButtonInterface.setColor(ButtonColorTypes.ORANGE);
        }
        else if (color == ButtonColorTypes.YELLOW)
        {
            ButtonInterface.setColor(ButtonColorTypes.YELLOW);
        }
        else if (color == ButtonColorTypes.LIGHTBLUE)
        {
            ButtonInterface.setColor(ButtonColorTypes.LIGHTBLUE);
        }
        else
        {
            //defaulting to a color as not to break code
            ButtonInterface.setColor(ButtonColorTypes.BLUE);
            System.out.println("BAD BUTTON COLOR");
        }
    }

    /**
     *
     * @param currentState
     */
    public void play(String currentState)
    {
        // check value of currentState
        // call play() in ButtonInterface with appropriate argument

        if(currentState == "engaged")
        {
            ButtonInterface.play(ButtonSoundTypes.ENGAGED);
        }
        else if (currentState == "disengaged")
        {
            ButtonInterface.play(ButtonSoundTypes.DISENGAGED);
        }
        else if (currentState == "longA")
        {
            ButtonInterface.play(ButtonSoundTypes.LONG_BEEP_A);
        }
        else if (currentState == "longB")
        {
            ButtonInterface.play(ButtonSoundTypes.LONG_BEEP_B);
        }
        else if (currentState == "longC")
        {
            ButtonInterface.play(ButtonSoundTypes.LONG_BEEP_C);
        }
        else if (currentState == "shortA")
        {
            ButtonInterface.play(ButtonSoundTypes.SHORT_BEEP_A);
        }
        else if (currentState == "shortB")
        {
            ButtonInterface.play(ButtonSoundTypes.SHORT_BEEP_B);
        }
        else
        {
            //defaulting to this as not to break code
            ButtonInterface.play(ButtonSoundTypes.SHORT_BEEP_B);
            System.out.println("INVLAID ALARM");
        }


    }

    /* For unit testing purposes */
    public static void main(String[] args)
    {
        //

    }
}
