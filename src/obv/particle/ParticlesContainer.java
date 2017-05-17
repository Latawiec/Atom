package obv.particle;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import obv.ui.ChoiceIndicator;

public class ParticlesContainer extends BorderPane {
	
	private int selectedParticle = 0;
	private HBox slider = new HBox();
	ChoiceIndicator indicator = new ChoiceIndicator();
	private List<ParticleView> particleViews = new ArrayList<ParticleView>();
	
	public ParticlesContainer(double width, double height){
		super();
		setWidth(width);
		setHeight(height);
		getChildren().add(slider);
		getChildren().add(indicator);
		indicator.setAlignment(Pos.BOTTOM_CENTER);
		indicator.setTranslateX(getWidth()/2);
		indicator.setTranslateY(getHeight());
		setOnMousePressed(o->{
			if(o.getSceneX()>getWidth()/2){
				selectParticle(selectedParticle+1);
			}else{
				selectParticle(selectedParticle-1);
			}
		});	
	}
	
	void selectParticle(int number){
		if(number < particleViews.size() && number >= 0){
			TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5f), slider);
			selectedParticle = number;
			tt.setToX(-number*getWidth());
			indicator.selectElement(selectedParticle);
			tt.play();
		}
	}
	
	public void addParticle(ParticleView ob){
		particleViews.add(ob);
		slider.getChildren().add(ob);
		indicator.addElement();
		if(particleViews.indexOf(ob) == 0){
			indicator.selectElement(0);
		}
	}
	
	public int getSelectedParticleIndex(){
		return selectedParticle;
	}
	
	public final ParticleView getSelectedParticle(){
		return particleViews.get(selectedParticle);
	}
	
	public void removeParticle(ParticleView ob){
		int index = particleViews.indexOf(ob);
		if(index == selectedParticle){
			selectParticle(index-1);
		}
		ob.disassemble(0.5f);
		Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.5f), null));
		tl.setOnFinished(o->{
			indicator.removeElement(index);
			particleViews.remove(ob);
			slider.getChildren().remove(ob);			
			if(index == 0 && particleViews.size()>0){
				selectParticle(0);
			}
		});
		tl.play();
	}
	
	public final List<ParticleView> getParticleViews(){
		return particleViews;
	}
}
