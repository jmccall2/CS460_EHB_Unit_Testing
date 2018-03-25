import logic_layer.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import virtual_layer.*;

/**
 * Created by Kevin Omidvaran on 3/23/18.
 */
public class EventTest
{
  EventTesterButton _button = new EventTesterButton();
  EventTesterBrake _brake = new EventTesterBrake();
  EventTesterMotion _motion = new EventTesterMotion();
  EventTesterEvents _events = new EventTesterEvents(_motion,_brake,_button);

  @Test
  void test_brake_force_fully_one()
  {
    _brake.setPressure(100.0);
    Assertions.assertEquals(true, _events.didEventOccur(Event.BRAKE_FORCE_FULLY_ENGAGED));
  }

  @Test
  void test_brake_force_fully_two()
  {
    _brake.setPressure(50.0);
    Assertions.assertEquals(false, _events.didEventOccur(Event.BRAKE_FORCE_FULLY_ENGAGED));
  }

  @Test
  void test_brake_force_fully_three()
  {
    _brake.setPressure(0.0);
    Assertions.assertEquals(false, _events.didEventOccur(Event.BRAKE_FORCE_FULLY_ENGAGED));
  }

  @Test
  void test_timer()
  {
    Assertions.assertEquals(true, _events.didEventOccur(Event.NON_EVENT));

  }
  @Test
  void test_pressed_stopped()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    _motion.setSpeed(0.0);
    Assertions.assertEquals(true, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_STOP));

  }
  @Test
  void test_pressed_stopped2()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    _motion.setSpeed(0.0);
    Assertions.assertEquals(true, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_STOP));

  }
  @Test
  void test_pressed_stopped3()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    _motion.setSpeed(10.0);
    Assertions.assertEquals(false, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_STOP));

  }
  @Test
  void test_pressed_stopped4()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    _motion.setSpeed(10.0);
    Assertions.assertEquals(false, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_STOP));

  }
  @Test
  void test_pressed_stopped5()
  {
    _button.setStatus(ButtonStatus.NOT_PRESSED);
    _motion.setSpeed(0.0);
    Assertions.assertEquals(false, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_STOP));

  }
  @Test
  void test_pressed_low()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    _motion.setSpeed(30.0);
    Assertions.assertEquals(true, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_LOW));

  }
  @Test
  void test_pressed_low2()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    _motion.setSpeed(30.0);
    Assertions.assertEquals(true, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_LOW));

  }
  @Test
  void test_pressed_low3()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    _motion.setSpeed(40.0);
    Assertions.assertEquals(true, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_LOW));

  }
  @Test
  void test_pressed_low4()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    _motion.setSpeed(50.0);
    Assertions.assertEquals(false, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_LOW));

  }
  @Test
  void test_pressed_low5()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    _motion.setSpeed(50.0);
    Assertions.assertEquals(false, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_LOW));

  }
  @Test
  void test_pressed_low6()
  {
    _button.setStatus(ButtonStatus.NOT_PRESSED);
    _motion.setSpeed(30.0);
    Assertions.assertEquals(false, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_LOW));

  }

  @Test
  void test_pressed_med()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    _motion.setSpeed(41.0);
    Assertions.assertEquals(true, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_MED));

  }
  @Test
  void test_pressed_med2()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    _motion.setSpeed(79.0);
    Assertions.assertEquals(true, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_MED));

  }
  @Test
  void test_pressed_med3()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    _motion.setSpeed(60.0);
    Assertions.assertEquals(true, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_MED));

  }
  @Test
  void test_pressed_med4()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    _motion.setSpeed(100.0);
    Assertions.assertEquals(false, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_MED));

  }
  @Test
  void test_pressed_med5()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    _motion.setSpeed(30.0);
    Assertions.assertEquals(false, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_MED));

  }
  @Test
  void test_pressed_med6()
  {
    _button.setStatus(ButtonStatus.NOT_PRESSED);
    _motion.setSpeed(60.0);
    Assertions.assertEquals(false, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_MED));

  }
  @Test
  void test_pressed_high()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    _motion.setSpeed(85.0);
    Assertions.assertEquals(true, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_HIGH));

  }
  @Test
  void test_pressed_high2()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    _motion.setSpeed(85.0);
    Assertions.assertEquals(true, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_HIGH));

  }
  @Test
  void test_pressed_high3()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    _motion.setSpeed(80.0);
    Assertions.assertEquals(true, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_HIGH));

  }
  @Test
  void test_pressed_high4()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    _motion.setSpeed(30.0);
    Assertions.assertEquals(false, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_HIGH));

  }
  @Test
  void test_pressed_high5()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    _motion.setSpeed(50.0);
    Assertions.assertEquals(false, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_HIGH));

  }
  @Test
  void test_pressed_high6()
  {
    _button.setStatus(ButtonStatus.NOT_PRESSED);
    _motion.setSpeed(85.0);
    Assertions.assertEquals(false, _events.didEventOccur(Event.BUTTON_PRESSED_SPEED_HIGH));

  }
  @Test
  void test_pressed()
  {
    _button.setStatus(ButtonStatus.NOT_PRESSED);
    Assertions.assertEquals(false, _events.didEventOccur(Event.BUTTON_PRESSED));

  }
  @Test

  void test_pressed2()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    Assertions.assertEquals(true, _events.didEventOccur(Event.BUTTON_PRESSED));

  }
  @Test

  void test_pressed3()
  {
    _button.setStatus(ButtonStatus.PRESSED);
    Assertions.assertEquals(true, _events.didEventOccur(Event.BUTTON_PRESSED));

  }

}