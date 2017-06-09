package UI.Text;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created by Latawiec on 09/06/2017.
 */
public class StyledLabel extends Text {

    public void setFontSize(double size, FontWeight weight){
        setFont(Font.font("Helvetica", weight, size));
    }

    public StyledLabel(double size, FontWeight weight){
        super();
        setFill(Color.WHITE);
        setFontSize(size, weight);
        setFontSmoothingType(FontSmoothingType.LCD);
    }
}
