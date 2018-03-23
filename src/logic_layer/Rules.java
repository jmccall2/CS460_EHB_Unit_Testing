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
     * @return mapping of events to states:
     *         -the events are what we are "listening" for
     *         -the states are what we would move into, based on which events occurred
     */
    private static HashMap<Event,State> whatEvents(State currentState)
    {
        HashMap eventToState = new HashMap();

        if (currentState == null)
        {
            eventToState.put(Event.INIT_EVENT, null);
            return eventToState;
        }

        switch(currentState)
        {
            // SIMPLER STATES
            case BRAKE_DISENGAGED:
                eventToState.put(Event.BUTTON_PRESSED_SPEED_STOP, currentState);
                eventToState.put(Event.BUTTON_PRESSED_SPEED_LOW, currentState);
                eventToState.put(Event.BUTTON_PRESSED_SPEED_MED, currentState);
                eventToState.put(Event.BUTTON_PRESSED_SPEED_HIGH, currentState);
                break;
            case BRAKE_ENGAGING: // Emergency mode
                eventToState.put(Event.TIMER_TICK, currentState);
                eventToState.put(Event.BRAKE_FORCE_FULLY_ENGAGED, currentState);
                eventToState.put(Event.BUTTON_PRESSED, currentState);
                break;
            case BRAKE_ENGAGED:  // Park mode
                eventToState.put(Event.BUTTON_PRESSED, currentState);
                break;

            // Different "entry points" to the BRAKE_ENGAGING state, based on the vehicle's speed
            case HIGH_BRAKING_MODE:
            case MED_BRAKING_MODE:
            case LOW_BRAKING_MODE:
                eventToState.put(Event.BUTTON_PRESSED, currentState);
                eventToState.put(Event.TIMER_TICK, currentState);
                break;
            default:
                break;
        }

        return eventToState;
    }

    /**
     *
     * @param currentEvent
     * @param currentState
     * @return mapping of (Event, State) to a list of Actions
     */
    private static List<Action> whatActions(Event currentEvent, State currentState)
    {
        List actions = new ArrayList();

        if (currentState == null)
        {
            actions.add(Action.SET_BLUE_LED);
            actions.add(Action.DISENGAGE_BRAKE);
            return actions;
        }

        switch(currentState) {
            // SIMPLER STATES
            case BRAKE_DISENGAGED:
                switch(currentEvent)
                {
                    case BUTTON_PRESSED_SPEED_STOP:
                        actions.add(Action.ENGAGE_BRAKE_FULLY);
                        actions.add(Action.SET_RED_LED);
                        actions.add(Action.SOUND_BRAKE_FULLY_ENGAGED);
                        break;
                    case BUTTON_PRESSED_SPEED_HIGH:
                        actions.add(Action.ENGAGE_BRAKE_HIGH_FORCE);
                        break;
                    case BUTTON_PRESSED_SPEED_MED:
                        actions.add(Action.ENGAGE_BRAKE_MED_FORCE);
                        break;
                    case BUTTON_PRESSED_SPEED_LOW:
                        actions.add(Action.ENGAGE_BRAKE_LOW_FORCE);
                        break;
                }
                break;
            case BRAKE_ENGAGING: // Emergency mode
                switch (currentEvent)
                {
                    case BRAKE_FORCE_FULLY_ENGAGED:
                        actions.add(Action.SOUND_BRAKE_FULLY_ENGAGED);
                        actions.add(Action.STOP_TIMER);
                        break;
                    case TIMER_TICK:
                        actions.add(Action.INCREASE_BRAKE_FORCE);
                        break;
                    case BUTTON_PRESSED:
                        actions.add(Action.DISENGAGE_BRAKE);
                        actions.add(Action.SET_BLUE_LED);
                        actions.add(Action.SOUND_BRAKE_DISENGAGED);
                        actions.add(Action.STOP_TIMER);
                        break;
                }
                break;
            case BRAKE_ENGAGED:  // Park mode
                switch (currentEvent)
                {
                    case BUTTON_PRESSED:
                        actions.add(Action.DISENGAGE_BRAKE);
                        actions.add(Action.SET_BLUE_LED);
                        actions.add(Action.SOUND_BRAKE_DISENGAGED);
                        break;
                }
                break;

            // Different "entry points" to the BRAKE_ENGAGING state, based on the vehicle's speed
            case HIGH_BRAKING_MODE:
            case MED_BRAKING_MODE:
            case LOW_BRAKING_MODE:
                switch(currentEvent)
                {
                    case BUTTON_PRESSED:
                        actions.add(Action.DISENGAGE_BRAKE);
                        actions.add(Action.SET_BLUE_LED);
                        actions.add(Action.SOUND_BRAKE_DISENGAGED);
                        actions.add(Action.STOP_TIMER);
                        break;
                    case TIMER_TICK:
                        actions.add(Action.INCREASE_BRAKE_FORCE);
                        break;
                }
                break;
            default:
                break;
        }

        return actions;
    }


    /* For unit testing purposes */
    public static void main(String[] args)
    {
        System.out.println("(state) -> (event, state) -> (actions)");
        for (State st : State.values())
        {
            System.out.println("\t"+st+": if");
            HashMap eventsToStates = whatEvents(st);
            eventsToStates.forEach((event,state) -> {
                List actions = whatActions((Event)event, (State)state);
                System.out.println("\t\t+ " + event+": then");
                actions.forEach((action) -> {
                    System.out.println("\t\t\t"+action);
                });
            });
        }
    }
}
