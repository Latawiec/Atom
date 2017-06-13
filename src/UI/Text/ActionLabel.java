package UI.Text;

import UI.CircleButton;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

/**
 * Created by Latawiec on 12/06/2017.
 */
public class ActionLabel extends BorderPane {

    private StyledLabel costLabel = new StyledLabel(9, FontWeight.EXTRA_LIGHT);
    private StyledLabel nameLabel = new StyledLabel(15, FontWeight.EXTRA_LIGHT);
    private StyledLabel incomeLabel = new StyledLabel(9, FontWeight.EXTRA_LIGHT);
    private CircleButton button = new CircleButton("", 10, false, true);

    public ActionLabel(FloatProperty cost, StringProperty name, FloatProperty income){

        //incomeLabel.setFill(new Color(0.03, 0.53, 0.12, 1));
        //costLabel.setFill(Color.CRIMSON);

        costLabel.textProperty().bind(cost.asString("Cost: %1$.1f"));
        nameLabel.textProperty().bind(name);
        incomeLabel.textProperty().bind(income.asString("Income: %1$.1f"));
        setBackground(Background.EMPTY);
        VBox container = new VBox();
        container.setBackground(Background.EMPTY);
        container.getChildren().addAll(costLabel, nameLabel, incomeLabel);
        setCenter(container);
        StackPane temp = new StackPane();
        temp.setPadding(new Insets(0,10,0,0));
        temp.getChildren().add(button);
        setLeft(temp);
        button.setOnMousePressed(e->{
            onClick();
        });
    }


    public void onClick(){

    }
}
