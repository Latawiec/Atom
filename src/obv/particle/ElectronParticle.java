package obv.particle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;

public class ElectronParticle extends Sphere {
	
	public ElectronParticle(){
		setRadius(2);
		setMaterial(new ParticlesMaterial(Color.ALICEBLUE));
	}
}
