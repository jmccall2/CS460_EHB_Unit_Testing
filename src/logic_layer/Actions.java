package logic_layer;

import simulator.interfaces.ButtonColorTypes;
import virtual_layer.Alarm;
import virtual_layer.Brake;

/**
 * Provides the functionality to perform an action through its execute() method.
 * Acts as an interface between the Controller on the control logic layer and the virtual components
 * that contain setters on the virtual layer.
 */
public class Actions
{

    // References to the virtual components
    private Brake brake;
    private Alarm alarm;

    /**
     * Constructor
     *
     * @param brake virtual component initialized by parent
     * @param alarm virtual component initialized by parent
     */
    public Actions(Brake brake, Alarm alarm)
    {
        this.brake = brake;
        this.alarm = alarm;
    }

    /**
     * Given a value from the Action enum and the current state, we perform said action by interfacing with our
     * virtual components, which in turn interact with the drivers
     *
     * @param action the action to execute
     */
    void execute(Action action)
    {
        switch(action) {
            case INIT_ACTIONS:
                brake.setPressure(0.0);
                alarm.setColor(ButtonColorTypes.BLUE);
                break;

            case DISENGAGE_BRAKE:
                brake.setPressure(0.0);
                break;

            case ENGAGE_BRAKE_FULLY:
                brake.setPressure(100.0);
                break;

            case ENGAGE_BRAKE_HIGH_FORCE:
                brake.setPressure(60.0);
                break;

            case ENGAGE_BRAKE_MED_FORCE:
                brake.setPressure(50.0);
                break;

            case ENGAGE_BRAKE_LOW_FORCE:
                brake.setPressure(25.0);
                break;

            case INCREASE_BRAKE_FORCE:
                double press = brake.getPressure();
                brake.setPressure(press + 0.5);
                break;

            case SET_BLUE_LED:
                alarm.setColor(ButtonColorTypes.BLUE);
                break;

            case SET_RED_LED:
                alarm.setColor(ButtonColorTypes.RED);
                break;

            case SOUND_BRAKE_DISENGAGED:
                alarm.play("disengaging");
                break;

            case SOUND_BRAKE_FULLY_ENGAGED:
                alarm.play("engaging");
                break;

            case SOUND_BRAKE_ENGAGING:
                alarm.play("engaging");
                break;

            default:
                System.out.println("INVALID ACTION = "+action);

        }

    }

    /* For unit testing purposes */
    public static void main(String[] args)
    {


    }
}
