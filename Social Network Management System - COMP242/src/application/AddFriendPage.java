package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AddFriendPage extends Scene {
	private static BorderPane AddFriendBp = new BorderPane();
	private TableView<User> usersTable;
	private User currUser;

	@SuppressWarnings({ "unchecked", "deprecation" })
	public AddFriendPage(double h, double w) {
		super(AddFriendBp, h, w);

//		Create VBox contains the table view
		VBox tableVb = new VBox();

//		Create the table and its columns and set the table style 
		usersTable = new TableView<User>();
		TableColumn<User, String> id = new TableColumn<User, String>("UserID");
		TableColumn<User, String> title = new TableColumn<User, String>("User Name");
		TableColumn<User, Integer> age = new TableColumn<User, Integer>("Age");
		TableColumn<User, Void> buttonCol = new TableColumn<>("Add");
		buttonCol.setCellFactory(col -> new TableCell<>() {
			private final MyButton button = new MyButton();

			{
				// Button to add friends and its action
				button.setText("Add Friend");
				button.setOnAction(e -> {
					User friend = getTableView().getItems().get(getIndex());
					button.setDisable(true);
					currUser.getFriendsList().addFirst(friend);
					FriendshipManagement.refreshData();
				});
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(button);
				}
			}
		});
		usersTable.setStyle("  -fx-background-color: white;\r\n" + "    -fx-background-radius: 10;\r\n"
				+ "    -fx-border-radius: 10;\r\n" + "    -fx-border-color: #E5E7EB;\r\n" + "    -fx-border-width: 1;");
		usersTable.getColumns().addAll(id, title, age, buttonCol);

		id.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
		title.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		age.setCellValueFactory(new PropertyValueFactory<User, Integer>("age"));
		usersTable.autosize();
		usersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//		Add the table to the VBox
		tableVb.getChildren().add(usersTable);
		this.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

//		Create a label for this page
		MyLabel pageTitle = new MyLabel("Add Friends");
		pageTitle.setPadding(new Insets(20, 0, 10, 30));

		tableVb.getStyleClass().add("rounded-vbox");
		AddFriendBp.setId("myBorderPane");

		AddFriendBp.setPadding(new Insets(0, 10, 10, 10));

		AddFriendBp.setTop(pageTitle);

		AddFriendBp.setCenter(tableVb);

	}

//	Display all users are not friends of the current user
	public void suggestedFriends() {
		ObservableList<User> list = FXCollections.observableArrayList();
		for (int i = 0; i < Main.usersList.size(); i++) {
			if ((!currUser.getFriendsList().contains(Main.usersList.get(i))) && Main.usersList.get(i) != currUser)
				list.add(Main.usersList.get(i));
		}
		usersTable.setItems(list);
	}

	public User getCurrUser() {
		return currUser;
	}

	public void setCurrUser(User currUser) {
		this.currUser = currUser;
	}

}
