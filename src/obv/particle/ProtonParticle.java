package obv.particle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;

public class ProtonParticle extends Sphere {
	
	public ProtonParticle(){
		setRadius(10);
		setMaterial(new ParticlesMaterial(Color.CRIMSON));
	}
}