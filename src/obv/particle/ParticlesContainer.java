package obv.particle;

import java.util.ArrayList;
import java.util.List;

import UI.ChoiceIndicator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import model.Particle.ParticleModel;

public class ParticlesContainer extends BorderPane {
	
	private List<ParticleModel> particleModels;
		
	public IntegerProperty selectedParticle = new SimpleIntegerProperty();
	public void setSelectedParticle(int newNumber){
		if(newNumber < particleViews.size() && newNumber >= 0){
			selectedParticle.set(newNumber);
			TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5f), slider);
			tt.setToX(-newNumber*getWidth());
			indicator.selectElement(getSelectedParticle());
			tt.play();
		}
	}
	public int getSelectedParticle() { return selectedParticle.get(); }
	public IntegerProperty getSelectedParticleProperty(){return selectedParticle;}
	
	
	private HBox slider = new HBox();
	ChoiceIndicator indicator = new ChoiceIndicator();
	private List<ParticleView> particleViews = new ArrayList<ParticleView>();
	
	public ParticlesContainer(double width, double height, List<ParticleModel> models){
		super();
		particleModels = models;
		setWidth(width);
		setHeight(height);
		getChildren().add(slider);
		getChildren().add(indicator);
		setSelectedParticle(0);
		indicator.setAlignment(Pos.BOTTOM_CENTER);
		indicator.setTranslateX(getWidth()/2);
		indicator.setTranslateY(getHeight());
		
		for(final ParticleModel model : models){
			addParticle(model);
		}
		
	}
	
	public void addParticle(final ParticleModel model){
		ParticleView view = new ParticleView(model, widthProperty(), heightProperty());
		addParticle(view);
	}
	
	private void addParticle(ParticleView ob){
		particleViews.add(ob);
		slider.getChildren().add(ob);
		indicator.addElement();
		if(particleViews.indexOf(ob) == 0){
			indicator.selectElement(0);
		}
	}
	
	public final ParticleView getSelectedParticleView(){
		return particleViews.get(getSelectedParticle());
	}
	
	public void removeParticle(int index){
		if(index == getSelectedParticle()){
			setSelectedParticle(index-1);
		}
		particleViews.get(index).disassemble(0.5f);
		Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.5f), null));
		tl.setOnFinished(o->{
			indicator.removeElement(index);
			particleViews.remove(particleViews.get(index));
			slider.getChildren().remove(particleViews.get(index));			
			if(index == 0 && particleViews.size()>0){
				setSelectedParticle(0);
			}
		});
		tl.play();
	}
	
	public void removeParticle(ParticleView ob){
		int index = particleViews.indexOf(ob);
		removeParticle(index);
	}
	
	public final List<ParticleView> getParticleViews(){
		return particleViews;
	}
}
