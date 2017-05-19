package UI;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class ValueBar extends StackPane {

	Line backgroundLine;
	Line foregroundLine;
	
	Color foregroundColor;
	Color backgroundColor;
	
	private double width;
	
	private FloatProperty value = new SimpleFloatProperty();
	public void setValue(float newValue){
		if(newValue <= getMaxValue() && newValue>=0){
			value.set(newValue);
			foregroundLine.setEndX( (newValue * width) / getMaxValue());			
		}
	}
	public float getValue(){ return value.get(); }
	
	private FloatProperty maxValue = new SimpleFloatProperty();
	public void setMaxValue(float newValue){
		maxValue.set(newValue);
		setValue(getValue());
	}
	public float getMaxValue(){ return maxValue.get(); }
	
	public ValueBar(double width, double radius){
		super();
		setAlignment(Pos.CENTER_LEFT);
		foregroundColor = new Color(1, 1, 1, 1);
		backgroundColor = new Color(1, 1, 1, 0.5);
		
		this.width = width;
		
		backgroundLine = new Line();
		backgroundLine.setStartX(0);
		backgroundLine.setStartY(0);
		backgroundLine.setEndX(width);
		backgroundLine.setEndY(0);
		backgroundLine.setStrokeWidth(radius);
		backgroundLine.setStroke(backgroundColor);
		backgroundLine.setStrokeLineCap(StrokeLineCap.ROUND);
		
		foregroundLine = new Line();
		foregroundLine.setStartX(0);
		foregroundLine.setStartY(0);
		foregroundLine.setEndX(0);
		foregroundLine.setEndY(0);
		foregroundLine.setStrokeWidth(radius);
		foregroundLine.setStroke(foregroundColor);
		foregroundLine.setStrokeLineCap(StrokeLineCap.ROUND);
		
		getChildren().addAll(backgroundLine, foregroundLine);
	}
}
