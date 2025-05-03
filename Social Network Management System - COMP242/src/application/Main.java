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
		HomeScene home = new HomeScene();
		primaryStage.setScene(home);
		primaryStage.setMaximized(true);
		primaryStage.setResizable(false);
		primaryStage.setTitle("BZU Social Network");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
