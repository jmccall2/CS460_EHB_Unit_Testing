package logic_layer;

import java.lang.reflect.Array;
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
            // SIMPLER STATES
            case BRAKE_DISENGAGED:
                hm.put(Event.BUTTON_PRESSED, currentState);
                hm.put(Event.SPEED_EQUAL_TO_ZERO, currentState);
                break;
            case BRAKE_ENGAGING: // Emergency mode
                hm.put(Event.TIMER_TICK, currentState);
                hm.put(Event.BRAKE_FORCE_FULLY_ENGAGED, currentState);
                hm.put(Event.BUTTON_PRESSED, currentState);
                break;
            case BRAKE_ENGAGED:  // Park mode
                hm.put(Event.BUTTON_PRESSED, currentState);
                break;

            // Different "entry points" to the BRAKE_ENGAGING state, based on the vehicle's speed
            case HIGH_BRAKING_MODE:
            case MED_BRAKING_MODE:
            case LOW_BRAKING_MODE:
                hm.put(Event.BUTTON_PRESSED, currentState);
                hm.put(Event.TIMER_TICK, currentState);
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
        List<Action> actions = new ArrayList<>();

        switch(currentState) {
            // SIMPLER STATES
            case BRAKE_DISENGAGED:
                if (currentEvent == Event.BUTTON_PRESSED)
                {

                }
            case BRAKE_ENGAGING: // Emergency mode
                if (currentEvent == Event.BRAKE_FORCE_FULLY_ENGAGED)
                {
                    actions.add(Action.SOUND_BRAKE_FULLY_ENGAGED);
                    actions.add(Action.STOP_TIMER);
                }
            case BRAKE_ENGAGED:  // Park mode

            // Different "entry points" to the BRAKE_ENGAGING state, based on the vehicle's speed
            case HIGH_BRAKING_MODE:
            case MED_BRAKING_MODE:
            case LOW_BRAKING_MODE:
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
