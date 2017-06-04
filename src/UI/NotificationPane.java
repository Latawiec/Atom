package UI;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class NotificationPane extends StackPane {
	
	private CircleButton close = new CircleButton("CLOSE", 15, true);
	private Rectangle background = new Rectangle();
	
	protected StackPane container = new StackPane();
	GridPane grid = new GridPane();

	public NotificationPane(){
		super();
		grid.setMaxHeight(500);
		grid.setMaxWidth(300);
		background.heightProperty().bind(grid.maxHeightProperty());
		background.widthProperty().bind(grid.maxWidthProperty());
		background.setArcHeight(20);
		background.setArcWidth(20);
		background.setFill(new Color(1f,1f,1f, 0.4f));


		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(90);
		row1.setVgrow(Priority.ALWAYS);
		RowConstraints row2 = new RowConstraints();
		row2.setPercentHeight(10);
		row2.setVgrow(Priority.ALWAYS);
		row2.setValignment(VPos.CENTER);
		grid.getRowConstraints().addAll(row1, row2);
		grid.setAlignment(Pos.CENTER);
		//grid.setGridLinesVisible(true);

		grid.add(container, 0, 0);
		StackPane buttonContainer = new StackPane();
		buttonContainer.getChildren().add(close);
		grid.add(buttonContainer, 0, 1);

		getChildren().addAll(background);
		close.setTranslateY(-10);
		getChildren().add(grid);
	}
	
	public CircleButton getCloseButton(){ return close; }
}
