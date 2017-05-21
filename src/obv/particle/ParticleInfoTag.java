package obv.particle;

import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;

public class ParticleInfoTag extends BorderPane {
	private Text nameTagText;
	private Text nameText;
	private Text massText;
	private Text massNumberText;
	private Text atomicNumberText;
	private Shape tagBackground;
	private VBox verticalInfoContainer;
	private VBox nucleoidsNumbersContainer;
	
	public IntegerProperty atomicNumber = new SimpleIntegerProperty();
	public void setAtomicNumber(int newNumber){
		atomicNumberText.setText(Integer.toString(newNumber));
		atomicNumber.set(newNumber);
	}
	public int getAtomicNumber() { return massNumber.get(); }
	
	public IntegerProperty massNumber = new SimpleIntegerProperty();
	public void setMassNumber(int newNumber){
		massNumberText.setText(Integer.toString(newNumber));
		massNumber.set(newNumber);
	}
	public int getMassNumber() { return massNumber.get(); }
	
	public FloatProperty mass = new SimpleFloatProperty();
	public void setMass(float newMass){ 
		massText.setText(Float.toString(newMass));
		mass.set(newMass);
	}
	public float getMass() { return mass.get();	}
	
	public StringProperty nameTag = new SimpleStringProperty();
	public void setNameTag(String newTag){ 
		nameTagText.setText(newTag);
		nameTag.set(newTag);
	}
	public String getNameTag() { return nameTag.get();	}
	
	public StringProperty name = new SimpleStringProperty();
	public void setName(String newName){ 
		nameText.setText(newName);
		name.set(newName);
	}
	public String getName() { return name.get();	}
	
	public ParticleInfoTag(double width, double height){
		super();
		setWidth(width);
		setHeight(height);
		
		verticalInfoContainer = new VBox();
		verticalInfoContainer.setAlignment(Pos.CENTER);
		verticalInfoContainer.setTranslateX(getWidth()/2);
		verticalInfoContainer.setTranslateY(getHeight()/2);
		
		StackPane tagPane = new StackPane();
		
		nameTagText = new Text(0, getHeight()/2, "");
		nameTagText.setFill(Color.WHITE);
		nameTagText.setFont(Font.font("Helvetica", 35));
		nameTagText.setFontSmoothingType(FontSmoothingType.GRAY);
		tagPane.getChildren().add(nameTagText);
		
		nucleoidsNumbersContainer = new VBox();
		nucleoidsNumbersContainer.setAlignment(Pos.TOP_RIGHT);
		nucleoidsNumbersContainer.translateXProperty().bind(tagPane.widthProperty().divide(-2).add(getWidth()/2));
		nucleoidsNumbersContainer.setTranslateY(getHeight()/2 - 33);
		nucleoidsNumbersContainer.setSpacing(12);
		
		massText = new Text(0, getHeight()/2, "");
		massText.setFill(Color.WHITE);
		massText.setFont(Font.font("Helvetica", 10));
		massText.setFontSmoothingType(FontSmoothingType.LCD);
		
		nameText = new Text(0, getHeight()/2, "");
		nameText.setFill(Color.WHITE);
		nameText.setFont(Font.font("Helvetica", FontPosture.ITALIC, 12));
		nameText.setFontSmoothingType(FontSmoothingType.LCD);
		
		massNumberText = new Text(0, getHeight()/2, "");
		massNumberText.setFill(Color.WHITE);
		massNumberText.setFont(Font.font("Helvetica", 10));
		massNumberText.setFontSmoothingType(FontSmoothingType.LCD);
		
		atomicNumberText = new Text(0, getHeight()/2, "");
		atomicNumberText.setFill(Color.WHITE);
		atomicNumberText.setFont(Font.font("Helvetica", 10));
		atomicNumberText.setFontSmoothingType(FontSmoothingType.LCD);
		
		tagBackground = new Rectangle(getWidth(), getHeight());
		tagBackground.setFill(new Color(1, 1, 1, 0.1f));
		tagBackground.setStroke(Color.WHITE);
		tagBackground.setStrokeWidth(1);
		tagBackground.setStrokeType(StrokeType.INSIDE);
		tagBackground.setSmooth(true);
		/*tagBackground = new Ellipse(0.7f*getWidth(), 0.7f*getWidth());
		tagBackground.setTranslateX(getWidth()/2);
		tagBackground.setTranslateY(getHeight()/2);
		tagBackground.setFill(new Color(1, 1, 1, 0.1f));
		tagBackground.setStroke(Color.WHITE);
		tagBackground.setStrokeWidth(2);
		tagBackground.setStrokeType(StrokeType.INSIDE);
		tagBackground.setSmooth(true);*/
		
		nucleoidsNumbersContainer.getChildren().addAll(massNumberText, atomicNumberText);
		verticalInfoContainer.getChildren().addAll(tagPane, nameText, massText);
		getChildren().addAll(tagBackground, verticalInfoContainer, nucleoidsNumbersContainer);		
	}
}
