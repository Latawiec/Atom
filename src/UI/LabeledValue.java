package UI;

import UI.Text.StyledLabel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;

/**
 * Created by Latawiec on 09/06/2017.
 */
public class LabeledValue extends BorderPane {

    private StringProperty name = new SimpleStringProperty();
    public void setName(String value){ name.set(value); }
    public String getName(){ return name.get(); }
    public StringProperty getNameProperty(){ return name; }

    private StringProperty value = new SimpleStringProperty();
    public void setValue(String val){ value.set(val); }
    public String getValue(){ return value.get(); }
    public StringProperty getValueProperty(){ return value; }

    private StyledLabel nameLabel = new StyledLabel(10, FontWeight.BOLD);
    private StyledLabel valueLabel = new StyledLabel(15, FontWeight.EXTRA_LIGHT);

    public LabeledValue(String name){
        super();
        setMinSize(getWidth(), getHeight());
        setName(name);
        nameLabel.textProperty().bind(getNameProperty());
        valueLabel.textProperty().bind(getValueProperty());

        VBox container = new VBox();
        container.getChildren().addAll(nameLabel, valueLabel);

        getChildren().add(container);
    }
}
