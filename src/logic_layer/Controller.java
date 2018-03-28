package logic_layer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import demo.DemoVisual;
import virtual_layer.Alarm;
import virtual_layer.Brake;
import virtual_layer.Button;
import virtual_layer.Motion;

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
    private Actions actions = new Actions(brake, alarm, motion);
    private Events events = new Events(motion, brake, button);
    private Rules rules = new Rules();

    // The current State of the button
    private State currentState;
    int counter = 0;

    /**
     * Constructor
     */
    public Controller()
    {
        DemoVisual.demoPopup(); // Here only for demo.
        this.currentState = State.BRAKE_DISENGAGED;
        DemoVisual.refresh(this.currentState); // Here only for demo.
    }

    /**
     * Called 60 times a second by the simulator.
     */
    public void update()
    {
        boolean performActions = false;
        List<Action> actionsToPerform = new ArrayList<>();
        Event eventOccurred = Event.NON_EVENT;
        LinkedHashMap<Event,State> eventsToState = rules.whatEvents(currentState);
        
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
            DemoVisual.refresh(this.currentState); // Here only for demo.
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
