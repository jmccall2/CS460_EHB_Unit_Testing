package simulator.ehb;

//alternating values causes jerky stops
//StressTest made by Michael Sosebee
import simulator.interfaces.*;

import java.util.Map;
import java.util.TreeMap;

public class StressTest3 {

  private double _speed;
  private GearTypes _gear;
  private boolean wasEngaged = false;
  private int alertPlayed = 0;

  //Max pressure is considered to be at 6 kPa.
  //first is speed. second is pressure
  static TreeMap<Long, Double> stressTest3;

  //The units for the profiler is miles per hour to kPascal
  static
  {
    stressTest3 = new TreeMap<Long, Double>();
    stressTest3.put(Long.valueOf(20), 4.75);
    stressTest3.put(Long.valueOf(25), 4.75);
    stressTest3.put(Long.valueOf(30), 0.00);
    stressTest3.put(Long.valueOf(35), 4.75);
    stressTest3.put(Long.valueOf(40), 0.00);
    stressTest3.put(Long.valueOf(45), 4.75);
    stressTest3.put(Long.valueOf(50), 0.00);
    stressTest3.put(Long.valueOf(55), 4.75);
    stressTest3.put(Long.valueOf(60), 0.00);
    stressTest3.put(Long.valueOf(65), 4.75);
    stressTest3.put(Long.valueOf(70), 0.00);
    stressTest3.put(Long.valueOf(75), 4.75);
    stressTest3.put(Long.valueOf(80), 0.00);
    stressTest3.put(Long.valueOf(85), 4.75);
    stressTest3.put(Long.valueOf(90), 0.00);
    stressTest3.put(Long.valueOf(95), 4.75);
    stressTest3.put(Long.valueOf(100), 0.00);
    stressTest3.put(Long.valueOf(105), 4.75);
    stressTest3.put(Long.valueOf(110), 0.00);
    stressTest3.put(Long.valueOf(115), 4.75);
    stressTest3.put(Long.valueOf(120), 0.00);
    stressTest3.put(Long.valueOf(125), 4.75);
    stressTest3.put(Long.valueOf(130), 0.00);
    stressTest3.put(Long.valueOf(135), 4.75);
    stressTest3.put(Long.valueOf(140), 0.00);
  }

  //We use the pressure profile obtained from http://www.scielo.br/scielo.php?script=sci_arttext&pid=S0100-73862001000100007
  // with supplement http://www.optimumg.com/docs/Brake_tech_tip.pdf

  public void update()
  {
    _speed = 0;
    if (ButtonInterface.isDown())
    {
      ButtonInterface.setColor(ButtonColorTypes.RED);
      if (alertPlayed == 0)
      {
        ButtonInterface.play(ButtonSoundTypes.ENGAGED);
        wasEngaged = true;
        alertPlayed++;
      }
      _speed = (SpeedInterface.getSpeed() / 0.44704); // Get the speed from the speed interface. calculating from m/s to mph
      _gear = GearInterface.getGear();  // Get the current gear from the Gear interface.

      if (_gear.toString().equals("Park"))
      {
        BrakeInterface.setPressure(100.00);
      }
      else if ((!_gear.toString().equals("Reverse")) && _speed < 0)
      {
        BrakeInterface.setPressure(100.00);
      }
      else
      {
        //This uses the max and low values of the tree map to get the closest value in the
        //pressure profile
        Long key = Long.valueOf((int) _speed);
        Map.Entry<Long, Double> floor = stressTest3.floorEntry(key);
        Map.Entry<Long, Double> ceiling = stressTest3.ceilingEntry(key);


        double closestResult;
        if (floor != null && ceiling != null)
        {
          closestResult = (floor.getValue() + ceiling.getValue()) / 2.0;
        }
        else if (floor != null)
        {
          closestResult = floor.getValue();
        }
        else
        {
          closestResult = ceiling.getValue();
        }

        BrakeInterface.setPressure((closestResult / 6.0) * 100);
      }
    }
    else
    {
      ButtonInterface.setColor(ButtonColorTypes.BLUE);
      if (wasEngaged)
      {
        ButtonInterface.play(ButtonSoundTypes.DISENGAGED);
        wasEngaged = false;
        alertPlayed--;
      }

      BrakeInterface.setPressure(0.0);
    }
  }
}
