package virtual_layer;

import simulator.interfaces.ButtonInterface;

/**
 * Virtualizes the ButtonInterface, allowing the logic layer classes to
 * determine if the button has been pressed for a long time, a short time,
 * or is not pressed. This state is kept here, and uses the ButtonStatus enum.
 */
public class EventTesterButton
{

  ButtonStatus status = ButtonStatus.NOT_PRESSED;
  /**
   * Constructor for a Button object.
   */

  /**
   * Updates the time instance variables, calculates deltaT between them,
   * and returns a ButtonStatus accordingly.
   *
   * @return Either LONG_PRESS, SHORT_PRESS, or NOT_PRESSED.
   */
  public ButtonStatus getStatus()
  {
   return status;
  }
  public void setStatus(ButtonStatus stat)
  {
    status = stat;
  }

  /* For unit testing purposes */

}