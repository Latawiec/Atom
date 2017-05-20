package obv.particle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

public class ParticleView extends SubScene {
	
	Group entireParticle;
	Group centerParticles;					//Nucleons
	List<Group> electronShells;				//Electron shells (K, L, M, N, O, P, and Q)
	StackPane container; 					//This is where every object is added to.
	Camera camera = new PerspectiveCamera();
	int nucleons;
	int protons;
	int neutrons;
	int[] electrons;
	
	List<Shape3D>nucleonsShapes;
	List<List<Shape3D>>electronsShapes;
	
	public ParticleView(int neutrons, int protons, int[] electrons, ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty){
		super(new StackPane(), 0, 0, true, SceneAntialiasing.BALANCED);
		widthProperty().bind(widthProperty);
		heightProperty().bind(heightProperty);
				
		camera.setFarClip(1000);
		setCamera(camera);
		
		container = (StackPane) getRoot();
		
		AmbientLight ambient = new AmbientLight();		//Subscene lighting set to ambient so that everything has no shadow at all.
		ambient.setColor(Color.rgb(255, 255, 255,0f));
		this.neutrons = neutrons;
		this.protons = protons;
		this.electrons = electrons;
		nucleons = protons + neutrons;
		entireParticle = new Group();
		centerParticles = new Group();
		electronShells = new ArrayList<Group>();
		nucleonsShapes = new ArrayList<Shape3D>();
		
		for(int i=0; i<7; i++){
			electronShells.add(new Group());
		}
		
		createParticles();
		
		Sphere ranger = new Sphere(200, 1);
		ranger.radiusProperty().bind(widthProperty);
		ranger.setOpacity(0);
		entireParticle.getChildren().add(ranger);
		entireParticle.getChildren().addAll(electronShells);
		entireParticle.getChildren().add(centerParticles);
		container.getChildren().addAll(ambient, entireParticle);
		
		setElectronsSpin();
		setParticleSpin();
	}
	
	public void addNucleons(String[] particleTypes){
		float duration = 0.5f;
		Random rand = new Random();
		double spawnOffsetMultiplier = 5;
		double[][] nucleonsPositions = FibbonacciSphere(nucleons + particleTypes.length, atomRadius(nucleons + particleTypes.length));
		for(String nuc : particleTypes){
			int position = nucleons != 0 ? rand.nextInt(nucleons) : 0;
			Shape3D particle = null;
			switch(nuc.toLowerCase()){
			case "proton":
				particle = new ProtonParticle();
				protons++;
				break;
			case "neutron":
				particle = new NeutronParticle();
				neutrons++;
				break;
			}
			nucleonsShapes.add(position, particle);
			centerParticles.getChildren().add(particle);
			particle.setTranslateX(spawnOffsetMultiplier*nucleonsPositions[position][0]);
			particle.setTranslateY(spawnOffsetMultiplier*nucleonsPositions[position][1]);
			particle.setTranslateZ(spawnOffsetMultiplier*nucleonsPositions[position][2]);
			((ParticlesMaterial) particle.getMaterial()).fadeIn(0.5f*duration);
			nucleons++;
		}		
		updateNucleonsPositions(duration);	
	}
	
	public void disassemble(double duration){
		double distanceMultiplier=5;
		Timeline tl = new Timeline();
		List<Shape3D> temp = new ArrayList<Shape3D>();
		for(Shape3D particle : nucleonsShapes){
			tl.getKeyFrames().add(new KeyFrame(Duration.seconds(duration), new KeyValue(particle.translateXProperty(), particle.getTranslateX()*distanceMultiplier, Interpolator.EASE_OUT)));
			tl.getKeyFrames().add(new KeyFrame(Duration.seconds(duration), new KeyValue(particle.translateYProperty(), particle.getTranslateY()*distanceMultiplier, Interpolator.EASE_OUT)));
			tl.getKeyFrames().add(new KeyFrame(Duration.seconds(duration), new KeyValue(particle.translateZProperty(), particle.getTranslateZ()*distanceMultiplier, Interpolator.EASE_OUT)));
			((ParticlesMaterial) particle.getMaterial()).fadeOut(duration);
			nucleons--;
			temp.add(particle);
		}
		tl.setOnFinished(o->{
			for(Shape3D particle : temp){
				particle.setVisible(false);
				nucleonsShapes.remove(particle);
			}
			temp.clear();
		});
		tl.play();
	}
	
	public void removeNucleons(){
		
		
	}
	
	void addElectron(int shellNumber){
		
	}
	
	double atomRadius(int nucleons){
		return 4.2f*Math.sqrt(nucleons);
	}
	
	private void createParticles(){
		Random rand = new Random();
		
		double electronShellsOffset = 110;
		double electronShellsDelta = 16;
		
		for(int i=0; i<nucleons; i++){	
			if(rand.nextBoolean()){
				if(neutrons>0){
					nucleonsShapes.add(new NeutronParticle());
					neutrons--;					
				}else{
					nucleonsShapes.add(new ProtonParticle());
					protons--;
				}
			}else{
				if(protons>0){
					nucleonsShapes.add(new ProtonParticle());
					protons--;
				}else{
					nucleonsShapes.add(new NeutronParticle());
					neutrons--;										
				}
			}
		}
		
		centerParticles.getChildren().addAll(nucleonsShapes);
		updateNucleonsPositions(0);
		
		for(int i=0; i<electrons.length; i++){
			double[][] samplePoints = CircleSpaceOut(electrons[i], electronShellsOffset + i*electronShellsDelta);
			for(int j=0; j<electrons[i]; j++){
				Shape3D particle = new ElectronParticle();
				electronShells.get(i).getChildren().add(particle);
				particle.setTranslateX(samplePoints[j][0]);
				particle.setTranslateY(samplePoints[j][1]);
				Shape3D offweight = new Sphere(0);
				offweight.setDisable(true);
				offweight.setTranslateX(-samplePoints[j][0]);
				offweight.setTranslateY(-samplePoints[j][1]);
				electronShells.get(i).getChildren().add(offweight);
			}
		}
	}
	
	void setParticleSpin(){
		RotateTransition rt = new RotateTransition();
		rt.setNode(entireParticle);
		rt.setByAngle(360);
		rt.setDuration(Duration.seconds(10));
		rt.setInterpolator(Interpolator.LINEAR);
		rt.setAxis(new Point3D(0, 1, 1));
		rt.setCycleCount(Animation.INDEFINITE);
		rt.play();
	}
	
	void setElectronsSpin(){
		for(int i=0; i<electronShells.size(); i++){
			RotateTransition rt = new RotateTransition();
			rt.setNode(electronShells.get(i));
			rt.setByAngle((electronShells.size()-i)*360);
			rt.setDuration(Duration.seconds((i+1)*9));
			rt.setInterpolator(Interpolator.LINEAR);
			Point3D axis = new Point3D(1, 0, 1);
			rt.setAxis(axis.normalize());
			rt.setCycleCount(Animation.INDEFINITE);
			rt.play();
		}
	}
	
	void updateNucleonsPositions(float duration){

		double[][] nucleonsPositions = FibbonacciSphere(nucleons, atomRadius(nucleons));
		if(duration == 0){
			for(int i=0; i<nucleons; i++){
				nucleonsShapes.get(i).setTranslateX(nucleonsPositions[i][0]);
				nucleonsShapes.get(i).setTranslateY(nucleonsPositions[i][1]);
				nucleonsShapes.get(i).setTranslateZ(nucleonsPositions[i][2]);
			}
		}else{
			Timeline tl = new Timeline();
			for(int i=0; i<nucleons; i++){
				tl.getKeyFrames().add(new KeyFrame(Duration.seconds(duration), new KeyValue(nucleonsShapes.get(i).translateXProperty(), nucleonsPositions[i][0], Interpolator.EASE_OUT)));
				tl.getKeyFrames().add(new KeyFrame(Duration.seconds(duration), new KeyValue(nucleonsShapes.get(i).translateYProperty(), nucleonsPositions[i][1], Interpolator.EASE_OUT)));
				tl.getKeyFrames().add(new KeyFrame(Duration.seconds(duration), new KeyValue(nucleonsShapes.get(i).translateZProperty(), nucleonsPositions[i][2], Interpolator.EASE_OUT)));
			}
			tl.play();			
		}
	}
	
	static double[][] CircleSpaceOut(int samples, double radius){
		double[][] result = new double[samples][2];
		double angleDelta = 2.f*(Math.PI/samples);
		
		for(int i=0; i<samples; i++){
			result[i] = new double[]{ radius*Math.sin(i*angleDelta), radius*Math.cos(i*angleDelta) };
		}
		return result;
	}
	
	static double[][] FibbonacciSphere(int samples, double radius){
		
		double[][] result = new double[samples][3];
		
		double rnd = 1f;
		double offset = 2.f/samples;
		double x, y, z, r, phi;
		
		double increment = (Math.PI * (3. - Math.sqrt(5.)));
		
		for(int i=0; i<samples; i++){
			y = ((i * offset) - 1) + (offset / 2);
	        r = Math.sqrt(1 - Math.pow(y,2));

	        phi = ((i + rnd) % samples) * increment;

	        x = Math.cos(phi) * r * radius;
	        z = Math.sin(phi) * r * radius;
	        y*= radius;
	        result[i] = new double[]{ x, y, z };
		}
		
		return result;
	}
}
