package controller.scenes.admin;

import UI.CircleButton;
import UI.Notifications.admin.AddActionNotification;
import UI.Notifications.admin.AddUserNotification;
import controller.ScenesController;
import controller.scenes.SceneTemplate;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * Created by Latawiec on 03/06/2017.
 */
public class adminMainScene extends SceneTemplate {

    CircleButton addUserButton = new CircleButton("Add User", 15, true, true);
    CircleButton addActionButton = new CircleButton("Add Action", 15, true, true);
    CircleButton addExchangePositionButton = new CircleButton("Add Exchange", 15, true, true);

    private VBox vbox = new VBox();

    public adminMainScene(ScenesController controller) {
        super(controller);
        container.getChildren().add(vbox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(30);
        vbox.getChildren().addAll(addUserButton, addActionButton, addExchangePositionButton);
        addUserButton.setOnMousePressed(e->{
            popNotification(new AddUserNotification());
        });

        addActionButton.setOnMousePressed(e->{
            popNotification(new AddActionNotification());
        });



    }
}
