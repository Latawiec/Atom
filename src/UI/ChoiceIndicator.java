package UI;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.FillTransition;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class ChoiceIndicator extends HBox {

	public class Indicator extends Ellipse{
		
		Color color;
		public final double disabledOpacity = 0.35f;
		
		public Indicator(Color color){
			super();
			setSmooth(true);
			setSpacing(5);
			this.color = color;
			setFill(new Color(color.getRed(), color.getGreen(), color.getBlue(), disabledOpacity));
			setRadiusX(3);
			setRadiusY(3);
		}
		
		public void animateOpacity(double seconds, double to){
			FillTransition ft = new FillTransition();
			ft.setShape(this);
			ft.setDuration(Duration.seconds(seconds));
			ft.setToValue(new Color(color.getRed(), color.getGreen(), color.getBlue(), to));
			ft.play();
		}
		
		public void animateOpacity(double seconds){
			animateOpacity(seconds, disabledOpacity);
		}
	}
	
	private int chosenElement = 0;
	private int elementsCount = 0;
	
	List<Indicator> indicators = new ArrayList<Indicator>();
	
	public ChoiceIndicator(){
		super();
	}
	
	public void addElements(int number){
		elementsCount += number;
		Indicator indic = new Indicator(Color.WHITE);
		indicators.add(indic);
		getChildren().add(indic);
	}
	
	public void removeElements(int[] values){
		elementsCount -= values.length;
		for(int val : values){
			getChildren().remove(indicators.get(val));
			indicators.remove(val);
		}
	}
	
	public void selectElement(int number){
		double durationSeconds = 0.5f;
		if(indicators.size()>1) {
			indicators.get(chosenElement).animateOpacity(durationSeconds);
		}
		chosenElement = number;
		indicators.get(chosenElement).animateOpacity(durationSeconds, 1);
	}
	
	public void addElement(){
		addElements(1);
	}
	
	public void removeElement(int index){
		removeElements(new int[]{index});
	}
	
}
