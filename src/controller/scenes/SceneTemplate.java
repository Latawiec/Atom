package controller.scenes;

import controller.ScenesController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Created by Latawiec on 26/05/2017.
 */
public abstract class SceneTemplate extends Scene {

    protected ScenesController controller;
    protected StackPane root;

    public SceneTemplate(ScenesController controller){
        super(new StackPane(), controller.getWidth(), controller.getHeight(), true, SceneAntialiasing.BALANCED);
        this.root = (StackPane) getRoot();
        this.controller = controller;
    }
}
