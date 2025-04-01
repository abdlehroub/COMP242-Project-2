package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;


public class Main extends Application {

	static LinkedList<User> usersList = new LinkedList<User>();
	static ObservableList<User> usersObList = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) {
		try {
			HomeScene home = new HomeScene();
			primaryStage.setScene(home);
			primaryStage.setMaximized(true);
			primaryStage.setTitle("AE Library");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
