package controller.scenes.admin;

import UI.CircleButton;
import UI.Notifications.admin.AddUserNotification;
import controller.ScenesController;
import controller.scenes.SceneTemplate;

/**
 * Created by Latawiec on 03/06/2017.
 */
public class adminMainScene extends SceneTemplate {

    CircleButton addUserButton = new CircleButton("Add User", 15, true);

    public adminMainScene(ScenesController controller) {
        super(controller);
        container.getChildren().addAll(addUserButton);

        addUserButton.setOnMousePressed(e->{
            popNotification(new AddUserNotification());
        });
    }
}
