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
     * @return mapping of events to states:
     *         -the events are what we are "listening" for
     *         -the states are what we would move into, based on which events occurred
     */
    private static HashMap<Event,State> whatEvents(State currentState)
    {
        HashMap<Event, State> eventToState = new HashMap<>();

        switch(currentState)
        {
            case BRAKE_DISENGAGED:
                eventToState.put(Event.BUTTON_PRESSED_SPEED_STOP, State.BRAKE_ENGAGED);
                // Slow speed, high braking force
                eventToState.put(Event.BUTTON_PRESSED_SPEED_LOW, State.HIGH_BRAKING_MODE);
                eventToState.put(Event.BUTTON_PRESSED_SPEED_MED, State.MED_BRAKING_MODE);
                // High speed, low braking force
                eventToState.put(Event.BUTTON_PRESSED_SPEED_HIGH, State.LOW_BRAKING_MODE);
                break;
            case BRAKE_ENGAGING:
                eventToState.put(Event.BUTTON_PRESSED, State.BRAKE_DISENGAGED);
                eventToState.put(Event.BRAKE_FORCE_FULLY_ENGAGED, State.BRAKE_ENGAGED);
                eventToState.put(Event.TIMER_TICK, State.BRAKE_ENGAGING);
                break;
            case BRAKE_ENGAGED:
                eventToState.put(Event.BUTTON_PRESSED, State.BRAKE_DISENGAGED);
                break;
            case HIGH_BRAKING_MODE:
            case MED_BRAKING_MODE:
            case LOW_BRAKING_MODE:
                eventToState.put(Event.BUTTON_PRESSED, State.BRAKE_DISENGAGED);
                eventToState.put(Event.TIMER_TICK, State.BRAKE_ENGAGING);
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
        List newActions = new ArrayList();
        switch(currentState)
        {
            case BRAKE_DISENGAGED:
                switch (currentEvent)
                {
                    case BUTTON_PRESSED_SPEED_STOP:
                        newActions.add(Action.ENGAGE_BRAKE_FULLY);
                        newActions.add(Action.SOUND_BRAKE_FULLY_ENGAGED);
                        newActions.add(Action.SET_RED_LED);
                        break;
                    case BUTTON_PRESSED_SPEED_LOW:
                    case BUTTON_PRESSED_SPEED_MED:
                    case BUTTON_PRESSED_SPEED_HIGH:
                }
                break;
            case BRAKE_ENGAGING:
                switch (currentEvent)
                {
                    default:
                        break;
                }
                break;
            case BRAKE_ENGAGED:
                switch (currentEvent)
                {
                    default:
                        break;
                }
                break;
            case HIGH_BRAKING_MODE:
                switch (currentEvent)
                {
                    default:
                        break;
                }
                break;
            case MED_BRAKING_MODE:
                switch (currentEvent)
                {
                    default:
                        break;
                }
                break;
            case LOW_BRAKING_MODE:
                switch (currentEvent)
                {
                    default:
                        break;
                }
                break;
        }

        return newActions;
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
