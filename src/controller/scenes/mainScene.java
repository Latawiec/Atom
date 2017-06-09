package controller.scenes;

import java.io.IOException;
import java.util.Random;

import UI.LabeledValue;
import UI.NotificationPane;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.databaseControllers.ParticleController;
import model.databaseControllers.UserController;
import obv.particle.ParticleInfoTag;
import obv.particle.ParticlesContainer;

public class mainScene extends SceneTemplate {

	UserController userControll;
	ParticlesContainer particlesContainer;
	VBox verticalStack = new VBox();
	ParticleInfoTag info;
		
	private IntegerProperty selectedParticle = new SimpleIntegerProperty();
	public void setSelectedParticle(int value){
		if(value >= 0 && value < userControll.getParticlesCount()){
			particlesContainer.setSelectedParticle(value);
			selectedParticle.set(value);
			
			final ParticleController particle = userControll.getParticlesArray().get(value);
			
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
	}
	public int getSelectedParticle() { return selectedParticle.get(); }
	
	public mainScene(ScenesController controller) throws IOException {
		super(controller);

		userControll = new UserController(controller.getUser());
		container.getChildren().add(verticalStack);
		//container.setBackground(new Background(new BackgroundFill(new Color(0,0,1,1), null, null)));

		particlesContainer = new ParticlesContainer(getWidth(), getWidth(), userControll.getParticlesArray());
		//verticalStack.setAlignment(Pos.CENTER);

		CircleButton colliderButton = new CircleButton("collider", 15, false);
		colliderButton.setOnMousePressed(e->{
			try {
				userControll.saveParticles();
				controller.setScene(ScenesController.Scenes.Collider);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		colliderButton.setTranslateY(300);
		container.getChildren().add(colliderButton);

		info = new ParticleInfoTag(70, 90);

		LabeledValue energyLabel = new LabeledValue("Energy");
		energyLabel.getValueProperty().bind(userControll.getEnergyProperty().asString());

		verticalStack.getChildren().addAll(info, particlesContainer, energyLabel);
		/*------------*/
		
		Timeline energyAdder = new Timeline();
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
		energyAdder.play();
		/*------------*/
		setSelectedParticle(0);
		setOnKeyPressed(e->{

				Random rand = new Random();
				Timeline tl = new Timeline();
				switch(e.getCode()){
				case LEFT:
					setSelectedParticle(getSelectedParticle() - 1);
					break;
				case RIGHT:
					setSelectedParticle(getSelectedParticle() + 1);
					break;
				case SPACE:
					popNotification(new NotificationPane());
					break;
				}
		});
	}
}
