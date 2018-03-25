package logic_layer;

import virtual_layer.*;

import java.util.*;

/**
 * Responsible for driving all logic that happens at the control logic level.
 * The current state in the control logic diagram is held within the abstract state of this object.
 * Instantiates the Rules, Events, and Actions classes and holds them as part of its abstract state.
 * It also instantiates the virtual components and makes the relevant ones available to the Actions and Events objects
 * since they will be the ones interacting with components on the virtual layer
 */
public class Controller
{
    // Instances of the virtual interfaces
    private Alarm alarm = new Alarm();
    private Brake brake = new Brake();
    private Motion motion = new Motion();
    private Button button = new Button();

    // Instances of the logic interfaces
    private Actions actions = new Actions(brake, alarm);
    private Events events = new Events(motion, brake, button);
    private Rules rules = new Rules();

    // The current State of the button
    private State currentState;

    /**
     * Constructor
     */
    public Controller()
    {
        this.currentState = State.BRAKE_DISENGAGED;
    }

    /**
     * Called 60 times a second by the simulator.
     */
    public void update()
    {
        boolean performActions = false;
        List<Action> actionsToPerform = new ArrayList<>();
        Event eventOccurred = Event.NON_EVENT;
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
            this.currentState = eventsToState.get(eventOccurred);
            for (Action action : actionsToPerform)
            {
                actions.execute(action);
            }
        }
    }

    /* For unit testing purposes */
    public static void main(String[] args)
    {
        //
    }
}
