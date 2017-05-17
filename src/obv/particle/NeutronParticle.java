package obv.particle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;

public class NeutronParticle extends Sphere {
	
	public NeutronParticle(){
		setRadius(10);
		setMaterial(new ParticlesMaterial(Color.LIGHTBLUE));
	}
}
