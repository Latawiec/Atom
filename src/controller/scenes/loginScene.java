package controller.scenes;

import controller.ScenesController;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.util.Duration;
import model.database.ParticleDB;
import model.database.UserDB;
import model.database.UserParticleDB;
import model.databaseControllers.DatabaseAccessor;
import model.databaseControllers.ParticleController;
import obv.particle.ParticleView;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Latawiec on 26/05/2017.
 */
public class loginScene extends SceneTemplate {

    StackPane container;

    TextField usernameField;
    TextField passwordField;
    Label outputLabel;
    Button loginButton;

    public loginScene(ScenesController controller) {
        super(controller);

        container = new StackPane();
        usernameField = new TextField();
        passwordField = new PasswordField();
        outputLabel = new Label();
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
        outputLabel.setTextFill(Color.CRIMSON);
        usernameField.setPromptText("username");
        usernameField.setMaxWidth(100);
        passwordField.setPromptText("password");
        passwordField.setMaxWidth(100);
        Text atomLabel = new Text("A    T    O    M");
        atomLabel.setFont(Font.font("Helvetica", FontWeight.EXTRA_LIGHT, 18));
        Text creditsLabel = new Text("github.com/Latawiec");
        creditsLabel.setFont(Font.font("Helvetica", FontWeight.EXTRA_LIGHT, 10));
        creditsLabel.setFill(new Color(1,1,1,0.2f));

        FillTransition label = new FillTransition(Duration.seconds(2f), atomLabel, Color.TRANSPARENT, Color.WHITE);
        label.play();

        Rectangle background = new Rectangle();
        background.widthProperty().bind(widthProperty());
        background.heightProperty().bind(heightProperty());
        background.setFill(new Color(0.03, 0.03, 0.12, 1));

        container.setEffect(new GaussianBlur(1.5f));
        VBox stack = new VBox();
        ParticleView view = new ParticleView(new ParticleController(new UserParticleDB(new UserDB(), new ParticleDB(6, 6, new int[]{2, 4, 0, 0, 0, 0, 0}), 1)), widthProperty(), heightProperty());

        stack.setSpacing(10);
        stack.setAlignment(Pos.CENTER);
        view.setTranslateY(-100);
        stack.setTranslateY(200);
        atomLabel.setTranslateY(50);
        creditsLabel.translateYProperty().bind(heightProperty().divide(2).add(-10));
        container.getChildren().addAll(background, view, stack, atomLabel, creditsLabel);
        stack.getChildren().addAll(usernameField, passwordField, loginButton, outputLabel);

        root.getChildren().add(container);
    }

    private void tryLogIn(String username, String password) throws SQLException {
        UserDB user = DatabaseAccessor.getInstance().getUser(username, password);
        if( user != null){
                controller.setUser(user);
                outputLabel.setTextFill(Color.GREEN);
                outputLabel.setText("Access granted! Logging in...");
                Timeline tl = new Timeline();
                tl.getKeyFrames().add(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            controller.setScene(ScenesController.Scenes.Main);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }));
                tl.play();

        }else{
            usernameField.setText("");
            passwordField.setText("");
            Timeline tl = new Timeline();
            tl.getKeyFrames().add(new KeyFrame(Duration.ZERO, new KeyValue(outputLabel.opacityProperty(), 0)));
            tl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5f), new KeyValue(outputLabel.opacityProperty(), 1)));
            tl.play();
            outputLabel.setText("Incorrect password or username.");
        }
    }
}
