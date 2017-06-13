package UI.Notifications;

import UI.CircleButton;
import UI.LabeledValue;
import UI.NotificationPane;
import UI.Text.ActionLabel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.*;
import model.database.ActionDB;
import model.database.ExchangePositionDB;
import model.database.UserParticleDB;
import model.databaseControllers.*;
import obv.particle.ParticlesContainer;
import view.collider.BasicInfoTag;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Latawiec on 13/06/2017.
 */
public class ExchangeNotification extends NotificationPane {

    ParticlesContainer positionsContainer;
    ArrayList<ParticleController> positionsControllers = new ArrayList<>();
    ArrayList<ExchangePositionController> exchangePositions = new ArrayList<>();
    UserController userController;

    public ExchangeNotification(UserController controller, ParticlesContainer pContainerRef){
        super();
        setHeader("Exchange");
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
        loadPositions();
        StackPane center = new StackPane();
        positionsContainer = new ParticlesContainer(250, 250, positionsControllers);
        positionsContainer.setScaleX(0.8f);
        positionsContainer.setScaleY(0.8f);
        positionsContainer.setSelectedParticle(0);
        center.getChildren().add(positionsContainer);
        CircleButton left = new CircleButton("left", 10, true, true);
        CircleButton right = new CircleButton("right", 10, false, true);
        center.getChildren().addAll(left, right);
        left.translateXProperty().bind(container.widthProperty().divide(2).multiply(-1));
        right.translateXProperty().bind(container.widthProperty().divide(2));
        left.setOnMousePressed(e->{
            positionsContainer.setSelectedParticle(positionsContainer.getSelectedParticle() - 1);
        });
        right.setOnMousePressed(e->{
            positionsContainer.setSelectedParticle(positionsContainer.getSelectedParticle() + 1);
        });

        grid.add(center, 0, 0);

        LabeledValue energyLabel = new LabeledValue("Energy");
        energyLabel.getValueProperty().bind(controller.getEnergyProperty().asString());

        BasicInfoTag tag = new BasicInfoTag("", 0, 0);
        positionsContainer.getSelectedParticleProperty().addListener(e->{
            tag.setTag(positionsControllers.get(positionsContainer.getSelectedParticle()).getNameTag());
            tag.setMassNumber(positionsControllers.get(positionsContainer.getSelectedParticle()).getNucleons());
            tag.setAtomicNumber(positionsControllers.get(positionsContainer.getSelectedParticle()).getProtons());
        });
        tag.setTranslateX(10);
        tag.setTranslateY(10);

        CircleButton buyButton = new CircleButton("buy", 10, false, false);
        buyButton.setOnMousePressed(e->{
            if(userController.getEnergy() >= exchangePositions.get(positionsContainer.getSelectedParticle()).getEnergyCost().get()){
                pContainerRef.addParticle(positionsControllers.get(positionsContainer.getSelectedParticle()));
                userController.getParticlesArray().add(new ParticleController (new UserParticleDB(userController.getSourceDB(), exchangePositions.get(positionsContainer.getSelectedParticle()).getSource().getParticle() , 0)));
                userController.setEnergy(userController.getEnergy() - exchangePositions.get(positionsContainer.getSelectedParticle()).getEnergyCost().get());
                userController.save();
            }
        });

        HBox hbox = new HBox();
        hbox.getChildren().addAll(energyLabel, tag, buyButton);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(100);
        grid.add(hbox, 0, 1);
        tag.setTag(positionsControllers.get(positionsContainer.getSelectedParticle()).getNameTag());
        tag.setMassNumber(positionsControllers.get(positionsContainer.getSelectedParticle()).getNucleons());
        tag.setAtomicNumber(positionsControllers.get(positionsContainer.getSelectedParticle()).getProtons());
    }

    private void loadPositions(){
        try {
            for(ExchangePositionDB source : DatabaseAccessor.getInstance().getExchangePositions()){
                positionsControllers.add(new ParticleController(source.getParticle()));
                exchangePositions.add(new ExchangePositionController(source));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
