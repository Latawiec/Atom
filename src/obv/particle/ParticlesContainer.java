package obv.particle;

import java.util.ArrayList;
import java.util.List;

import UI.ChoiceIndicator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class ParticlesContainer extends BorderPane {
	
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
	
	
	private HBox slider = new HBox();
	ChoiceIndicator indicator = new ChoiceIndicator();
	private List<ParticleView> particleViews = new ArrayList<ParticleView>();
	
	public ParticlesContainer(double width, double height){
		super();
		setWidth(width);
		setHeight(height);
		getChildren().add(slider);
		getChildren().add(indicator);
		setSelectedParticle(0);
		indicator.setAlignment(Pos.BOTTOM_CENTER);
		indicator.setTranslateX(getWidth()/2);
		indicator.setTranslateY(getHeight());
		setOnMousePressed(o->{
			if(o.getSceneX()>getWidth()/2){
				setSelectedParticle(getSelectedParticle() + 1);
			}else{
				setSelectedParticle(getSelectedParticle() - 1);
			}
		});	
	}
	
	public void addParticle(ParticleView ob){
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
	
	public void removeParticle(ParticleView ob){
		int index = particleViews.indexOf(ob);
		if(index == getSelectedParticle()){
			setSelectedParticle(index-1);
		}
		ob.disassemble(0.5f);
		Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.5f), null));
		tl.setOnFinished(o->{
			indicator.removeElement(index);
			particleViews.remove(ob);
			slider.getChildren().remove(ob);			
			if(index == 0 && particleViews.size()>0){
				setSelectedParticle(0);
			}
		});
		tl.play();
	}
	
	public final List<ParticleView> getParticleViews(){
		return particleViews;
	}
}
