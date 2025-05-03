package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AddFriendPage extends Scene {
	private static BorderPane AddFriendBp = new BorderPane();
	private ObservableList<User> list;
	private TableView<User> usersTable;
	private User currUser;
	private TextField searchTf;
	TableColumn<User, String> id;
	TableColumn<User, Integer> age;
	TableColumn<User, String> name;
	private TableColumn<User, Void> buttonCol;

	@SuppressWarnings({ "unchecked", "deprecation" })
	public AddFriendPage(double h, double w) {
		super(AddFriendBp, h, w);

//		Create VBox contains the table view
		VBox tableVb = new VBox();

//		Search TextField
		searchTf = new TextField();
		searchTf.setPrefHeight(30);
		searchTf.setPrefWidth(400);
		searchTf.setPromptText("Search By User Name:");

//		Create the table and its columns and set the table style 
		usersTable = new TableView<User>();
		id = new TableColumn<User, String>("UserID");
		name = new TableColumn<User, String>("User Name");
		age = new TableColumn<User, Integer>("Age");
		buttonCol = new TableColumn<>("Add");
		usersTable.setStyle("  -fx-background-color: white;\r\n" + "    -fx-background-radius: 10;\r\n"
				+ "    -fx-border-radius: 10;\r\n" + "    -fx-border-color: #E5E7EB;\r\n" + "    -fx-border-width: 1;");
		usersTable.getColumns().addAll(id, name, age, buttonCol);
		id.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
		name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		age.setCellValueFactory(new PropertyValueFactory<User, Integer>("age"));
		usersTable.autosize();
		usersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

//		Add the table to the VBox
		tableVb.getChildren().addAll(searchTf, usersTable);
		this.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

//		Create a label for this page
		MyLabel pageTitle = new MyLabel("Add Friends");
		pageTitle.setPadding(new Insets(20, 0, 10, 30));

		tableVb.getStyleClass().add("rounded-vbox");
		AddFriendBp.setId("myBorderPane");

		AddFriendBp.setPadding(new Insets(0, 10, 10, 10));

		AddFriendBp.setTop(pageTitle);

		AddFriendBp.setCenter(tableVb);

		searchTf.setOnKeyTyped(e -> {
			if (!searchTf.getText().isEmpty())
				filtered();
			else
				usersTable.setItems(list);
		});

	}

//	Display all users are not friends of the current user
	public void suggestedFriends() {
		list = FXCollections.observableArrayList();
		for (User user : Main.usersList) {
			if ((!currUser.getFriendsList().contains(user)) && user != currUser && currUser != null)
				list.add(user);
		}
		usersTable.setItems(list);
		addButtonToTable();
	}

//	Display the users depends on the search TextField
	public void filtered() {
		ObservableList<User> filterdList = FXCollections.observableArrayList();
		for (User user : usersTable.getItems()) {
			if (user.getName().toLowerCase().trim().startsWith(searchTf.getText().toLowerCase().trim())) {
				filterdList.add(user);
			}
		}
		usersTable.setItems(filterdList);
		addButtonToTable();

	}

//	Add Button AddFriend to the TableView
	public void addButtonToTable() {
		buttonCol.setCellFactory(col -> new TableCell<>() {
			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || getIndex() >= getTableView().getItems().size()) {
					setGraphic(null);
				} else {
					// Get the correct User for this row
					User friend = getTableView().getItems().get(getIndex());

					// Create a new button for each user
					MyButton button = new MyButton();
					button.setText("Add Friend");

					// Disable the button if the user is already a friend
					button.setDisable(currUser.getFriendsList().contains(friend));

					// Action for the button
					button.setOnAction(e -> {
						button.setDisable(true); // Disable after clicking
						currUser.getFriendsList().insertSorted(friend);
						FriendshipManagement.refreshData();

						// Refresh the table to update button states
						getTableView().refresh();
					});

					// Set the button to the cell
					setGraphic(button);
				}
			}
		});
	}

	public User getCurrUser() {
		return currUser;
	}

	public void setCurrUser(User currUser) {
		this.currUser = currUser;
	}

}
