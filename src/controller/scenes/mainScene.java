package controller.scenes;

import java.io.IOException;
import java.util.Random;

import UI.NotificationPane;
import UI.ValueBar;
import UI.CircleButton;
import controller.ScenesController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;
import model.databaseControllers.ParticleController;
import model.databaseControllers.UserController;
import obv.particle.ParticleInfoTag;
import obv.particle.ParticlesContainer;

public class mainScene extends SceneTemplate {

	UserController userControll;
	ParticlesContainer particlesContainer;
	
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

		particlesContainer = new ParticlesContainer(getWidth(), getWidth(), userControll.getParticlesArray());
		container.getChildren().add(particlesContainer);

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
		
		info.setTranslateX(getWidth()/8);
		info.setTranslateY(getHeight()/20);
		particlesContainer.getChildren().add(info);
				
		ValueBar bar = new ValueBar(150, 5);
		bar.setTranslateX(250);
		bar.setTranslateY(50);
		bar.setMaxValue(100);
		bar.setValue(50);
		particlesContainer.getChildren().add(bar);
		
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
