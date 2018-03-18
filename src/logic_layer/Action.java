package logic_layer;

/**
 * Based on the SRS control logic diagram
 */
enum Action
{
    // Brake actions
    ENGAGE_BRAKE,
    DISENGAGE_BRAKE,
    APPLY_BRAKE_FORCE,

    // Light alert actions
    SET_BLUE_LED,
    SET_ORANGE_LED,
    SET_RED_LED,

    // Sound alert actions
    /* Should we differentiate between continuous and single sound like this?
       Diagram is very confusing
     */
    CONTINUOUS_ENGAGED_SOUND_ON,
    SINGLE_ENGAGED_SOUND_ON,
    SINGLE_DISENGAGED_SOUND_ON,
    ENGAGED_SOUND_OFF,
    /* This action isn't even in the diagram??? Even though there is an action that says
       'Sound "electronic handbrake disengaged" alert'
       And all the 'Sound "electronic handbrake engaged" alert' actions have a corresponding "stopping" action
     */
    DISENGAGED_SOUND_OFF,

    // Old sound actions, will be discontinued by the ones above, whenever we understand that
    ENGAGE_SOUND,
    DISENGAGE_SOUND,

    // "Nothing" action - used to represent the * where nothing happens
    NON_ACTION,

}
