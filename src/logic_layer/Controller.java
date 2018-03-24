package logic_layer;

import virtual_layer.*;

import java.util.*;

/**
 *
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

    private State currentState;

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
            System.out.println(" ******** Controller found actions to execute");
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
        Controller controller = new Controller();

        // Haven't tested this yet
        for (int i = 0; i < 1000; i++)
        {
            controller.update();
        }
    }
}