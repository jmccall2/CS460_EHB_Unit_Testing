package logic_layer;

import java.util.*;

/**
 *
 */
public class Controller
{
    private Actions actions = new Actions();
    // TODO: pass virtual layer objects to events and rules
    private Events events = new Events();
    private Rules rules = new Rules();

    private State currentState;

    public Controller()
    {
        // is this a safe assumption?
        currentState = State.BRAKE_DISENGAGED;
    }

    /**
     * Called 60 times a second by the simulator.
     */
    private void update()
    {
        boolean performActions = false;
        List<Action> actionsToPerform = new ArrayList<>();
        Event eventOccurred = Event.NON_EVENT; // TODO: use this or null?
        HashMap<Event,State> eventsToState = rules.whatEvents(currentState);

        for (Event eventKey : eventsToState.keySet())
        {
            performActions = events.didEventOccur(eventKey);

            if (performActions)
            {
                actionsToPerform = rules.whatActions(eventKey, currentState);
                eventOccurred = eventKey;
                break;
            }
        }

        if (performActions)
        {
            currentState = eventsToState.get(eventOccurred);
            for (Action action : actionsToPerform)
            {
                actions.execute(action);
            }
        }
    }

    /* For unit testing purposes */
    public static void main(String[] args)
    {
        Controller controller = new Controller();

        // Haven't tested this yet
        for (int i = 0; i < 1000; i++)
        {
            controller.update();
        }
    }
}