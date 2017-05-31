package controller.scenes;

import UI.NotificationPane;
import controller.ScenesController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


/**
 * Created by Latawiec on 26/05/2017.
 */
public abstract class SceneTemplate extends Scene {

    protected ScenesController controller;
    protected StackPane root;
    protected StackPane container;
    private Rectangle background = new Rectangle();
    
    private GaussianBlur blur = new GaussianBlur(0f);
    
    public SceneTemplate(ScenesController controller){
        super(new StackPane(), controller.getWidth(), controller.getHeight(), true, SceneAntialiasing.BALANCED);
        this.root = (StackPane) getRoot();
        this.controller = controller;
        container = new StackPane();
        container.setEffect(blur);
        root.setEffect(new GaussianBlur(1.5f));

        background.widthProperty().bind(widthProperty());
        background.heightProperty().bind(heightProperty());
        background.setFill(new Color(0.03, 0.03, 0.12, 1));
        root.getChildren().addAll(background, container);
    }
    
    public void popNotification(NotificationPane content){
    	Timeline tl = new Timeline();
    	content.setOpacity(0);
    	root.getChildren().add(content);
    	tl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5f), new KeyValue(content.opacityProperty(), 1)));
    	tl.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new KeyValue(blur.radiusProperty(), 50)));
    	content.getCloseButton().setOnMouseClicked(e->{
    		Timeline close = new Timeline();
    		close.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5f), new KeyValue(content.opacityProperty(), 0)));
    		close.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new KeyValue(blur.radiusProperty(), 1.5f)));
    		close.setOnFinished(ev->{
    			root.getChildren().remove(content);
    		});
    		close.play();
    	});
    	tl.play();
    }
}
