package UI.Notifications.admin;

import UI.CircleButton;
import UI.NotificationPane;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.databaseControllers.DatabaseAccessor;

import java.sql.SQLException;

/**
 * Created by Latawiec on 13/06/2017.
 */
public class AddActionNotification extends NotificationPane {

    TextField nameField = new TextField();
    TextField costField = new TextField();
    TextField incomeField = new TextField();
    VBox mainContainer = new VBox();

    CircleButton addUserButton = new CircleButton("Create", 15, true, true);
    public AddActionNotification(){
        super();
        nameField.setPromptText("name");
        nameField.setMaxWidth(100);

        costField.setPromptText("cost");
        costField.setMaxWidth(100);

        incomeField.setPromptText("income");
        incomeField.setMaxWidth(100);

        addUserButton.setOnMousePressed(e->{
            createAction();
        });

        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setSpacing(20);

        mainContainer.getChildren().addAll(nameField, costField, incomeField, addUserButton);
        container.getChildren().add(mainContainer);
    }

    private void createAction(){
        if(nameField.getText() != null && costField.getText()!= null && incomeField.getText() != null){
            try {
                DatabaseAccessor.getInstance().createAction(nameField.getText(), Float.parseFloat(costField.getText()), Float.parseFloat(incomeField.getText()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
