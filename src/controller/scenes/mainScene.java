package controller.scenes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import UI.LabeledValue;
import UI.NotificationPane;
import UI.Notifications.ActionsNotification;
import UI.Notifications.ExchangeNotification;
import UI.ValueBar;
import UI.CircleButton;
import com.sun.javafx.css.CalculatedValue;
import controller.ScenesController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.databaseControllers.DatabaseAccessor;
import model.databaseControllers.ParticleController;
import model.databaseControllers.UserController;
import obv.particle.ParticleInfoTag;
import obv.particle.ParticlesContainer;

public class mainScene extends SceneTemplate {

	UserController userControll;
	ParticlesContainer particlesContainer;
	VBox verticalStack = new VBox();
	ParticleInfoTag info;
	HBox buttons = new HBox();


	private IntegerProperty selectedParticle = new SimpleIntegerProperty();
	public void updateInfo(){
		final ParticleController particle = userControll.getParticlesArray().get(particlesContainer.getSelectedParticle());

		info.setNameTag(particle.getNameTag());
		info.setName(particle.getName());
		info.setMass(particle.getMass());
		info.setBindingEnergy(particle.getBindingEnergy());
		info.getEnergyProperty().unbind();
		info.getEnergyProperty().bind(particle.getEnergyProperty());
		info.setAtomicNumber(particle.getProtons());
		info.setMassNumber(particle.getProtons() + particle.getNeutrons());
		info.setEnergized(particle.isEnergized());

	}
	public int getSelectedParticle() { return selectedParticle.get(); }
	
	public mainScene(ScenesController controller) throws IOException {
		super(controller);

		userControll = new UserController(controller.getUser());
		container.getChildren().add(verticalStack);
		//container.setBackground(new Background(new BackgroundFill(new Color(0,0,1,1), null, null)));
		particlesContainer = new ParticlesContainer(getWidth(), getWidth(), userControll.getParticlesArray());
		//verticalStack.setAlignment(Pos.CENTER);
		selectedParticle.bind(particlesContainer.selectedParticle);
		selectedParticle.addListener(e->{
			updateInfo();
		});

		CircleButton colliderButton = new CircleButton("collider", 10, false, false);
		colliderButton.setOnMousePressed(e->{
			try {
				userControll.saveParticles();
				controller.setScene(ScenesController.Scenes.Collider);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		//colliderButton.setTranslateY(300);

		CircleButton actionsButton = new CircleButton("actions", 10, false, false);
		actionsButton.setOnMousePressed(e->{
			popNotification(new ActionsNotification(userControll, userControll.getParticlesArray().get(getSelectedParticle())));
		});

		CircleButton deleteButton = new CircleButton("delete", 10, false, false);
		deleteButton.setOnMousePressed(e->{
			if(userControll.getParticlesArray().size() >= 1){
				ParticleController particle = userControll.getParticlesArray().get(getSelectedParticle());
				try {
					DatabaseAccessor.getInstance().deleteUserParticle(particle.getSourceDB());
					particlesContainer.removeParticle(getSelectedParticle());
					userControll.getParticlesArray().remove(particle);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		CircleButton exchangeButton = new CircleButton("exchange", 10, false, false);
		exchangeButton.setOnMousePressed(e->{
			popNotification(new ExchangeNotification(userControll, particlesContainer));
		});

		buttons.setAlignment(Pos.CENTER);
		buttons.setFillHeight(true);
		buttons.getChildren().addAll(colliderButton, actionsButton, deleteButton, exchangeButton);

		info = new ParticleInfoTag(70, 90);
		info.translateXProperty().bind(widthProperty().divide(2).subtract(info.getWidth()/2));
		info.setTranslateY(50);

		/*LabeledValue energyLabel = new LabeledValue("Energy");
		energyLabel.getValueProperty().bind(userControll.getEnergyProperty().asString());
		energyLabel.translateXProperty().bind(widthProperty().divide(2));*/

		verticalStack.setSpacing(20);
		verticalStack.getChildren().addAll(info, particlesContainer, buttons/*, energyLabel*/);
		/*------------*/
		
		/*Timeline energyAdder = new Timeline();
		energyAdder.getKeyFrames().add(new KeyFrame(Duration.seconds(1), null));
		energyAdder.setOnFinished(e->{
			for(ParticleController p : userControll.getParticlesArray()){
				Timeline tl = new Timeline();
				tl.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new KeyValue(p.getEnergyProperty(), p.getEnergy() + p.getBindingEnergy()*0.01f)));
				tl.setOnFinished(et->{
					p.setEnergy(p.getEnergy());					
				});
				tl.play();
			}
			energyAdder.play();
		});
		energyAdder.play();*/
		/*------------*/
		particlesContainer.setSelectedParticle(0);
		updateInfo();
		setOnKeyPressed(e->{

				switch(e.getCode()){
				case LEFT:
					particlesContainer.setSelectedParticle(getSelectedParticle() - 1);
					break;
				case RIGHT:
					particlesContainer.setSelectedParticle(getSelectedParticle() + 1);
					break;
				case SPACE:

					break;
				}
		});
	}
}
