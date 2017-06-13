package view.collider;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;

/**
 * Created by Latawiec on 28/05/2017.
 */
public class BasicInfoTag extends BorderPane {

    private Text nameTagText;
    private Text massNumberText;
    private Text atomicNumberText;
    private VBox verticalInfoContainer;
    private VBox nucleoidsNumbersContainer;

    public void setTag(String tag){
        nameTagText.setText(tag);
    }

    public void setMassNumber(int number){
        massNumberText.setText(String.valueOf(number));
    }

    public void setAtomicNumber(int number){
        atomicNumberText.setText(String.valueOf(number));
    }

    public BasicInfoTag(String nameTag, int atomic, int nucleons){
        verticalInfoContainer = new VBox();
        verticalInfoContainer.setAlignment(Pos.CENTER);
        verticalInfoContainer.setTranslateX(getWidth()/2);
        verticalInfoContainer.setTranslateY(getHeight()/2);

        StackPane tagPane = new StackPane();

        nameTagText = new Text(0, getHeight()/2, nameTag);
        if(nameTag == null){
            nameTagText.setText("?");
        }
        nameTagText.setFill(Color.WHITE);
        nameTagText.setFont(Font.font("Helvetica", 25));
        nameTagText.setFontSmoothingType(FontSmoothingType.GRAY);
        tagPane.getChildren().add(nameTagText);

        nucleoidsNumbersContainer = new VBox();
        nucleoidsNumbersContainer.setAlignment(Pos.TOP_RIGHT);
        nucleoidsNumbersContainer.translateXProperty().bind(tagPane.widthProperty().divide(-2).add(getWidth()/2));
        nucleoidsNumbersContainer.setTranslateY(getHeight()/2 - 20);
        nucleoidsNumbersContainer.setSpacing(12);

        massNumberText = new Text(0, getHeight()/2, Integer.toString(nucleons));
        massNumberText.setFill(Color.WHITE);
        massNumberText.setFont(Font.font("Helvetica", 10));
        massNumberText.setFontSmoothingType(FontSmoothingType.LCD);

        atomicNumberText = new Text(0, getHeight()/2, Integer.toString(atomic));
        atomicNumberText.setFill(Color.WHITE);
        atomicNumberText.setFont(Font.font("Helvetica", 10));
        atomicNumberText.setFontSmoothingType(FontSmoothingType.LCD);

        nucleoidsNumbersContainer.getChildren().addAll(massNumberText, atomicNumberText);
        verticalInfoContainer.getChildren().add(tagPane);
        getChildren().addAll(verticalInfoContainer, nucleoidsNumbersContainer);
    }

}
