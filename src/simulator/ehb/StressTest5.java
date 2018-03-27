package simulator.ehb;

//sets values at each value of mph with calcualted pressure values
//starts from nearly no pressure to maximum pressure
//slow stop at high speeds, heavy skidding at lower speeds
//Michael Sosebee
import simulator.interfaces.*;

import java.util.Map;
import java.util.TreeMap;

public class StressTest5 {

  private double _speed;
  private GearTypes _gear;
  private boolean wasEngaged = false;
  private int alertPlayed = 0;

  //Max pressure is considered to be at 6 kPa.
  //first is speed. second is pressure
  static TreeMap<Long, Double> stressTest5;

  //The units for the profiler is miles per hour to kPascal
  static
  {
    double begin = 6.0;
    double div = 140.0;
    stressTest5 = new TreeMap<Long, Double>();
    for (int i = 140; i > 0; i--)
    {
      double pressure = (begin + 0.1) - ((i/div) * begin);

      System.out.println("This is the i value " + i);
      System.out.println(pressure);
      System.out.println();

      stressTest5.put(Long.valueOf(i), pressure);

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
        Map.Entry<Long, Double> floor = stressTest5.floorEntry(key);
        Map.Entry<Long, Double> ceiling = stressTest5.ceilingEntry(key);


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
