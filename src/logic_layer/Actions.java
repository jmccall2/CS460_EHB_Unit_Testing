package logic_layer;

import simulator.interfaces.ButtonColorTypes;
import virtual_layer.Alarm;
import virtual_layer.Brake;

/**
 *
 */
public class Actions
{

    //virtual interfaces that Actions interacts with
    Alarm alarm = new Alarm();
    //do we instantiate a new brake object here?
    Brake brake = new Brake();

    /**
     *
     * @param action
     */
    void execute(Action action)
    {
        //runs code based on the action enum given
        if(action == Action.ENGAGE_BRAKE_FULLY)
        {
            //apply current brake pressure 100%
        }
        else if(action == Action.DISENGAGE_BRAKE)
        {
            //set brake pressure to 0
        }
        else if(action == Action.INCREASE_BRAKE_FORCE)
        {
            //increase brake force here
        }
        else if(action == Action.SET_BLUE_LED)
        {
            alarm.setColor(ButtonColorTypes.BLUE);
        }
//        else if(action == Action.SET_ORANGE_LED)
//        {
//            alarm.setColor(ButtonColorTypes.ORANGE);
//        }
        else if(action == Action.SET_RED_LED)
        {
            alarm.setColor(ButtonColorTypes.RED);
        }
        else if(action == Action.SOUND_BRAKE_FULLY_ENGAGED)
        {
            alarm.play("engaged");
        }
        else if(action == Action.SOUND_BRAKE_DISENGAGED)
        {
            alarm.play("disengaged");
        }
        else if(action == Action.SOUND_BRAKE_ENGAGING)
        {
            // play sound for disengaging
        }
//        else if(action == Action.ENGAGED_SOUND_OFF)
//        {
//            //pending sound to play
//        }
//        else if(action == Action.DISENGAGED_SOUND_OFF)
//        {
//            //pending sound to play
//        }
//        else if(action == Action.CONTINUOUS_ENGAGED_SOUND_ON)
//        {
//            alarm.play("longA");
//        }
//        else if(action == Action.NON_ACTION)
//        {
//            System.out.println("non-action");
//        }
        else
        {
            System.out.println("INVALID ACTION");
        }
    }

    /* For unit testing purposes */
    public static void main(String[] args)
    {


    }
}
