package controller.scenes;

import application.Main;
import controller.ScenesController;
import javafx.animation.FillTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.*;
import javafx.util.Duration;
import model.Particle.ParticleModel;
import model.databaseControllers.DatabaseAccessor;
import obv.particle.ParticleView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;


/**
 * Created by Latawiec on 26/05/2017.
 */
public class loginScene extends SceneTemplate {

    StackPane container;

    TextField usernameField;
    TextField passwordField;
    Button loginButton;

    public loginScene(ScenesController controller) {
        super(controller);

        container = new StackPane();
        usernameField = new TextField();
        passwordField = new PasswordField();
        loginButton = new Button("Log in");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    tryLogIn(usernameField.getText(), passwordField.getText());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        usernameField.setMaxWidth(100);
        passwordField.setMaxWidth(100);
        Text atomLabel = new Text("A    T    O    M");
        atomLabel.setFont(Font.font("Helvetica", FontWeight.EXTRA_LIGHT, 18));

        FillTransition label = new FillTransition(Duration.seconds(2f), atomLabel, Color.TRANSPARENT, Color.WHITE);
        label.play();

        Rectangle background = new Rectangle();
        background.widthProperty().bind(widthProperty());
        background.heightProperty().bind(heightProperty());
        background.setFill(new Color(0.03, 0.03, 0.12, 1));

        container.setEffect(new GaussianBlur(1.5f));
        VBox stack = new VBox();
        ParticleView view = new ParticleView(new ParticleModel(6, 	6, new int[]{2, 4}), widthProperty(), heightProperty());
        stack.setSpacing(10);
        stack.setAlignment(Pos.CENTER);
        view.setTranslateY(-100);
        stack.setTranslateY(200);
        container.getChildren().addAll(background, view, stack, atomLabel);
        stack.getChildren().addAll(usernameField, passwordField, loginButton);

        root.getChildren().add(container);
    }

    private void tryLogIn(String username, String password) throws SQLException {
        if(DatabaseAccessor.getInstance().getUser(username, password) != null){
            try {
                controller.setScene(ScenesController.Scenes.Main);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{

        }
    }
}
