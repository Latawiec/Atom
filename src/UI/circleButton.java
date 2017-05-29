package UI;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.*;
import javafx.util.Duration;


/**
 * Created by Latawiec on 28/05/2017.
 */
public class circleButton extends StackPane {


    Text label = new Text();
    Ellipse ellipse = new Ellipse();
    Ellipse backEllipse = new Ellipse();
    private Timeline showLabel = new Timeline();
    private Timeline hideLabel = new Timeline();
    private int direction = 1;

    public circleButton(String text, double buttonSize, boolean dir){
        super();

        if(dir){
            label.setTextAlignment(TextAlignment.LEFT);
            direction = 1;
        }else{
            label.setTextAlignment(TextAlignment.RIGHT);
            direction = -1;
        }

        setMaxHeight(buttonSize*2);
        setMaxWidth(buttonSize*2);

        ellipse.setRadiusX(buttonSize);
        ellipse.setRadiusY(buttonSize);
        ellipse.setFill(Color.WHITE);
        ellipse.setCenterX(buttonSize);
        ellipse.setCenterY(buttonSize);

        backEllipse.setRadiusX(buttonSize);
        backEllipse.setRadiusY(buttonSize);
        backEllipse.setFill(Color.WHITE);
        backEllipse.setCenterX(buttonSize);
        backEllipse.setCenterY(buttonSize);
        backEllipse.setOpacity(1);


        label.setPickOnBounds(false);
        label.setFill(Color.WHITE);
        label.setText(text.toUpperCase());
        label.setFont(Font.font("Helvetica", FontWeight.EXTRA_LIGHT, buttonSize));
        label.setFontSmoothingType(FontSmoothingType.LCD);
        label.setOpacity(0);
        label.setTextAlignment(TextAlignment.LEFT);

        ellipse.setOnMouseEntered(e->{
            mouseEntered();
        });
        ellipse.setOnMouseExited(e->{
            mouseOff();
        });

        float duration = 0.3f;
        showLabel.getKeyFrames().add(new KeyFrame(Duration.seconds(duration), new KeyValue(label.opacityProperty(), 1, Interpolator.EASE_OUT)));
        showLabel.getKeyFrames().add(new KeyFrame(Duration.seconds(duration), new KeyValue(label.translateXProperty(), direction*label.getLayoutBounds().getWidth(), Interpolator.EASE_OUT)));

        showLabel.getKeyFrames().add(new KeyFrame(Duration.seconds(duration), new KeyValue(backEllipse.scaleXProperty(), 1.3f, Interpolator.EASE_OUT)));
        showLabel.getKeyFrames().add(new KeyFrame(Duration.seconds(duration), new KeyValue(backEllipse.scaleYProperty(), 1.3f, Interpolator.EASE_OUT)));
        showLabel.getKeyFrames().add(new KeyFrame(Duration.seconds(duration), new KeyValue(backEllipse.opacityProperty(), 0.5f, Interpolator.EASE_OUT)));

        hideLabel.getKeyFrames().add(new KeyFrame(Duration.seconds(duration), new KeyValue(label.opacityProperty(), 0, Interpolator.EASE_IN)));
        hideLabel.getKeyFrames().add(new KeyFrame(Duration.seconds(duration), new KeyValue(label.translateXProperty(), direction*label.getLayoutBounds().getWidth()*0.3f, Interpolator.EASE_IN)));

        hideLabel.getKeyFrames().add(new KeyFrame(Duration.seconds(duration), new KeyValue(backEllipse.scaleXProperty(), 0.9f, Interpolator.EASE_OUT)));
        hideLabel.getKeyFrames().add(new KeyFrame(Duration.seconds(duration), new KeyValue(backEllipse.scaleYProperty(), 0.9f, Interpolator.EASE_OUT)));
        hideLabel.getKeyFrames().add(new KeyFrame(Duration.seconds(duration), new KeyValue(backEllipse.opacityProperty(), 1, Interpolator.EASE_OUT)));

        getChildren().addAll(backEllipse, ellipse, label);
    }

    private void mouseEntered(){
        hideLabel.stop();
        showLabel.play();
    }

    private void mouseOff(){
        showLabel.stop();
        hideLabel.play();
    }

}
