package application;
import java.io.IOException;
import java.sql.SQLException;

import controlls.mainScreen.mainController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.databaseControllers.DatabaseAccessor;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		mainController scene = new mainController();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) throws SQLException, IOException {
		launch(args);
		/*--------*/

		DatabaseAccessor.getInstance();
	}
}
