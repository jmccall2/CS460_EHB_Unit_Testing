package logic_layer;

/**
 * Based on the SRS control logic diagram
 */
enum Event
{
    /* Entry to the initial state */
    INIT_EVENT, // NOTE may not be necessary
    BUTTON_PRESSED,
    BRAKE_FORCE_FULLY_ENGAGED,
    TIMER_TICK,

    // Variations of BUTTON_PRESSED
    BUTTON_PRESSED_SPEED_STOP, // speed === 0
    BUTTON_PRESSED_SPEED_LOW,  // speed <= 40km/h
    BUTTON_PRESSED_SPEED_MED,  // speed > 40km/h && < 80km/h
    BUTTON_PRESSED_SPEED_HIGH, // speed >= 80km/h

    NON_EVENT, // TODO: might be discontinued; used to initialize events

}
