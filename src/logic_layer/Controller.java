package logic_layer;

/**
 *
 */
public class Controller
{
    /**
     * Called 60 times a second by the simulator.
     */
    private void update()
    {
        // TODO
        // 1. Call whatEvents() from Rules with currentState
        //    - use mapping from whatEvents() to know which events to "listen" to
        //    - use mapping to know which State to move next
        // 2. Iterate through the events and call didEventOccur() (from Events) on each event
        //    - as long as we stay in the same state, keep calling didEventOccur() with the same list of events
        //      for each update() call
        // 3. When didEventOccur() returns true, call whatActions from Rules
        //    - then we update the object's current State to the one that corresponds from he mapping in whatEvents()
        //    - then iterate through all the actions returned by whatActions() and execute them
        // 4. To execute each action, we must pass them to the execute() method in Actions
        // 5. Repeat.
    }

    /* For unit testing purposes */
    public static void main(String[] args)
    {
        //
    }
}