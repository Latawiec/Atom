package view.collider;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.StrokeLineCap;
import model.databaseControllers.ParticleController;
import obv.particle.ParticleView;

/**
 * Created by Latawiec on 28/05/2017.
 */
public class ChainParticleView extends ParticleView {

    BasicInfoTag infoTag;
    Ellipse ellipse;

    public ChainParticleView(ParticleController controller, ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty) {
        super(controller, widthProperty, heightProperty);
        infoTag = new BasicInfoTag(controller.getNameTag(), controller.getProtons(), controller.getNeutrons());
        ellipse = new Ellipse();
        ellipse.setFill(Color.TRANSPARENT);
        ellipse.setStrokeWidth(2);
        ellipse.setStroke(Color.WHITE);
        ellipse.setStrokeLineCap(StrokeLineCap.ROUND);
        ellipse.getStrokeDashArray().addAll(0.0, 50.0, 10000.0);

        ellipse.centerXProperty().bind(ellipse.radiusXProperty());
        ellipse.centerYProperty().bind(ellipse.radiusYProperty());
        ellipse.setRotate(155);
        ellipse.setRadiusX(60);
        ellipse.setRadiusY(60);

        infoTag.translateXProperty().bind(widthProperty.divide(2).subtract(60));
        infoTag.translateYProperty().bind(heightProperty.divide(2));
        getChildren().add(infoTag);
        getChildren().add(ellipse);
    }

    public void setStable(){
        ellipse.setStroke(Color.GREEN);
    }

    public void setUnstable(){
        ellipse.setStroke(Color.CRIMSON);
    }
}
