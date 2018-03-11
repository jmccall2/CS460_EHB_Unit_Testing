package simulator.ehb;

//StressTest made by Michael Sosebee
import simulator.interfaces.*;

import java.util.Map;
import java.util.TreeMap;

//doubles all the double values

public class StressTest2 {

  private double _speed;
  private GearTypes _gear;
  private boolean wasEngaged = false;
  private int alertPlayed = 0;

  //Max pressure is considered to be at 6 kPa.
  //first is speed. second is pressure
  static TreeMap<Long, Double> stressTest2;

  //The units for the profiler is miles per hour to kPascal
  static
  {
    stressTest2 = new TreeMap<Long, Double>();
    stressTest2.put(Long.valueOf(20), 4.75*2);
    stressTest2.put(Long.valueOf(25), 4.75*2);
    stressTest2.put(Long.valueOf(30), 4.75*2);
    stressTest2.put(Long.valueOf(35), 4.5*2);
    stressTest2.put(Long.valueOf(40), 4.5*2);
    stressTest2.put(Long.valueOf(45), 4.5*2);
    stressTest2.put(Long.valueOf(50), 4.5*2);
    stressTest2.put(Long.valueOf(55), 4.25*2);
    stressTest2.put(Long.valueOf(60), 4.0*2);
    stressTest2.put(Long.valueOf(65), 3.75*2);
    stressTest2.put(Long.valueOf(70), 3.5*2);
    stressTest2.put(Long.valueOf(75), 3.25*2);
    stressTest2.put(Long.valueOf(80), 3.0*2);
    stressTest2.put(Long.valueOf(85), 2.75*2);
    stressTest2.put(Long.valueOf(90), 2.5*2);
    stressTest2.put(Long.valueOf(95), 2.25*2);
    stressTest2.put(Long.valueOf(100), 2.0*2);
    stressTest2.put(Long.valueOf(105), 1.75*2);
    stressTest2.put(Long.valueOf(110), 1.5*2);
    stressTest2.put(Long.valueOf(115), 1.25*2);
    stressTest2.put(Long.valueOf(120), 1.0*2);
    stressTest2.put(Long.valueOf(125), 0.75*2);
    stressTest2.put(Long.valueOf(130), 0.5*2);
    stressTest2.put(Long.valueOf(135), 0.25*2);
    stressTest2.put(Long.valueOf(140), 0.00*2);
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
        Map.Entry<Long, Double> floor = stressTest2.floorEntry(key);
        Map.Entry<Long, Double> ceiling = stressTest2.ceilingEntry(key);


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
