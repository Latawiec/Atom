package UI.Notifications;

import UI.LabeledValue;
import UI.NotificationPane;
import UI.Text.ActionLabel;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import model.database.ActionDB;
import model.databaseControllers.ActionController;
import model.databaseControllers.DatabaseAccessor;
import model.databaseControllers.ParticleController;
import model.databaseControllers.UserController;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Latawiec on 12/06/2017.
 */
public class ActionsNotification extends NotificationPane {

    VBox flow = new VBox();
    ArrayList<ActionController> actions = new ArrayList<>();
    UserController userController;

    public ActionsNotification(UserController controller, ParticleController chosenParticle){
        super();
        setHeader("Actions");
        container.setPadding(new Insets(30,30,30,30));
        userController = controller;

        GridPane grid = new GridPane();
        container.getChildren().add(grid);

        RowConstraints row1 = new RowConstraints();
        row1.setValignment(VPos.CENTER);
        row1.setVgrow(Priority.ALWAYS);
        row1.setPercentHeight(90);

        RowConstraints row2 = new RowConstraints();
        row2.setValignment(VPos.CENTER);
        row2.setVgrow(Priority.ALWAYS);
        row2.setPercentHeight(10);

        grid.getRowConstraints().addAll(row1, row2);
        grid.add(flow, 0, 0);

        LabeledValue energyLabel = new LabeledValue("Energy");
        energyLabel.getValueProperty().bind(controller.getEnergyProperty().asString());
        grid.add(energyLabel, 0, 1);

        loadActions();
        flow.setSpacing(10);
        for(ActionController action : actions){
            flow.getChildren().add(new ActionLabel(action.getEnergyCostProperty(), action.getNameProperty(), action.getEnergyIncomeProperty()){
                @Override
                public void onClick(){
                    if(userController.getEnergy() >= action.getEnergyCost()){
                        userController.setEnergy(userController.getEnergy() - action.getEnergyCost());
                        chosenParticle.setEnergy(chosenParticle.getEnergy() + action.getEnergyIncome());
                    }
                }
            });
        }
    }

    private void loadActions(){
        try {
            for(ActionDB source : DatabaseAccessor.getInstance().getActions()){
                actions.add(new ActionController(source));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
