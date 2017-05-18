package controlls.mainScreen;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import obv.particle.ParticleInfoView;
import obv.particle.ParticleView;
import obv.particle.ParticlesContainer;

public class mainController extends Scene {

	BorderPane root;
	ParticlesContainer particlesContainer = new ParticlesContainer(480, 480);
	
	public mainController() {
		super(new BorderPane(), 480,720, true, SceneAntialiasing.BALANCED);
		root = (BorderPane) getRoot();
		Rectangle background = new Rectangle(root.getWidth(), root.getHeight());
		background.setFill(new Color(0.03, 0.03, 0.12, 1));
		root.getChildren().add(background);

		particlesContainer.addParticle(new ParticleView(5, 3, new int[]{5, 4, 3}, getWidth(), getWidth()));
		particlesContainer.addParticle(new ParticleView(1, 1, new int[]{1}, getWidth(), getWidth()));
		particlesContainer.addParticle(new ParticleView(30, 30, new int[]{3, 3, 3, 3, 3, 3, 3}, getWidth(), getWidth()));
		root.getChildren().add(particlesContainer);
		
		/*--------------*/
		ParticleInfoView info = new ParticleInfoView(70, 100);
		info.setNameTag("H");
		info.setName("Hydrogen");
		info.setMass(1.008f);
		root.getChildren().add(info);
		
		GaussianBlur blur = new GaussianBlur(0);
		particlesContainer.setEffect(blur);
		setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent arg0) {
				Timeline tl = new Timeline();
				switch(arg0.getCode()){
				case SPACE:
					tl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5f), new KeyValue(blur.radiusProperty(), 20)));
					tl.play();
					break;
				case A:
					tl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5f), new KeyValue(blur.radiusProperty(), 0)));
					tl.play();
					break;
				case B:
					particlesContainer.addParticle(new ParticleView(10, 10, new int[]{5,3}, getWidth(), getWidth()));
					break;
				case V:
					particlesContainer.removeParticle(particlesContainer.getSelectedParticle());
				}
			}
		});
	}
}
