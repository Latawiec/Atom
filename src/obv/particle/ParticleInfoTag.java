package obv.particle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.*;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.sql.Time;

public class ParticleInfoTag extends BorderPane {
	private Text nameTagText;
	private Text nameText;
	private Text massText;
	private Text massNumberText;
	private Text atomicNumberText;
	private Shape tagBackground;
	private VBox verticalInfoContainer;
	private VBox nucleoidsNumbersContainer;

	public FloatProperty bindingEnergy = new SimpleFloatProperty(-1);
	public void setBindingEnergy(float value){
		bindingEnergy.set(value);
	}
	public float getBindingEnergy(){ return bindingEnergy.get(); }

	public FloatProperty energy = new SimpleFloatProperty(-1);
	public void setEnergy(float value){
		energy.set(value);
	}
	public float getEnergy(){ return energy.get(); }

	public BooleanProperty energized = new SimpleBooleanProperty();
	public void setEnergized(boolean value){ energized.set(value); }
	public boolean isEnergized(){ return energized.get(); }

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

		tagBackground = new Ellipse(0.7f*getWidth(), 0.7f*getWidth());
		tagBackground.setTranslateX(getWidth()/2);
		tagBackground.setTranslateY(getHeight()/2);
		tagBackground.setRotate(-90);
		tagBackground.setFill(new Color(1, 1, 1, 0.1f));
		tagBackground.setStroke(Color.WHITE);
		tagBackground.getStrokeDashArray().addAll(Math.PI* ( (Ellipse) tagBackground).getRadiusX(), Double.MAX_VALUE);
		tagBackground.setStrokeWidth(2);
		tagBackground.setStrokeLineCap(StrokeLineCap.ROUND);
		tagBackground.setStrokeType(StrokeType.CENTERED);
		tagBackground.setSmooth(true);

		Timeline tl = new Timeline();
		tl.getKeyFrames().add(new KeyFrame(Duration.ZERO, new KeyValue(tagBackground.strokeWidthProperty(), 2)));
		tl.getKeyFrames().add(new KeyFrame(Duration.seconds(2), new KeyValue(tagBackground.strokeWidthProperty(), 5)));
		tl.getKeyFrames().add(new KeyFrame(Duration.seconds(3), new KeyValue(tagBackground.strokeWidthProperty(), 2)));
		tl.setCycleCount(Animation.INDEFINITE);

		ChangeListener<Number> lengthListener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double value = getEnergy()/getBindingEnergy();
				tagBackground.getStrokeDashArray().set(0, 2*Math.PI*( (Ellipse) tagBackground).getRadiusX() * value);
			}
		};

		bindingEnergy.addListener(lengthListener);
		energy.addListener(lengthListener);

		ChangeListener<Boolean> animListener = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue){
					tagBackground.setStroke(Color.AQUA);
					tl.stop();
					tl.setCycleCount(Animation.INDEFINITE);
					tl.play();
				}else{
					tagBackground.setStroke(Color.WHITE);
					tl.stop();
					tagBackground.setStrokeWidth(2);
				}
			}
		};
		energized.addListener(animListener);

		nucleoidsNumbersContainer.getChildren().addAll(massNumberText, atomicNumberText);
		verticalInfoContainer.getChildren().addAll(tagPane, nameText, massText);
		getChildren().addAll(tagBackground, verticalInfoContainer, nucleoidsNumbersContainer);		
	}
}
