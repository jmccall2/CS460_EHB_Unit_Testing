package logic_layer;

/**
 * Based on the SRS control logic diagram
 */
enum State
{
    BRAKE_DISENGAGED,
    BRAKE_ENGAGING, // Emergency mode
    BRAKE_ENGAGED,  // Park mode

    // Different "entry points" to the BRAKE_ENGAGING state, based on the vehicle's speed
    HIGH_BRAKING_MODE,
    MED_BRAKING_MODE,
    LOW_BRAKING_MODE,
}
