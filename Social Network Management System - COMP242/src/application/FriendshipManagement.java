package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FriendshipManagement extends BorderPane {
	private static TableView<User> usersTable;
	private static ObservableList<User> friendsList;
	private static User currUser;
	private TextField searchTf;
	private MyButton searchB;
	private MyButton addB;
	private MyButton deleteB;

	@SuppressWarnings({ "unchecked", "deprecation" })
	public FriendshipManagement() {

//		The outer container of the content of this page
		VBox manageVb = new VBox();
		manageVb.getStyleClass().add("rounded-vbox");

//		Label as title of the page
		MyLabel manageL = new MyLabel("Friendship Management");
		manageL.setPadding(new Insets(20, 0, 20, 20));
		this.setTop(manageL);

//	    HBox for the upper buttons and text field
		HBox lowerHb = new HBox();
		HBox upperHb = new HBox();

//		Search TextField
		searchTf = new TextField();
		searchTf.setPrefHeight(30);
		searchTf.setPrefWidth(400);
		searchTf.setPromptText("Enter the User ID:");

		searchB = new MyButton();
		searchB.setText("Search");
		searchB.setPrefHeight(26);
		searchB.setPrefWidth(80);

		upperHb.getChildren().addAll(searchTf, searchB);
		upperHb.setSpacing(20);

//		Button to Add Friends for the Selected User manually
		addB = new MyButton();
		addB.setText("Add Friend");
		addB.setPrefHeight(30);
		addB.setPrefWidth(120);
		addB.setDisable(true);

//		Button to Delete user from the friends list
		deleteB = new MyButton();
		deleteB.setText("Delete Friend");
		deleteB.setPrefHeight(30);
		deleteB.setPrefWidth(140);
		deleteB.setDisable(true);

//		Add the items of the lower HBox to it
		lowerHb.getChildren().addAll(addB, deleteB);
		HBox.setMargin(searchTf, new Insets(0, 650, 0, 2));
		lowerHb.setSpacing(10);
		lowerHb.setAlignment(Pos.CENTER);

//		Create TableView to display the list of friends of the current user 
		usersTable = new TableView<User>();
		TableColumn<User, String> id = new TableColumn<User, String>("UserID");
		TableColumn<User, String> title = new TableColumn<User, String>("User Name");
		TableColumn<User, Integer> age = new TableColumn<User, Integer>("Age");
		usersTable.setStyle("  -fx-background-color: white;\r\n" + "    -fx-background-radius: 10;\r\n"
				+ "    -fx-border-radius: 10;\r\n" + "    -fx-border-color: #E5E7EB;\r\n" + "    -fx-border-width: 1;");
		id.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
		title.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		age.setCellValueFactory(new PropertyValueFactory<User, Integer>("age"));
		usersTable.autosize();
		usersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		usersTable.setItems(friendsList);
		usersTable.setPrefHeight(550);
		usersTable.getColumns().addAll(id, title, age);

		manageVb.getChildren().addAll(upperHb, usersTable, lowerHb);
		this.setCenter(manageVb);
		this.setPadding(new Insets(0, 10, 10, 10));

//		Search Action to display and do operations on specific user
		searchB.setOnAction(e -> {
			currUser = searchUser();
			if (currUser != null) {
				friendsList = FXCollections.observableArrayList(currUser.getFriendsList().toArrayList());
				addB.setDisable(false);
				deleteB.setDisable(false);
				usersTable.setItems(friendsList);
			}

		});

//		Delete Action to remove friend selected from the table of friends from the list of friends of the selected user
		deleteB.setOnAction(e -> {
			User selectedUser = usersTable.getSelectionModel().getSelectedItem();
			if (selectedUser != null) {
				currUser.getFriendsList().remove(selectedUser);
				friendsList.remove(selectedUser);
			} else {
				new ErrorAlert("There is No User selected!");
			}

		});

//		Open new window to add friends from the users list
		Stage addFriendStage = new Stage();
		addFriendStage.setTitle("Add Friend");
		AddFriendPage addPage = new AddFriendPage(600, 400);
		addFriendStage.setResizable(false);
		addFriendStage.setScene(addPage);
//		Action to display the add stage
		addB.setOnAction(e -> {
			addPage.setCurrUser(currUser);
			addPage.suggestedFriends();
			addFriendStage.show();

		});
	}

//	Search user by this ID
	public User searchUser() {
		for (int i = 0; i < Main.usersList.size(); i++) {
			if (Main.usersList.get(i).getId().equals(searchTf.getText())) {
				return Main.usersList.get(i);
			}
		}
		return null;
	}

//	Refresh the data of the table of friends of the current user
	public static void refreshData() {
		friendsList = FXCollections.observableArrayList(currUser.getFriendsList().toArrayList());
		usersTable.setItems(friendsList);
	}

}
