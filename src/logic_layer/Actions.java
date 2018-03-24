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
    //is passed an instance of brake to reference
    private Brake brake;
    private Alarm alarm;

    public Actions(Brake brake, Alarm alarm)
    {

        this.brake = brake;
        this.alarm = alarm;

    }

    /**
     *
     * @param action
     */
    void execute(Action action)
    {
        System.out.println("*** Actions execute() was called\n    -Brake pressure = " + this.brake.getPressure());
        //action enums ran through as switch case
        switch(action) {
            case INIT_ACTIONS:
                brake.setPressure(0.0);
                alarm.setColor(ButtonColorTypes.BLUE);

            case START_TIMER:

            case STOP_TIMER:

            case ENGAGE_BRAKE_FULLY:
                brake.setPressure(100.0);

            case DISENGAGE_BRAKE:
                brake.setPressure(0.0);

            case ENGAGE_BRAKE_HIGH_FORCE:
                brake.setPressure(75.0);

            case ENGAGE_BRAKE_MED_FORCE:
                brake.setPressure(50.0);

            case ENGAGE_BRAKE_LOW_FORCE:
                brake.setPressure(25.0);

            case INCREASE_BRAKE_FORCE:
                double press = brake.getPressure();
                brake.setPressure(press + 1.0);

            case SET_BLUE_LED:
                alarm.setColor(ButtonColorTypes.BLUE);

            case SET_RED_LED:
                alarm.setColor(ButtonColorTypes.RED);

            case SOUND_BRAKE_DISENGAGED:
                alarm.play("disengaged");

            case SOUND_BRAKE_FULLY_ENGAGED:
                alarm.play("engaged");

            case SOUND_BRAKE_ENGAGING:
                alarm.play("engaged");

            default:
                System.out.println("INVALID ACTION");

        }

    }

    /* For unit testing purposes */
    public static void main(String[] args)
    {


    }
}
