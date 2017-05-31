package UI;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class NotificationPane extends StackPane {
	
	private circleButton close = new circleButton("CLOSE", 15, true);
	private Rectangle background = new Rectangle();
	
	private StackPane container = new StackPane();
	
	public NotificationPane(){
		super();
		container.setMaxHeight(500);
		container.setMaxWidth(300);
		background.heightProperty().bind(container.maxHeightProperty());
		background.widthProperty().bind(container.maxWidthProperty());
		background.setArcHeight(20);
		background.setArcWidth(20);
		background.setFill(new Color(1f,1f,1f, 0.4f));
		container.getChildren().addAll(background, close);
		container.setAlignment(close, Pos.BOTTOM_CENTER);
		close.setTranslateY(-10);
		getChildren().add(container);
	}
	
	public circleButton getCloseButton(){ return close; }
}
