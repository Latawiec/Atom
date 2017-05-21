package controlls.mainScreen;

import java.io.IOException;

import UI.ValueBar;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Particle.ParticleModel;
import model.Particle.UserdataModel;
import obv.particle.ParticleInfoTag;
import obv.particle.ParticlesContainer;

public class mainController extends Scene {

	BorderPane root;
	UserdataModel userModel;
	ParticlesContainer particlesContainer;
	
	ParticleInfoTag info;
		
	private IntegerProperty selectedParticle = new SimpleIntegerProperty();
	public void setSelectedParticle(int value){
		if(value >= 0 && value < userModel.getParticlesCount()){
			particlesContainer.setSelectedParticle(value);
			selectedParticle.set(particlesContainer.getSelectedParticle());
			
			final ParticleModel particle = userModel.getParticles().get(value);
			
			info.setNameTag(particle.getNameTag());
			info.setName(particle.getName());
			info.setMass(particle.getParticleMass());
			info.setAtomicNumber(particle.getProtons());
			info.setMassNumber(particle.getProtons() + particle.getNeutrons());
		}
	}
	public int getSelectedParticle() { return selectedParticle.get(); }
	
	public mainController() throws IOException {
		super(new BorderPane(), 500,720, true, SceneAntialiasing.BALANCED);
		
		root = (BorderPane) getRoot();
		userModel = new UserdataModel();
		userModel.getParticles().add(new ParticleModel(0, 1, new int[]{1}, "Hydrogen", "H"));
		userModel.getParticles().add(new ParticleModel(1, 1, new int[]{1}, "Deuterium", "D"));
		userModel.getParticles().add(new ParticleModel(143, 92, new int[]{2, 8, 18, 32, 21, 9, 2}, "Uranium", "U"));
		particlesContainer = new ParticlesContainer(getWidth(), getWidth(), userModel.getParticles());
		Rectangle background = new Rectangle(root.getWidth(), root.getHeight());
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
		
		GaussianBlur blur = new GaussianBlur(0);
		particlesContainer.setEffect(blur);
		setSelectedParticle(0);
		setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent arg0) {
				Timeline tl = new Timeline();
				switch(arg0.getCode()){
				case LEFT:
					setSelectedParticle(getSelectedParticle() - 1);
					//particlesContainer.setSelectedParticle(particlesContainer.getSelectedParticle() - 1);
					break;
				case RIGHT:
					setSelectedParticle(getSelectedParticle() + 1);
					//particlesContainer.setSelectedParticle(particlesContainer.getSelectedParticle() + 1);
					break;
				case SPACE:
					tl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5f), new KeyValue(blur.radiusProperty(), 20)));
					tl.play();
					break;
				case A:
					tl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5f), new KeyValue(blur.radiusProperty(), 0)));
					tl.play();
					break;
				case UP:
					bar.setValue(bar.getValue() + 1);
					break;
				case DOWN:
					bar.setValue(bar.getValue() - 1);
					break;
				}
			}
		});
	}
}
