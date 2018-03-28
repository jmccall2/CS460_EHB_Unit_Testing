package demo;

import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.*;

import logic_layer.State;

public class DemoVisual
{

    private static HashMap <State, Rectangle> _stateMapping;
    private static StackPane _sp;
    private static ImageView _diagram;
    private static State _currState = null;
    private static State _power = null;
    private static List<State> _powerLevels = Arrays.asList(State.LOW_BRAKING_MODE, State.MED_BRAKING_MODE, State.HIGH_BRAKING_MODE);
    private static Stage _newState = null;

    public static void demoPopup()
    {
        try
        {
            if(_newState != null) _newState.close();
            _newState = new Stage();
            Image img = (new Image(DemoVisual.class.getResourceAsStream("/demo/logic-diagram-new-v3-1.png")));
            _diagram = new ImageView(img);
            _diagram.setFitHeight(700);
            _diagram.setFitWidth(800);
            DropShadow g = new DropShadow();
            g.setOffsetY(0f);
            g.setOffsetX(0f);
            g.setColor(Color.GREEN);
            g.setWidth(70);
            g.setHeight(70);
            Rectangle lowMode = generateRegion(80,44,5,61,g);
            Rectangle medMode = generateRegion(80,44,-120,61,g);
            Rectangle highMode = generateRegion(80,44,-245,61,g);
            Rectangle emergencyMode = generateRegion(120,55,-120,236,g);
            Rectangle parkedMode = generateRegion(89,59,205,0,g);
            Rectangle disengagedMode = generateRegion(75,52,42,-235,g);

            _stateMapping = new HashMap<State,Rectangle>(){{
                put(State.LOW_BRAKING_MODE, lowMode);
                put(State.MED_BRAKING_MODE, medMode);
                put(State.HIGH_BRAKING_MODE, highMode);
                put(State.BRAKE_ENGAGING, emergencyMode);
                put(State.BRAKE_ENGAGED, parkedMode);
                put(State.BRAKE_DISENGAGED, disengagedMode);
            }};
            _sp = new StackPane();
            _sp.getChildren().add(_diagram);
            _diagram.toBack();
            _sp.getChildren().addAll(lowMode, medMode, highMode, emergencyMode, disengagedMode, parkedMode);
            Scene scene = new Scene(_sp);
            _newState.setScene(scene);
            _newState.show();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void refresh(State currState)
    {
        if(currState == _currState) return;
        if(_powerLevels.contains(currState))
        {
            _power = currState;
            return;
        }
        _currState = currState;
        _sp.getChildren().clear();
        _sp.getChildren().add(_diagram);
        _diagram.toBack();
        for (Map.Entry<State, Rectangle> entry : _stateMapping.entrySet()) {
            State s = entry.getKey();
            Rectangle r = entry.getValue();
            if(s == _currState || (_currState == State.BRAKE_ENGAGING && s == _power)) r.setStyle("-fx-fill: rgba(0, 255, 0, .2);");
            else r.setStyle(null);
             _sp.getChildren().add(r);


        }

    }

    private static Rectangle generateRegion(int w, int h, int tx, int ty, DropShadow g)
    {
        Rectangle region = new Rectangle();
        region.setWidth(w);
        region.setHeight(h);
        region.setArcWidth(20);
        region.setArcHeight(20);
        region.setTranslateX(tx);
        region.setTranslateY(ty);
        region.setFill(Color.TRANSPARENT);
        region.setEffect(g);

        return region;
    }

}
