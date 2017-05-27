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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;
import model.Particle.ParticleModel;
import model.databaseControllers.ParticleController;

public class ParticleView extends SubScene {
	
	Group entireParticle;
	Group centerParticles;					//Nucleons
	List<Group> electronShells;				//Electron shells (K, L, M, N, O, P, and Q)
	StackPane container; 					//This is where every object is added to.
	Camera camera = new PerspectiveCamera();
	
	ParticleController controller;

	private IntegerProperty neutrons = new SimpleIntegerProperty();
	public void setNeutrons(int value){ neutrons.set(value); }
	public int getNeutrons() { return neutrons.get(); }
	public IntegerProperty getNeutronsPropety() { return neutrons; }
	
	private IntegerProperty protons = new SimpleIntegerProperty();
	public void setProtons(int value) { protons.set(value); }
	public int getProtons() { return protons.get(); }
	public IntegerProperty getProtonsPropety(){ return protons; }
	
	private IntegerProperty nucleons = new SimpleIntegerProperty();
	public int getNucleons(){ return nucleons.get(); }
	
	private IntegerProperty[] electrons = new IntegerProperty[7];
	public void setElectrons(int[] values){
		for(int i=0; i<values.length; i++){
			if(electrons[i].get() != values[i]){
				electrons[i].set(values[i]);	
				//TODO animating and stuff...
			}
		};
	}
	public int getElectrons(int index){ return electrons[index].get(); }
	public int[] getElectronsArray(){ 
		int[] result = new int[7];
		for(int i=0; i<electrons.length; i++){
			result[i] = electrons[i].get();
		}
		return result;
	}
	
	List<Shape3D>protonsShapes;
	List<Shape3D>neutronsShapes;
	List<Shape3D>nucleonsShapes;
	List<List<Shape3D>>electronsShapes;
	
	public ParticleView(ParticleController controller, ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty){
		super(new StackPane(), 0, 0, true, SceneAntialiasing.BALANCED);
		widthProperty().bind(widthProperty);
		setHeight(heightProperty.get());

		//Setting bindings
		nucleons.bind(neutrons.add(protons));		//? Does this even work?
		nucleons.bind(protons.add(neutrons));
		protons.bind(new SimpleIntegerProperty(controller.getProtons()));
		neutrons.bind(new SimpleIntegerProperty(controller.getNeutrons()));
		for(int i=0; i<7; i++){
			electrons[i] = new SimpleIntegerProperty(0);
			electrons[i].bind(new SimpleIntegerProperty(controller.getElectrons(i)));
		}
		
		//Adding listeners.
		protons.addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(newValue.intValue() > oldValue.intValue()){
					addNucleons(newValue.intValue() - oldValue.intValue(), 0);
				}else{
					removeNucleons(oldValue.intValue() - newValue.intValue(), 0);
				}
			}
		});
		
		neutrons.addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(newValue.intValue() > oldValue.intValue()){
					addNucleons(0, newValue.intValue() - oldValue.intValue());
				}else{
					removeNucleons(0, oldValue.intValue() - newValue.intValue());
				}
			}
		});
		//Setting camera
		camera.setFarClip(1000);
		setCamera(camera);
		
		container = (StackPane) getRoot();
		container.setBackground(Background.EMPTY);
		AmbientLight ambient = new AmbientLight();		//Subscene lighting set to ambient so that everything has no shadow at all.
		ambient.setColor(Color.rgb(255, 255, 255,0f));
		entireParticle = new Group();
		centerParticles = new Group();
		electronShells = new ArrayList<Group>();
		protonsShapes = new ArrayList<Shape3D>();
		neutronsShapes = new ArrayList<Shape3D>();
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

	public void disableElectronShells(){
		entireParticle.getChildren().removeAll(electronShells);
	}
	public void enableElectronShells(){
		entireParticle.getChildren().addAll(electronShells);
	}

	public void addNucleons(int protons, int neutrons){
		float duration = 0.5f;
		Random rand = new Random();
		double spawnOffsetMultiplier = 5;
		double[][] nucleonsPositions = FibbonacciSphere(getNucleons(), atomRadius(getNucleons()));
		Shape3D particle = null;
		int position=0;
		int toSpawn = protons+neutrons;

		for(int i=0; i<toSpawn; i++){
			position = nucleonsShapes.size() == 0 ? 0 : rand.nextInt(nucleonsShapes.size());
			if(protons != 0){
				particle = new ProtonParticle();
				protonsShapes.add(particle);
				protons--;
			}else
			{
				particle = new NeutronParticle();
				neutronsShapes.add(particle);
				neutrons--;
			}
			nucleonsShapes.add(position, particle);
			centerParticles.getChildren().add(particle);
			particle.setTranslateX(spawnOffsetMultiplier*nucleonsPositions[position][0]);
			particle.setTranslateY(spawnOffsetMultiplier*nucleonsPositions[position][1]);
			particle.setTranslateZ(spawnOffsetMultiplier*nucleonsPositions[position][2]);
			((ParticlesMaterial) particle.getMaterial()).fadeIn(duration);
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
	
	public void removeNucleons(int protons, int neutrons){
		float duration = 0.5f;
		for(int i=0; i<protons; i++){
			int index = protonsShapes.size()-1;
			centerParticles.getChildren().remove(protonsShapes.get(index));
			protonsShapes.remove(index);
			nucleonsShapes.remove(index);
		}
		for(int i=0; i<neutrons; i++){
			int index = neutronsShapes.size()-1;
			centerParticles.getChildren().remove(neutronsShapes.get(index));
			neutronsShapes.remove(index);
			nucleonsShapes.remove(index);
		}
		updateNucleonsPositions(duration);
	}
	
	void addElectron(int shellNumber){
		
	}
	
	double atomRadius(int nucleons){
		return 4.0f*Math.sqrt(nucleons);
	}
	
	private void createParticles(){				
		addNucleons(getProtons(), getNeutrons());
		addElectrons(getElectronsArray());
		
	}
	
	void addElectrons(int[] array){
		double electronShellsOffset = 110;
		double electronShellsDelta = 16;
		
		for(int i=0; i<array.length; i++){
			double[][] samplePoints = CircleSpaceOut(array[i], electronShellsOffset + i*electronShellsDelta);
			for(int j=0; j<array[i]; j++){
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

		double[][] nucleonsPositions = FibbonacciSphere(getNucleons(), atomRadius(getNucleons()));
		if(duration == 0){
			for(int i=0; i<getNucleons(); i++){
				nucleonsShapes.get(i).setTranslateX(nucleonsPositions[i][0]);
				nucleonsShapes.get(i).setTranslateY(nucleonsPositions[i][1]);
				nucleonsShapes.get(i).setTranslateZ(nucleonsPositions[i][2]);
			}
		}else{
			Timeline tl = new Timeline();
			for(int i=0; i<getNucleons(); i++){
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
