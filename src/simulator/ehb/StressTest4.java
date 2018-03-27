package simulator.ehb;

import simulator.interfaces.*;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

//random values generated from 0 to 6 for 140 mph to 0 mph
//random valeus causes car to jerk a lot
//StressTest made by Michael Sosebee
public class StressTest4 {
  private double _speed;
  private GearTypes _gear;
  private boolean wasEngaged = false;
  private int alertPlayed = 0;

  //Max pressure is considered to be at 6 kPa.
  //first is speed. second is pressure
  static TreeMap<Long, Double> stressTest4;

  //The units for the profiler is miles per hour to kPascal
  static
  {

    stressTest4 = new TreeMap<Long, Double>();
    for (int i = 140; i >= 5; i-=5)
    {
      //randomly create a pressure value for each mph value
      double randomNum = ThreadLocalRandom.current().nextInt(0, 6 + 1);
      stressTest4.put(Long.valueOf(i), randomNum);

    }

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
        Map.Entry<Long, Double> floor = stressTest4.floorEntry(key);
        Map.Entry<Long, Double> ceiling = stressTest4.ceilingEntry(key);


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
