package UI.Notifications.admin;

import UI.CircleButton;
import UI.NotificationPane;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.databaseControllers.DatabaseAccessor;

import java.sql.SQLException;

/**
 * Created by Latawiec on 04/06/2017.
 */
public class AddUserNotification extends NotificationPane {

    TextField usernameField = new TextField();
    TextField passwordField = new TextField();
    VBox mainContainer = new VBox();

    CircleButton addUserButton = new CircleButton("Create", 15, true, true);
    public AddUserNotification(){
        super();
        usernameField.setPromptText("username");
        usernameField.setMaxWidth(100);
        passwordField.setPromptText("password");
        passwordField.setMaxWidth(100);

        addUserButton.setOnMousePressed(e->{
            createUser();
        });

        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setSpacing(20);

        mainContainer.getChildren().addAll(usernameField, passwordField, addUserButton);
        container.getChildren().add(mainContainer);
    }

    private void createUser(){
        if(usernameField.getText() != null && passwordField.getText()!= null){
            try {
                DatabaseAccessor.getInstance().createUser(usernameField.getText(), passwordField.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
