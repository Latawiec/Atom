package application;
import java.io.IOException;
import java.sql.SQLException;

import controller.ScenesController;
import controller.scenes.mainScene;
import javafx.application.Application;
import javafx.stage.Stage;
import model.databaseControllers.DatabaseAccessor;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		new ScenesController(primaryStage, 500, 720);
	}
	
	public static void main(String[] args) throws SQLException, IOException {
		launch(args);
	}
}
