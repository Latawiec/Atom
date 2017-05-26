package controller;

import controller.scenes.loginScene;
import controller.scenes.mainScene;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Latawiec on 26/05/2017.
 */
public class ScenesController {

    public enum Scenes{
        Login,
        Main,
        Collider,
        User
    }

    private Stage mainStage;

    private double width;
    public double getWidth(){ return width; }
    private double height;
    public double getHeight(){ return height; }

    public ScenesController(Stage mainStage, double width, double height) throws IOException {
        this.mainStage = mainStage;
        //mainStage.setResizable(false);
        mainStage.setWidth(width);
        mainStage.setHeight(height);
        this.width = width;
        this.height = height;
        setScene(Scenes.Login);
    }

    public void setScene(Scenes scene) throws IOException {

        switch (scene){
            case Login:
                changeScene(new loginScene(this));
                break;
            case Main:
                changeScene(new mainScene(this));
                break;
        }
    }

    private void changeScene(Scene scene){
        mainStage.setScene(scene);
        mainStage.show();
    }
}
