package logic_layer;

/**
 * Based on the SRS control logic diagram
 */
enum State
{
    // Moving states
    MOVING_ENGAGING,
    MOVING_ENGAGED,
    MOVING_DISENGAGING,
    MOVING_DISENGAGED,

    // Parked states
    PARKED_ENGAGED,
    PARKED_DISENGAGED,

    // Stopped states
    STOPPED_ENGAGED,
    STOPPED_DISENGAGED,
}
