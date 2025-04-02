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
			User user = new User("123", "Omar", 20);
			User user2 = new User("124", "Zaid", 40);
			User user3 = new User("125", "Ahmad", 27);

			user.addFreind(user3);
			
			usersList.addFirst(user);
			usersList.addFirst(user2);
			usersList.addFirst(user3);
			
			usersObList.addAll(usersList.toArrayList());

			
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
