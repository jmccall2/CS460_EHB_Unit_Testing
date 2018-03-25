package logic_layer;

/**
 * Based on the SRS control logic diagram
 */
public enum Event
{
    BUTTON_RELEASED,
    BRAKE_FORCE_FULLY_ENGAGED,

    // Variations of BUTTON_RELEASED
    BUTTON_PRESSED_SPEED_STOP, // speed === 0
    BUTTON_PRESSED_SPEED_LOW,  // speed <= 40km/h
    BUTTON_PRESSED_SPEED_MED,  // speed > 40km/h && < 80km/h
    BUTTON_PRESSED_SPEED_HIGH, // speed >= 80km/h

    // Used to repeat actions within the same state
    NON_EVENT,

}
