package simulator.simulation;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import simulator.interfaces.ButtonColorTypes;
import simulator.interfaces.GearTypes;
import java.lang.Math;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import simulator.simulation.engine.Engine;
import simulator.simulation.engine.Message;
import simulator.simulation.engine.MessageHandler;
import simulator.simulation.engine.Singleton;
import simulator.interfaces.SpeedInterface;

/**
 * Controller for main GUI.
 */
public class GuiController implements Initializable
{
  //Field that speed can be entered in.
  @FXML private TextField _setSpeedField;
  //Button that starts and resets the simulation.
  @FXML private Button _startStopSim;
  //The main handbrake button.
  @FXML private Button _handBrake;
  //Displays stats about the simulation.
  @FXML private Button _statsButton;
  //The following are the gear buttons.
  @FXML private RadioButton _parkButton;
  @FXML private RadioButton _reverseButton;
  @FXML private RadioButton _neutralButton;
  @FXML private RadioButton _driveButton;
  //Used to convert MPH speed to meters per second speed.
  private final double MPH_TO_MS = 0.448;
  private final double MAX_SPEED = 140;
  private final double MAX_REVERSE_SPEED = 40;
  private boolean _previousState = false;

  // fastest speed at which a transition to park is allowed
  private final double MAX_PARKING_SPEED = 1;

  private ButtonMessageHelper _buttonMessageHelper = new ButtonMessageHelper();
  private boolean _stopped = true;
  private ToggleGroup _group = new ToggleGroup();
  private ButtonColorTypes _buttonColor = null;
  private boolean _inReverse = false;
  private GUI _guiRef;
  private ArrayList<String> _invalidTransitions = new ArrayList<>();
  private String _currGear = "D";
  private StatCollector _statCollector;
  private StatsPopupController _statController;
  private InitStatus _initStatus = InitStatus.GOOD;
  private GearTypes _initGear;
  private double _initSpeed;
  private boolean _longHold = false;

  /**
   * Initialize and set action listeners for all components of the GUI.
   */
  @Override
  public void initialize(URL arg0, ResourceBundle arg1)
  {
    Engine.getMessagePump().signalInterest(SimGlobals.SET_BUTTON_COLOR, _buttonMessageHelper);
    _setSpeedField.setOnKeyPressed((keyPressEvent) -> {
      if (keyPressEvent.getCode().equals(KeyCode.ENTER))
      {
        System.out.println(" *** pressed enter from speed input");
        _setStartResetLogic();
      }

    });
    _initStats();
    _initGears();
    _initHandBrakeButton();
    _initStartResetButton();
  }

  // Initialize stats components.
  private void _initStats()
  {
    _statsButton.setDisable(true); // Stats are not available until the simulation starts.
    _statsButton.setOnAction((event) ->{
      if(!_statController.isUp) _InvokeOtherStage();
    });
  }

  private void _setStartResetLogic()
  {
    if(_stopped) _setInitSpeed();
    _validate();
    if(_initStatus != InitStatus.GOOD)
    {
      _guiRef.showPopup(_initStatus.toString());
    }
    else if(_stopped)
    {
      // Code that runs when simulation begins to run
      _handBrake.setDisable(false);
      _setGearTransitions();
      _stopped = false;
      _startStopSim.setText("Reset");
      Engine.getMessagePump().sendMessage(new Message(SimGlobals.SPEED, (_inReverse ? _initSpeed*-1 : _initSpeed)*MPH_TO_MS));
      Engine.getMessagePump().sendMessage(new Message(SimGlobals.IS_UP));
      Engine.getMessagePump().sendMessage(new Message(SimGlobals.SET_PRESSURE,0.0));
      Engine.getMessagePump().sendMessage(new Message(SimGlobals.START_SIM));
      // Stop simulating movement
      Engine.getConsoleVariables().find(Singleton.CALCULATE_MOVEMENT).setValue("true");
      _setSpeedField.setDisable(true);
      _statCollector = new StatCollector(); // Start gathering stats.
      _statsButton.setDisable(false); // Users is now allowed to view stats.
    }
    else
    {
      _stopped = true;
      _startStopSim.setText("Start");
      Engine.getMessagePump().sendMessage(new Message(SimGlobals.RESET_SIM));
      // Start simulating movement
      Engine.getConsoleVariables().find(Singleton.CALCULATE_MOVEMENT).setValue("false");
      _setSpeedField.setDisable(false);
      _parkButton.setDisable(false);
      _reverseButton.setDisable(false);
      _neutralButton.setDisable(false);
      _driveButton.setDisable(false);
    }
  }

  // Initialize start/reset button.
  private void _initStartResetButton()
  {
     _startStopSim.setOnAction((event) -> {
      _setStartResetLogic();
    });
  }

  // Initialize hand brake button.
  // Kevin changed from activate_brake and deactivate_brake to is_Up and is_down
  // For sticky button changed to have everything be based off of released action
  private void _initHandBrakeButton()
  {
    // System.out.println("** Initializing handbrake\n  -isDisabled? = "+_handBrake.isDisabled());
    // Start with disabled handbrake
    _handBrake.setId("hand brake");
    _handBrake.setDisable(true);
//    _handBrake.setOnMouseReleased(event -> {
//      if(!_stopped)
//      {
//        if(!_previousState)
//        {
//          _handBrake.setText("Deactivate");
//          Engine.getMessagePump().sendMessage(new Message(SimGlobals.IS_DOWN));
//          _previousState = !_previousState;
//        }
//        else
//        {
//          _handBrake.setText("Activate Brake");
//          Engine.getMessagePump().sendMessage(new Message(SimGlobals.IS_UP));
//          _previousState = !_previousState;
//
//        }
//      }
//    });

//    If group doesn't like this revert here
    _handBrake.setOnMouseReleased(event -> {
      if(!_stopped)
      {
        _longHold = false;
      }
    });
    _handBrake.setOnMousePressed(event -> {
      if(!_stopped)
      {
        _handBrake.setText("Deactivate");
//        System.out.println("This is pressed");
        Engine.getMessagePump().sendMessage(new Message(SimGlobals.IS_PRESSED));
      }
    });
    _handBrake.setOnMouseClicked(event -> {
      if(!_stopped)
      {

          if (!_previousState)
          {
            _handBrake.setText("Deactivate");
//            System.out.println("This is short");
            Engine.getMessagePump().sendMessage(new Message(SimGlobals.IS_DOWN));
            _previousState = !_previousState;
            _longHold = true;
          }
          else
          {
            _handBrake.setText("Activate Brake");
            Engine.getMessagePump().sendMessage(new Message(SimGlobals.IS_UP));
            _previousState = !_previousState;

          }


      }
    });
    _handBrake.getParent().setOnKeyPressed(event -> {
      if(event.getCode() == KeyCode.ENTER) {
        if(!_stopped)
        {
          if(!_previousState)
          {
            _handBrake.setText("Deactivate");
            Engine.getMessagePump().sendMessage(new Message(SimGlobals.IS_DOWN));
            _previousState = !_previousState;
          }
          else
          {
            _handBrake.setText("Activate Brake");
            Engine.getMessagePump().sendMessage(new Message(SimGlobals.IS_UP));
            _previousState = !_previousState;

          }
        }
      }
    });

  }

  // Initialize gears.
  private void _initGears()
  {
    Engine.getConsoleVariables().loadConfigFile("src/simulator/resources/gearStates.cfg");
    _setDefaultStates();
    _parkButton.setToggleGroup(_group);
    _parkButton.setUserData("P");
    _neutralButton.setToggleGroup(_group);
    _neutralButton.setUserData("N");
    _driveButton.setToggleGroup(_group);
    _driveButton.setUserData("D");
    _reverseButton.setToggleGroup(_group);
    _reverseButton.setUserData("R");
    // We need to start in some gear.
    _driveButton.setSelected(true);
    Engine.getMessagePump().sendMessage(new Message(SimGlobals.GEAR_CHANGE, GearTypes.DRIVE));
    _initGear = GearTypes.DRIVE;

    _group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
      public void changed(ObservableValue<? extends Toggle> ov,
                          Toggle old_toggle, Toggle new_toggle) {
        if (_group.getSelectedToggle() != null)
        {
          String gearString = _group.getSelectedToggle().getUserData().toString();
          _currGear = gearString;
          Engine.getMessagePump().sendMessage(new Message(SimGlobals.GEAR_CHANGE, _getGear(gearString)));
          _initGear = _getGear(gearString);
          if(!_stopped)_setGearTransitions();
          if(gearString.equals("R")) _inReverse = true;
          else _inReverse = false;
        }
      }
    });
  }

  //This reads and interprets the default disallowed gear change states from gearStates.cfg.
  private void _setDefaultStates()
  {
    _invalidTransitions.clear();
    //if(Engine.getConsoleVariables().contains("default1")) _setRestrictedGear(Engine.getConsoleVariables().find("default1").getcvarValue());
    //if(Engine.getConsoleVariables().contains("default2")) _setRestrictedGear(Engine.getConsoleVariables().find("default2").getcvarValue());
    //if(Engine.getConsoleVariables().contains("default3")) _setRestrictedGear(Engine.getConsoleVariables().find("default3").getcvarValue());
    //if(Engine.getConsoleVariables().contains("default4")) _setRestrictedGear(Engine.getConsoleVariables().find("default4").getcvarValue());
  }

  //Set the current disallowed gear changes by disabling the necessary gear buttons.
  void _setGearTransitions()
  {
    // check if going too fast to shift to "Park"
    double currSpeed = SpeedInterface.getSpeed() / 0.448;
    boolean tooFastToPark = Math.abs(currSpeed) > MAX_PARKING_SPEED;

    /*System.out.println("_setGearTransitions(); _currGear = " + _currGear
            + "; currSpeed = " + currSpeed
            + "; tooFastToPark = " + tooFastToPark);

    for (String x : _invalidTransitions)
    {
      System.out.print(x + " ");
    }
    System.out.println();*/

    if (tooFastToPark)
    {
      if (!_invalidTransitions.contains("DP"))
        _setRestrictedGear("Drive->Park");
      if (!_invalidTransitions.contains("RP"))
        _setRestrictedGear("Reverse->Park");
      if (!_invalidTransitions.contains("NP"))
        _setRestrictedGear("Neutral->Park");
    }
    else
    {
      _invalidTransitions.remove("DP");
      _invalidTransitions.remove("RP");
      _invalidTransitions.remove("NP");

      _parkButton.setDisable(false);
    }

    for(int i = 0; i < _invalidTransitions.size(); i ++)
    {
      String transition = _invalidTransitions.get(i);
      if(_currGear.charAt(0) == transition.charAt(0))
      {
        char invalidGear = transition.charAt(1);
        if(invalidGear == 'D') _driveButton.setDisable(true);
        else if(invalidGear == 'R') _reverseButton.setDisable(true);
        else if(invalidGear == 'P') _parkButton.setDisable(true);
        else if(invalidGear == 'N') _neutralButton.setDisable(true);
      }
    }
  }

  /**
   *  Set a disallowed gear change.
   * @param gearString
   */
  private void _setRestrictedGear(String gearString)
  {
    switch(gearString)
    {
      case "Drive->Park":
        _invalidTransitions.add("DP");
        break;
      case "Drive->Reverse":
        _invalidTransitions.add("DR");
        break;
      case "Drive->Neutral":
        _invalidTransitions.add("DN");
        break;
      case "Park->Reverse":
        _invalidTransitions.add("PR");
        break;
      case "Park->Neutral":
        _invalidTransitions.add("PN");
        break;
      case "Park->Drive":
        _invalidTransitions.add("PD");
        break;
      case "Reverse->Park":
        _invalidTransitions.add("RP");
        break;
      case "Reverse->Neutral":
        _invalidTransitions.add("RN");
        break;
      case "Reverse->Drive":
        _invalidTransitions.add("RD");
        break;
      case "Neutral->Park":
        _invalidTransitions.add("NP");
        break;
      case "Neutral->Reverse":
        _invalidTransitions.add("NR");
        break;
      case "Neutral->Drive":
        _invalidTransitions.add("ND");
        break;
      default:
        System.err.println("Invalid gear transition.");
    }
  }

  /**
   *  Add reference to the GUI.
   * @param gui
   */
  void setGui(GUI gui)
  {
    this._guiRef = gui;
  }

  //Set speed entered into speed field when simulation begins.
  private void _setInitSpeed()
  {
    if(_setSpeedField.getText() != null && !_setSpeedField.getText().isEmpty())
    {
      try
      {
        _initSpeed = Double.parseDouble(_setSpeedField.getText());
      } catch (Exception e) {
        _initStatus = InitStatus.NON_NUMBER_ERROR;
      }
    }
    else
    {
      _initStatus = InitStatus.NO_INPUT_ENTERED_ERROR;
    }
  }

  /**
   * Set initial handbrake button color.
   */
  void setInitButtonColor()
  {
    _handBrake.setStyle(_buildCSSString());
  }

  private void _validate()
  {
    if(_initStatus != InitStatus.GOOD) return; // Error is known.
    if(_initGear == GearTypes.PARK && _initSpeed != 0)
    {
      _initStatus = InitStatus.INVALID_PARK_SPEED_ERROR;
      return;
    }
    if(_initGear == GearTypes.REVERSE && (_initSpeed > MAX_REVERSE_SPEED || _initSpeed < 0))
    {
      _initStatus = InitStatus.INVALID_REVERSE_SPEED_ERROR;
      return;
    }
    if(_initSpeed < 0 || _initSpeed > MAX_SPEED) _initStatus = InitStatus.INVALID_DRIVE_NEUTRAL_SPEED_ERROR;
  }

  /**
   * Update status to GOOD since error box was closed.
   */
  void errorPopupClosed() {_initStatus = InitStatus.GOOD;}

  /**
   * Get gear enum value.
   * @param s
   * @return the gear enum
   */
  private GearTypes _getGear(String s)
  {
    switch(s)
    {
      case "P": return GearTypes.PARK;
      case "R": return GearTypes.REVERSE;
      case "N": return GearTypes.NEUTRAL;
      case "D": return GearTypes.DRIVE;
      default:
        System.err.println("UNSUPPORTED GEAR, returning drive.");
        return GearTypes.DRIVE;
    }
  }
  //Launches stats screen.
  private void _InvokeOtherStage()
  {
    try
    {
      Stage newStage = new Stage();
      // Make the stage transparent.
      newStage.initStyle(StageStyle.TRANSPARENT);

      FXMLLoader fxmlLoader = new FXMLLoader(
              getClass().getResource("/simulator/resources/fxml/simData.fxml"));
      Parent root = fxmlLoader.load();
      _statController = (StatsPopupController) fxmlLoader.getController();
      Scene scene = new Scene(root);
      // Again needed for making the window
      // transparent.
      _statController.init(_statCollector);
      scene.setFill(Color.TRANSPARENT);
      newStage.setScene(scene);
      newStage.show();
    } catch (Exception e)
    {
      e.printStackTrace();
    }

  }
  //String used to format the handbrake button.
  private String _buildCSSString()
  {
    return "-fx-background-color:" + ((_buttonColor == null) ? "grey" : _buttonColor.toString())+";"
            + "-fx-background-radius:100;"
            + "-fx-border-width:0;"
            + "-fx-border-style:none;";
  }

  /**
   * Handles a SET_BUTTON_COLOR message by setting the color of the
   * handbrake button.
   */
  class ButtonMessageHelper implements MessageHandler
  {
    @Override
    public void handleMessage(Message message)
    {
      switch(message.getMessageName())
      {
        case SimGlobals.SET_BUTTON_COLOR:
          _buttonColor = (ButtonColorTypes) message.getMessageData();
          _handBrake.setStyle(_buildCSSString());
          break;

      }
    }
  }


}
