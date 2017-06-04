package controller;

import controller.scenes.admin.adminMainScene;
import controller.scenes.colliderScene;
import controller.scenes.loginScene;
import controller.scenes.mainScene;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.database.UserDB;

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

    private UserDB user;
    public UserDB getUser(){ return user; }
    public void setUser(UserDB user) { this.user = user; }

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
                if(user.getAccountType()){
                    changeScene(new adminMainScene(this));
                }else{
                    changeScene(new mainScene(this));
                }
                break;
            case Collider:
                changeScene(new colliderScene(this));
                break;
        }
    }

    private void changeScene(Scene scene){
        mainStage.setScene(scene);
        mainStage.show();
    }
}
