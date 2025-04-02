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
			User user4 = new User("125", "Wadee", 30);
			User user5 = new User("125", "Waseem", 41);
			User user6 = new User("125", "Jihad", 19);

			user.addFreind(user3);
			
			usersList.addFirst(user);
			usersList.addFirst(user2);
			usersList.addFirst(user3);
			usersList.addFirst(user4);
			usersList.addFirst(user5);
			usersList.addFirst(user6);


			
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
