package virtual_layer;


import simulator.interfaces.GearTypes;

/**
 * Created by family on 3/23/18.
 */
public class EventTesterMotion
{
  GearTypes currentGear;
  GearTypes previousGear;
  double speed;

  /**
   * Constructor for a Motion object.
   */
  public EventTesterMotion()
  {
    currentGear = null;
    previousGear = null;
    speed = 0.0;
  }

  /**
   * Calls getSpeed() in SpeedInterface, to return the current speed.
   *
   * @return Current speed of the vehicle in m/s, as a double.
   */
  public double getSpeed()
  {
    return speed;
  }

  public void setSpeed(double speed1)
  {
    speed = speed1;
  }

  /**
   * Calls getGear() in SpeedInterface, to return the current gear.
   * Updates the instance variables previousGear and currentGear accordingly.
   *
   * @return The current gear of the vehicle.
   */


  public void setGear(GearTypes gear)
  {
    if (currentGear != gear)
    {
      previousGear = currentGear;
      currentGear = gear;
    }

  }

  public GearTypes getGear()
  {
    return currentGear;
  }

  /**
   * @return The last gear the vehicle was in.
   */
  public GearTypes getPreviousGear()
  {
    return previousGear;
  }
}