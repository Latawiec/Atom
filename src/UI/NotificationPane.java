package UI;

import UI.Text.StyledLabel;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;

public class NotificationPane extends StackPane {
	
	private CircleButton close = new CircleButton("CLOSE", 15, true, true);
	private Rectangle background = new Rectangle();
	private StyledLabel header = new StyledLabel(20, FontWeight.EXTRA_LIGHT);
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
		row1.setPercentHeight(10);
		row1.setVgrow(Priority.ALWAYS);
		row1.setValignment(VPos.CENTER);

		RowConstraints row2 = new RowConstraints();
		row2.setPercentHeight(80);
		row2.setVgrow(Priority.ALWAYS);

		RowConstraints row3 = new RowConstraints();
		row3.setPercentHeight(10);
		row3.setVgrow(Priority.ALWAYS);
		row3.setValignment(VPos.CENTER);

		grid.getRowConstraints().addAll(row1, row2, row3);
		grid.setAlignment(Pos.CENTER);
		//grid.setGridLinesVisible(true);

		StackPane headerContainer = new StackPane();
		headerContainer.getChildren().add(header);
		grid.add(headerContainer, 0, 0);

		grid.add(container, 0, 1);
		StackPane buttonContainer = new StackPane();
		buttonContainer.getChildren().add(close);
		grid.add(buttonContainer, 0, 2);

		getChildren().addAll(background);
		close.setTranslateY(-10);
		getChildren().add(grid);
	}
	
	public CircleButton getCloseButton(){ return close; }

	public void setHeader(String header){
		this.header.setText(header);
	}
}
