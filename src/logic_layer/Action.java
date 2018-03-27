package logic_layer;

/**
 * Based on the SRS control logic diagram
 */
enum Action
{
    /* Entry point, accounts for:
       no audio,
       button light is blue,
       brake disengaged
     */
    INIT_ACTIONS,

    // EHB actions
    ENGAGE_BRAKE_FULLY,
    DISENGAGE_BRAKE,
    ENGAGE_BRAKE_HIGH_FORCE,
    ENGAGE_BRAKE_MED_FORCE,
    ENGAGE_BRAKE_LOW_FORCE,
    INCREASE_BRAKE_FORCE, // increase force by 1 "unit"

    // Light alert actions
    SET_BLUE_LED,
    SET_RED_LED,

    // Timer actions
    START_TIMER,
    STOP_TIMER,

    // Sound alert actions
    SOUND_BRAKE_FULLY_ENGAGED,
    SOUND_BRAKE_ENGAGING,
    SOUND_BRAKE_DISENGAGED,
}
