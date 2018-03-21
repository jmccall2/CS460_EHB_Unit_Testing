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
        HashMap<Event, State> hm = new HashMap<>();

        switch(currentState)
        {
            // Moving states
            case MOVING_ENGAGING:
                hm.put(Event.BUTTON_HELD, State.MOVING_ENGAGING);
                hm.put(Event.BRAKE_FULLY_ENGAGED, State.MOVING_ENGAGING);
                hm.put(Event.TIMER_DISPATCH_FORCE, State.MOVING_ENGAGING);
                break;
            case MOVING_ENGAGED:
                hm.put(Event.BUTTON_HELD, State.MOVING_ENGAGED);
                hm.put(Event.SPEED_EQUAL_TO_ZERO, State.MOVING_ENGAGED);
                break;
            case MOVING_DISENGAGING:
                hm.put(Event.BRAKE_FULLY_DISENGAGED, State.MOVING_DISENGAGING);
                hm.put(Event.BUTTON_HELD, State.MOVING_DISENGAGING);
                break;
            case MOVING_DISENGAGED:
                hm.put(Event.BUTTON_HELD, State.MOVING_DISENGAGED);
                hm.put(Event.SPEED_EQUAL_TO_ZERO, State.MOVING_DISENGAGED);
                break;

                // Parked states
            case PARKED_ENGAGED:
                hm.put(Event.BUTTON_PUSHED, State.PARKED_ENGAGED);
                hm.put(Event.TRANSMISSION_SHIFT_OUT_PARK, State.PARKED_ENGAGED);
                break;
            case PARKED_DISENGAGED:
                hm.put(Event.BUTTON_PUSHED, State.PARKED_DISENGAGED);
                hm.put(Event.TRANSMISSION_SHIFT_OUT_PARK, State.PARKED_DISENGAGED);
                break;

                // Stopped states
            case STOPPED_ENGAGED:
                hm.put(Event.SPEED_GREATER_THAN_ZERO, State.STOPPED_ENGAGED);
                hm.put(Event.TRANSMISSION_SHIFT_IN_PARK, State.STOPPED_ENGAGED);
                hm.put(Event.BUTTON_HELD, State.STOPPED_ENGAGED);
                break;
            case STOPPED_DISENGAGED:
                hm.put(Event.TRANSMISSION_SHIFT_IN_PARK, State.STOPPED_DISENGAGED);
                hm.put(Event.BUTTON_HELD, State.STOPPED_DISENGAGED);
                break;
        }

        return hm;
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
        System.out.println("(state) -> (event, state) -> (actions)");
        for (State st : State.values())
        {
            System.out.println("\t"+st+": if");
            HashMap hm = whatEvents(st);
            hm.forEach((event,state) -> {
                List actions = whatActions((Event)event, (State)state);
                System.out.println("\t\t+ " + event+": then");
                actions.forEach((action) -> {
                    System.out.println("\t\t\t"+action);
                });
            });
        }
    }
}
