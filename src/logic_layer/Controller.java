package logic_layer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
    private Event lastEvent;
    int counter = 0;
    int counter2 = 0;

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
            if(lastEvent == Event.BUTTON_PRESSED_SPEED_STOP && counter2 == 0)
            {
              counter2 ++;
              if(actionsToPerform.contains(Action.SOUND_BRAKE_DISENGAGED))
              {
                actionsToPerform.remove(Action.SOUND_BRAKE_DISENGAGED);
              }
            }
            else if(lastEvent == Event.BUTTON_PRESSED_SPEED_STOP && counter2 == 1)
            {
              counter2 = 0;
            }
            lastEvent = eventOccurred;
            this.currentState = eventsToState.get(eventOccurred);
            if(actionsToPerform.contains(Action.SOUND_BRAKE_ENGAGING))
            {
              counter ++;
            }
            if(counter == 2 && (actionsToPerform.contains(Action.SOUND_BRAKE_ENGAGING)))
            {
              counter = 0;
              actionsToPerform.remove(Action.SOUND_BRAKE_ENGAGING);
            }
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
