package logic_layer;

/**
 * Based on the SRS control logic diagram
 */
enum Event
{
    /* Entry to the initial state */
    INIT_EVENT,
    BUTTON_PRESSED,
    BRAKE_FORCE_FULLY_ENGAGED,
    TIMER_TICK,


    /* The following events will be discontinued after refactoring the logic interfaces */
    // Button events
    BUTTON_PUSHED,
    BUTTON_HELD,

    // Brake events
    BRAKE_ENGAGED_PARK,
    BRAKE_DISENGAGED_PARK,
    BRAKE_FULLY_ENGAGED,
    BRAKE_FULLY_DISENGAGED,

    // Speed events
    SPEED_EQUAL_TO_ZERO,
    SPEED_GREATER_THAN_ZERO,

    // Transmission events
    TRANSMISSION_SHIFT_IN_PARK,
    TRANSMISSION_SHIFT_OUT_PARK,

    // Timer events
    TIMER_DISPATCH_FORCE, /* Open to suggestions on how to rephrase this. Event in the SRS diagram is confusing */
}
