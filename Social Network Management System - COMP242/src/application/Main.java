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
			User user4 = new User("126", "Wadee", 30);
			User user5 = new User("127", "Waseem", 41);
			User user6 = new User("128", "Jihad", 19);

			user.addFreind(user3);
			
			usersList.addFirst(user);
			usersList.addFirst(user2);
			usersList.addFirst(user3);
			usersList.addFirst(user4);
			usersList.addFirst(user5);
			usersList.addFirst(user6);
//			Post psot = new Post(1, user6, "hello", new GregorianCalendar(2022,1,1));

//			psot.addSharedWith(user5);
//			psot.addSharedWith(user);
//			psot.addSharedWith(user2);



			
			usersObList.addAll(usersList.toArrayList());

			
			HomeScene home = new HomeScene();
			primaryStage.setScene(home);
			primaryStage.setMaximized(true);
			primaryStage.setResizable(false);
			primaryStage.setTitle("AE Social Network");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
