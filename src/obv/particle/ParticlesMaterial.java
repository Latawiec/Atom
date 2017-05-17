package obv.particle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.util.Duration;

public class ParticlesMaterial extends PhongMaterial {
	
	public ParticlesMaterial(Color color){
		super();
		setSpecularPower(0);
		setDiffuseColor(color);
	}
	
	public void fadeOut(double seconds){
		Color col = getDiffuseColor();
		Timeline tl = new Timeline();
		tl.getKeyFrames().add(new KeyFrame(Duration.ZERO, new KeyValue(this.diffuseColorProperty(), new Color(col.getRed(), col.getGreen(), col.getBlue(), 1))));
		tl.getKeyFrames().add(new KeyFrame(Duration.seconds(seconds), new KeyValue(this.diffuseColorProperty(), new Color(0, 0, 0, 0))));
		tl.play();
	}
	
	public void fadeIn(double seconds){
		Color col = getDiffuseColor();
		Timeline tl = new Timeline();
		tl.getKeyFrames().add(new KeyFrame(Duration.ZERO, new KeyValue(this.diffuseColorProperty(), new Color(0, 0, 0, 0))));
		tl.getKeyFrames().add(new KeyFrame(Duration.seconds(seconds), new KeyValue(this.diffuseColorProperty(), new Color(col.getRed(), col.getGreen(), col.getBlue(), 1))));
		tl.play();
	}
}
