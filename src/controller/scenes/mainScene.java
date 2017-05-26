package controller.scenes;

import java.io.IOException;
import java.util.Random;

import UI.ValueBar;
import controller.ScenesController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Particle.ParticleModel;
import model.Particle.UserdataModel;
import obv.particle.ParticleInfoTag;
import obv.particle.ParticlesContainer;

public class mainScene extends SceneTemplate {

	UserdataModel userModel;
	ParticlesContainer particlesContainer;
	
	ParticleInfoTag info;
		
	private IntegerProperty selectedParticle = new SimpleIntegerProperty();
	public void setSelectedParticle(int value){
		if(value >= 0 && value < userModel.getParticlesCount()){
			particlesContainer.setSelectedParticle(value);
			selectedParticle.set(value);
			
			final ParticleModel particle = userModel.getParticles().get(value);
			
			info.setNameTag(particle.getNameTag());
			info.setName(particle.getName());
			info.setMass(particle.getMass());
			info.setAtomicNumber(particle.getProtons());
			info.setMassNumber(particle.getProtons() + particle.getNeutrons());
		}
	}
	public int getSelectedParticle() { return selectedParticle.get(); }
	
	public mainScene(ScenesController controller) throws IOException {
		super(controller);

		userModel = new UserdataModel();
		userModel.getParticles().add(new ParticleModel(0, 1, new int[]{1}, "Hydrogen", "H", 1.008f));
		userModel.getParticles().add(new ParticleModel(1, 1, new int[]{1}, "Deuterium", "D", 2.016f));
		userModel.getParticles().add(new ParticleModel(2, 2, new int[]{2}, "Helium", "He", 4.002f));
		userModel.getParticles().add(new ParticleModel(143, 92, new int[]{2, 8, 18, 32, 21, 9, 2}, "Uranium", "U", 238));
		particlesContainer = new ParticlesContainer(getWidth(), getWidth(), userModel.getParticles());
		Rectangle background = new Rectangle();
		background.widthProperty().bind(widthProperty());
		background.heightProperty().bind(heightProperty());
		background.setFill(new Color(0.03, 0.03, 0.12, 1));
		root.getChildren().add(background);
		root.getChildren().add(particlesContainer);
		
		/*--------------*/
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
		
		GaussianBlur blur = new GaussianBlur(1.5f);
		particlesContainer.setEffect(blur);
		setSelectedParticle(0);
		setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent arg0) {
				Random rand = new Random();
				Timeline tl = new Timeline();
				switch(arg0.getCode()){
				case LEFT:
					setSelectedParticle(getSelectedParticle() - 1);
					break;
				case RIGHT:
					setSelectedParticle(getSelectedParticle() + 1);
					break;
				case SPACE:
					tl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5f), new KeyValue(blur.radiusProperty(), 20)));
					tl.play();
					break;
				case A:
					tl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5f), new KeyValue(blur.radiusProperty(), 1.5f)));
					tl.play();
					break;
				case UP:
					bar.setValue(bar.getValue() + 1);
					if(rand.nextBoolean()){
						userModel.getParticles().get(0).setNeutrons(userModel.getParticles().get(0).getNeutrons() + 1);						
					}else{
						userModel.getParticles().get(0).setProtons(userModel.getParticles().get(0).getProtons() + 1);
					}
					break;
				case DOWN:
					bar.setValue(bar.getValue() - 1);
					if(rand.nextBoolean()){
						userModel.getParticles().get(0).setNeutrons(userModel.getParticles().get(0).getNeutrons() - 1);						
					}else{
						userModel.getParticles().get(0).setProtons(userModel.getParticles().get(0).getProtons() - 1);
					}
					break;
				}
			}
		});
	}
}
