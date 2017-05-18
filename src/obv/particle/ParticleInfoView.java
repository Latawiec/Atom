package obv.particle;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ParticleInfoView extends BorderPane {
	private Text nameTagText;
	private Text nameText;
	private Text massText;
	private Rectangle tagBackground;
	private VBox verticalInfoContainer;
	
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
	
	public ParticleInfoView(double width, double height){
		super();
		setWidth(width);
		setHeight(height);
		
		verticalInfoContainer = new VBox();
		verticalInfoContainer.setAlignment(Pos.CENTER);
		verticalInfoContainer.setTranslateX(getWidth()/2);
		verticalInfoContainer.setTranslateY(getHeight()/2);
		
		massText = new Text(0, getHeight()/2, "");
		massText.setFill(Color.WHITE);
		massText.setFont(Font.font("Verdana", 8));
		massText.setFontSmoothingType(FontSmoothingType.LCD);
		
		nameText = new Text(0, getHeight()/2, "");
		nameText.setFill(Color.WHITE);
		nameText.setFont(Font.font("Helvetica", FontPosture.ITALIC, 10));
		nameText.setFontSmoothingType(FontSmoothingType.LCD);
		
		nameTagText = new Text(0, getHeight()/2, "");
		nameTagText.setFill(Color.WHITE);
		nameTagText.setFont(Font.font("Verdana", 30));
		nameTagText.setFontSmoothingType(FontSmoothingType.LCD);
		
		tagBackground = new Rectangle(getWidth(), getHeight());
		tagBackground.setFill(Color.TRANSPARENT);
		tagBackground.setStroke(Color.WHITE);
		tagBackground.setStrokeType(StrokeType.INSIDE);
		tagBackground.setStrokeWidth(1);
		tagBackground.setSmooth(true);
		
		verticalInfoContainer.getChildren().addAll(nameTagText, nameText, massText);
		getChildren().addAll(tagBackground, verticalInfoContainer);
		/*Rectangle temp = new Rectangle(70, 100);
		temp.setClip(nameTagText);
		temp.setFill(Color.WHITE);*/
		
	}
}
