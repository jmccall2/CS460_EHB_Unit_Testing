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

    /**
     *
     * @param action
     */
    void execute(Action action)
    {
        //runs  code based on the action enum given
        if(action == Action.ENGAGE_BRAKE)
        {
            alarm.setColor(ButtonColorTypes.GREEN);
        }
        else if(action == Action.DISENGAGE_BRAKE)
        {
            alarm.setColor(ButtonColorTypes.RED);
        }
        else if(action == Action.ENGAGE_SOUND)
        {
            alarm.play("engaged");
        }
        else if(action == Action.DISENGAGE_SOUND)
        {
            alarm.play("disengaged");
        }
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
