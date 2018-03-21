package logic_layer;

import java.util.*;

/**
 *
 */
public class Rules
{
    /**
     *
     * @param currentState
     * @return
     */
    private static HashMap<Event,State> whatEvents(State currentState)
    {

//        switch(currentState)
//        {
//            // Moving states
//            case MOVING_ENGAGING:
//                return null;
//            case MOVING_ENGAGED:
//                return null;
//            case MOVING_DISENGAGING:
//                return null;
//            case MOVING_DISENGAGED:
//                return null;
//
//            // Parked states
//            case PARKED_ENGAGED:
//                return null;
//            case PARKED_DISENGAGED:
//                return null;
//
//            // Stopped states
//            case STOPPED_ENGAGED:
//                return null;
//            case STOPPED_DISENGAGED:
//                return null;
//        }

        return null;
    }

    /**
     *
     * @param currentEvent
     * @param currentState
     * @return
     */
    private static List<Action> whatActions(Event currentEvent, State currentState)
    {
        switch(currentState)
        {
            // Moving states
            case MOVING_ENGAGING:
                if (currentEvent == Event.BUTTON_HELD)
                    return Arrays.asList(Action.DISENGAGE_BRAKE, Action.SET_RED_LED);
                else if(currentEvent == Event.BRAKE_FULLY_ENGAGED)
                    return Arrays.asList(Action.SINGLE_ENGAGED_SOUND_ON, Action.SET_RED_LED);
                else if(currentEvent == Event.TIMER_DISPATCH_FORCE)
                    return Arrays.asList(Action.APPLY_BRAKE_FORCE);

            case MOVING_ENGAGED:
                if (currentEvent == Event.BUTTON_HELD)
                    return Arrays.asList(Action.DISENGAGE_BRAKE,
                                        Action.ENGAGED_SOUND_OFF);
                else if(currentEvent == Event.SPEED_EQUAL_TO_ZERO)
                    return Arrays.asList(Action.NON_ACTION);

            case MOVING_DISENGAGING:
                if (currentEvent == Event.BRAKE_FULLY_DISENGAGED)
                    return Arrays.asList(Action.DISENGAGE_SOUND,
                                        Action.SET_BLUE_LED);
                else if(currentEvent == Event.BUTTON_HELD)
                    return Arrays.asList(Action.SET_ORANGE_LED);

            case MOVING_DISENGAGED:
                if (currentEvent == Event.BUTTON_HELD)
                    return Arrays.asList(Action.SET_ORANGE_LED);
                else if(currentEvent == Event.SPEED_EQUAL_TO_ZERO)
                    return Arrays.asList(Action.NON_ACTION);

            // Parked states
            case PARKED_ENGAGED:
                if (currentEvent == Event.BUTTON_PUSHED)
                    return Arrays.asList(Action.DISENGAGE_BRAKE,
                                        Action.SET_BLUE_LED);
                else if(currentEvent == Event.TRANSMISSION_SHIFT_OUT_PARK)
                    return Arrays.asList(Action.CONTINUOUS_ENGAGED_SOUND_ON);
            case PARKED_DISENGAGED:
                if (currentEvent == Event.BUTTON_PUSHED)
                    return Arrays.asList(Action.ENGAGE_BRAKE,
                                        Action.SET_RED_LED);
                else if(currentEvent == Event.TRANSMISSION_SHIFT_OUT_PARK)
                    return Arrays.asList(Action.NON_ACTION);


            // Stopped states
            case STOPPED_ENGAGED:
                if(currentEvent == Event.SPEED_GREATER_THAN_ZERO)
                    return Arrays.asList(Action.NON_ACTION);
                else if(currentEvent == Event.TRANSMISSION_SHIFT_IN_PARK)
                    return Arrays.asList(Action.ENGAGED_SOUND_OFF);
                else if(currentEvent == Event.BUTTON_HELD)
                    return Arrays.asList(Action.DISENGAGE_BRAKE,
                                        Action.ENGAGED_SOUND_OFF,
                                        Action.SET_BLUE_LED);

            case STOPPED_DISENGAGED:
                if(currentEvent == Event.TRANSMISSION_SHIFT_IN_PARK)
                    return Arrays.asList(Action.NON_ACTION);
                else if(currentEvent == Event.BUTTON_HELD)
                    return Arrays.asList(Action.ENGAGE_BRAKE,
                                        Action.CONTINUOUS_ENGAGED_SOUND_ON,
                                        Action.SET_RED_LED);

        }

        return null;
    }


    /* For unit testing purposes */
    public static void main(String[] args)
    {

    }
}
