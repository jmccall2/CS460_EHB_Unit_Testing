package logic_layer;

// import java.lang.reflect.Array;
import java.util.*;

/**
 * Responsible for encapsulating the rules (or mappings) that define the control logic diagram for the EHB.
 * There are two main data structures inside of the Rules object’s abstract state, both of which are mappings:
 *  1. A mapping from (currentState) to (Event, nextState). This data structure is used to check which events
 *     are in the scope of a given state, and what states to transition to when a particular event occurs
 *     in a given state.
 *  2. A mapping from (State, Event) to (List<Action>). This data structure is used to check what actions to take
 *     when an event occurs in a particular state.
 */
public class Rules
{
    /**
     *
     * @param currentState of the EHB
     * @return mapping of events to states:
     *         -the events are what we are "listening" for
     *         -the states are what we would move into, based on which events occurred
     */
    protected HashMap<Event,State> whatEvents(State currentState)
    {
        HashMap eventToState = new HashMap();

            switch (currentState) {
                case BRAKE_DISENGAGED:
                    eventToState.put(Event.BUTTON_PRESSED_SPEED_STOP, State.BRAKE_ENGAGED);
                    // NOTE: Slow speed, high braking force
                    eventToState.put(Event.BUTTON_PRESSED_SPEED_LOW, State.HIGH_BRAKING_MODE);
                    eventToState.put(Event.BUTTON_PRESSED_SPEED_MED, State.MED_BRAKING_MODE);
                    // NOTE: High speed, low braking force
                    eventToState.put(Event.BUTTON_PRESSED_SPEED_HIGH, State.LOW_BRAKING_MODE);
                    eventToState.put(Event.NON_EVENT, State.BRAKE_DISENGAGED);

                    break;
                case BRAKE_ENGAGING: // Emergency mode
                    eventToState.put(Event.BUTTON_RELEASED, State.BRAKE_DISENGAGED);
                    eventToState.put(Event.BRAKE_FORCE_FULLY_ENGAGED, State.BRAKE_ENGAGED);
                    eventToState.put(Event.NON_EVENT, State.BRAKE_ENGAGING);
                    break;
                case BRAKE_ENGAGED: // Parking mode
                    eventToState.put(Event.BUTTON_RELEASED, State.BRAKE_DISENGAGED);
                    break;
                case HIGH_BRAKING_MODE:
                case MED_BRAKING_MODE:
                case LOW_BRAKING_MODE:
                    eventToState.put(Event.BUTTON_RELEASED, State.BRAKE_DISENGAGED);
                    eventToState.put(Event.NON_EVENT, State.BRAKE_ENGAGING);
                    break;
                default:
                    break;
            }

        return eventToState;
    }

    /**
     *
     * @param currentEvent that just occurred
     * @param currentState of the EHB
     * @return mapping of (Event, State) to a list of Actions
     */
    protected List<Action> whatActions(Event currentEvent, State currentState)
    {
        List actions = new ArrayList();

        if (currentState == null)
        {
            actions.add(Action.SET_BLUE_LED);
            actions.add(Action.DISENGAGE_BRAKE);
            return actions;
        }

        switch(currentState) {
            case BRAKE_DISENGAGED:
                switch(currentEvent)
                {
                    case BUTTON_PRESSED_SPEED_STOP:
                        actions.add(Action.ENGAGE_BRAKE_FULLY);
                        actions.add(Action.SET_RED_LED);
                        actions.add(Action.SOUND_BRAKE_FULLY_ENGAGED);
                        break;
                    case BUTTON_PRESSED_SPEED_HIGH: // NOTE: high speed, low force
                        actions.add(Action.ENGAGE_BRAKE_LOW_FORCE);
                        actions.add(Action.SET_RED_LED);
                        actions.add(Action.SOUND_BRAKE_ENGAGING);
                        break;
                    case BUTTON_PRESSED_SPEED_MED:
                        actions.add(Action.ENGAGE_BRAKE_MED_FORCE);
                        actions.add(Action.SET_RED_LED);
                        actions.add(Action.SOUND_BRAKE_ENGAGING);
                        break;
                    case BUTTON_PRESSED_SPEED_LOW: // NOTE: low speed, high force
                        actions.add(Action.ENGAGE_BRAKE_HIGH_FORCE);
                        actions.add(Action.SET_RED_LED);
                        actions.add(Action.SOUND_BRAKE_ENGAGING);
                        break;
                    default:
                        actions.add(Action.INIT_ACTIONS);

                }
                break;
            case BRAKE_ENGAGING: // Emergency mode
                switch (currentEvent)
                {
                    case BRAKE_FORCE_FULLY_ENGAGED:
                        actions.add(Action.SOUND_BRAKE_FULLY_ENGAGED);
                        break;
                    case NON_EVENT:
                        actions.add(Action.INCREASE_BRAKE_FORCE);
                        break;
                    case BUTTON_RELEASED:
                        actions.add(Action.DISENGAGE_BRAKE);
                        actions.add(Action.SET_BLUE_LED);
                        actions.add(Action.SOUND_BRAKE_DISENGAGED);
                        break;
                }
                break;
            case BRAKE_ENGAGED:  // Park mode
                switch (currentEvent)
                {
                    case BUTTON_RELEASED:
                        actions.add(Action.DISENGAGE_BRAKE);
                        actions.add(Action.SET_BLUE_LED);
                        actions.add(Action.SOUND_BRAKE_DISENGAGED);
                        break;
                }
                break;
            case HIGH_BRAKING_MODE:
            case MED_BRAKING_MODE:
            case LOW_BRAKING_MODE:
                switch(currentEvent)
                {
                    case NON_EVENT:
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
        Rules rules = new Rules();
        System.out.println("(state) -> (event, state) -> (actions)");
        for (State st : State.values())
        {
            System.out.println("\t"+st+": if");
            HashMap eventsToStates = rules.whatEvents(st);
            eventsToStates.forEach((event,state) -> {
                List actions = rules.whatActions((Event)event, (State)state);
                System.out.println("\t\t+ " + event+": then");
                actions.forEach((action) -> {
                    System.out.println("\t\t\t"+action);
                });
            });
        }
    }
}
