package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FriendshipManagement extends BorderPane {
	private static TableView<User> usersTable;
	private static ObservableList<User> friendsList;
	private static User currUser;
	private TextField searchTf;
	private MyButton searchB;
	private MyButton addB;
	private MyButton deleteB;
	private File file;

	@SuppressWarnings({ "unchecked", "deprecation" })
	public FriendshipManagement() {

//		The outer container of the content of this page
		VBox manageVb = new VBox();
		manageVb.getStyleClass().add("rounded-vbox");

//		Label as title of the page
		MyLabel titleL = new MyLabel("Friendship Management");
		titleL.setPadding(new Insets(20, 0, 20, 20));
		this.setTop(titleL);

//	    HBox for the upper buttons and text field
		HBox lowerHb = new HBox();
		HBox upperHb = new HBox();

//		Show the current user name and id
		MyLabel userL = new MyLabel("User: " + currUser);
		userL.setStyle("-fx-font-size: 15px; -fx-font-style: italic; -fx-text-fill: #635bff; -fx-font-weight: bold;");

//		Search TextField
		searchTf = new TextField();
		searchTf.setPrefHeight(30);
		searchTf.setPrefWidth(400);
		searchTf.setPromptText("Enter the User ID:");

		searchB = new MyButton();
		searchB.setText("Search");
		searchB.setPrefHeight(26);
		searchB.setPrefWidth(80);

//		ContextMenu to give more options 
		ContextMenu actionsM = new ContextMenu();
		MyButton actionsB = new MyButton();
		actionsB.setText("More Actions");
		actionsB.setPrefHeight(25);
		actionsB.setPrefWidth(140);

//		Items of the ContextMenu
		MenuItem exportMi = (new MenuItem("Export to file"));
		MenuItem loadMi = (new MenuItem("Load from file"));
		actionsM.getItems().addAll(loadMi, exportMi);

		upperHb.getChildren().addAll(searchTf, searchB, actionsB, userL);
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
		HBox.setMargin(actionsB, new Insets(0, 310, 0, 2));
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

		actionsB.setOnAction(e -> {
			actionsM.show(actionsB, Side.BOTTOM, 0, 10);
		});

//		Search Action to display and do operations on specific user
		searchB.setOnAction(e -> {
			currUser = searchUser();
			if (currUser != null) {
				userL.setText("User: " + currUser);
				friendsList = FXCollections.observableList(currUser.getFriendsList().toArrayList());
				addB.setDisable(false);
				deleteB.setDisable(false);
				usersTable.setItems(friendsList);
			}

		});

//		Delete Action to remove friend selected from the table of friends from the list of friends of the selected user
		deleteB.setOnAction(e -> {
			User selectedUser = usersTable.getSelectionModel().getSelectedItem();
			if (selectedUser != null) {
				for (Post post : selectedUser.getSharedWithThemPostsList()) {
					if (post.getCreator() == currUser) {
						selectedUser.getSharedWithThemPostsList().remove(post);
						post.removeSharedWith(selectedUser);
					}
				}
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

//		Action to load data from txt file
		loadMi.setOnAction(e -> {
			try {
				readFromFile();
			} catch (FileNotFoundException e1) {
				new ErrorAlert("Error: File not found!");
			} catch (NullPointerException e2) {
				new ErrorAlert("Error: No File Selected");
			}
		});

//		Action to export the data on txt file
		exportMi.setOnAction(e -> {
			try {
				exportToFile();
			} catch (FileNotFoundException e1) {
				new ErrorAlert("Error: File not found!");
			} catch (NullPointerException e2) {
				new ErrorAlert("Error: No File Selected");
			}
		});
	}

	public void readFromFile() throws FileNotFoundException {
//		Call the file chooser method
		fileChooser();
//		Create Scanner to read from file
		Scanner in = new Scanner(file);
		int errorCount = 0;
		while (in.hasNext()) {
			String userId = null;
			try {
//				Read full line from file and split it by using comma as delimiter
				String[] line = in.nextLine().split(",");
				userId = line[0];
				User currUser = null;
				for (User user : Main.usersList) {
					if (user.getId().equals(userId)) {
						currUser = user;
					}
				}
//				Validation and add the friend if is valid
				if (currUser != null) {
					for (int i = 1; i < line.length; i++) {
						for (User user : Main.usersList) {
							if (user.getId().equals(line[i]) && user != currUser
									&& !currUser.getFriendsList().contains(user)) {
								currUser.getFriendsList().insertSorted(user);
								break;
							} else if ((!user.getId().equals(line[i])) && user == Main.usersList.getLast()) {
								errorCount++;
							}
						}
					}
				}
			} catch (NumberFormatException e) {
				errorCount++;
			} catch (IllegalArgumentException e) {
				errorCount++;
			} catch (NullPointerException e) {
				errorCount++;
			}
		}
		if (errorCount > 0)
			new ErrorAlert("Error: File data are loaded with [" + errorCount + "] Errors!");
		else
			new SuccessAlert("Great: File data are loaded without any error!");
	}

//	Export the data to txt file
	public void exportToFile() throws FileNotFoundException {
		fileChooser();
		PrintWriter out = new PrintWriter(file);
		for (User user : Main.usersList) {
			if (user.getFriendsList().isEmpty()) {
				continue;
			}
			out.print(user.getId() + ",");
			for (User friend : user.getFriendsList()) {
				if (friend == user.getFriendsList().getLast()) {
					out.print(friend.getId());
					continue;
				}
				out.print(friend.getId() + ",");
			}
			out.println();
		}
		out.close();
	}

//	method to open file chooser dialog
	public void fileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		file = fileChooser.showOpenDialog(new Stage());
	}

//	Search user by this ID
	public User searchUser() {
		for (User user : Main.usersList) {
			if (user.getId().equals(searchTf.getText())) {
				return user;
			}
		}
		return currUser;
	}

//	Refresh the data of the table of friends of the current user
	public static void refreshData() {
		friendsList = FXCollections.observableArrayList(currUser.getFriendsList().toArrayList());
		usersTable.setItems(friendsList);
	}

}
