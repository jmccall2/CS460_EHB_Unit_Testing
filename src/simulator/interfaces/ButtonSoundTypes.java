package simulator.interfaces;

/**
 * This enum represents the set of sounds that are recognized by
 * the system. Each of these maps directly to an internal sound file.
 */
public enum ButtonSoundTypes
{
  ENGAGED,
  DISENGAGED,
  LONG_BEEP_A,
  LONG_BEEP_B,
  LONG_BEEP_C,
  SHORT_BEEP_A,
  SHORT_BEEP_B;

  @Override
  public String toString() {
    switch(this)
    {
      case ENGAGED:
        return "/simulator/resources/sounds/engaged.wav";
      case DISENGAGED:
        return "/simulator/resources/sounds/disengaged.wav";
      case LONG_BEEP_A:
        return "/simulator/resources/sounds/longBeepA.wav";
      case LONG_BEEP_B:
        return "/simulator/resources/sounds/longBeepB.wav";
      case LONG_BEEP_C:
        return "/simulator/resources/sounds/longBeepC.wav";
      case SHORT_BEEP_A:
        return "/simulator/resources/sounds/shortBeepA.wav";
      case SHORT_BEEP_B:
        return "/simulator/resources/sounds/shortBeepB.wav";
      default:
        return "NULL";
    }
  }
}
