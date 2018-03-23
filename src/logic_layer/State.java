package logic_layer;

/**
 * Based on the SRS control logic diagram
 */
enum State
{
    // SIMPLER STATES
    BRAKE_DISENGAGED,
    BRAKE_ENGAGING, // Emergency mode
    BRAKE_ENGAGED,  // Park mode

    // Different "entry points" to the BRAKE_ENGAGING state, based on the vehicle's speed
    HIGH_BRAKING_MODE,
    MED_BRAKING_MODE,
    LOW_BRAKING_MODE,


    /* The following states will be discontinued after refactoring the logic interfaces */
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
