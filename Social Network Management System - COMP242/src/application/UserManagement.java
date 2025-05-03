package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UserManagement extends BorderPane {
	private static TableView<User> usersTable;
	private TextField searchTf;
	private File file;
	private MyButton addB;

	@SuppressWarnings({ "unchecked", "deprecation" })
	public UserManagement() {

//		The outer container of the content of this page
		VBox manageVb = new VBox();
		manageVb.getStyleClass().add("rounded-vbox");

//		Label as title of the page
		MyLabel manageL = new MyLabel("User Management");
		manageL.setPadding(new Insets(20, 0, 20, 20));
		this.setTop(manageL);

//	    HBox for the upper buttons and text field
		HBox upperHb = new HBox();

//		Search TextField
		searchTf = new TextField();
		searchTf.setPrefHeight(30);
		searchTf.setPrefWidth(300);
		searchTf.setPromptText("Search Users");

//		RadioButtons to select the filter type
		ToggleGroup group = new ToggleGroup();
		RadioButton nameRb = new RadioButton("Name");
		nameRb.setToggleGroup(group);
		nameRb.setPrefSize(60, 30);
		RadioButton idRb = new RadioButton("ID");
		idRb.setToggleGroup(group);
		idRb.setPrefSize(30, 30);
		idRb.setSelected(true);

//		Button to add User manually
		addB = new MyButton();
		addB.setText("Add");
		addB.setPrefHeight(25);
		addB.setPrefWidth(70);

		ToggleGroup sortGroup = new ToggleGroup();
		RadioButton ascRb = new RadioButton("ASC");
		RadioButton descRb = new RadioButton("DESC");
		ascRb.setSelected(true);
		ascRb.setToggleGroup(sortGroup);
		descRb.setToggleGroup(sortGroup);
		HBox radioButtonsHb = new HBox(ascRb, descRb);

//		ContextMenu to give more options 
		ContextMenu actionsM = new ContextMenu();
		MyButton actionsB = new MyButton();
		actionsB.setText("More Actions");
		actionsB.setPrefHeight(25);
		actionsB.setPrefWidth(140);

//		Items of the ContextMenu
		MenuItem deleteMi = new MenuItem("Delete User");
		MenuItem exportMi = (new MenuItem("Export to file"));
		MenuItem loadMi = (new MenuItem("Load from file"));
		MenuItem editMi = new MenuItem("Edit User");
		actionsM.getItems().addAll(loadMi, exportMi, editMi, deleteMi);

//		Add the items of the upper HBox to it
		upperHb.getChildren().addAll(searchTf, nameRb, idRb, addB, actionsB, radioButtonsHb);
		HBox.setMargin(idRb, new Insets(0, 300, 0, 0));
		HBox.setMargin(searchTf, new Insets(0, 0, 0, 2));
		upperHb.setSpacing(10);
		upperHb.setAlignment(Pos.CENTER);

//		Create TableView to display the users in the system
		usersTable = new TableView<User>();
		TableColumn<User, String> id = new TableColumn<User, String>("UserID");
		TableColumn<User, String> name = new TableColumn<User, String>("User Name");
		TableColumn<User, Integer> age = new TableColumn<User, Integer>("Age");
		usersTable.setStyle("  -fx-background-color: white;\r\n" + "    -fx-background-radius: 10;\r\n"
				+ "    -fx-border-radius: 10;\r\n" + "    -fx-border-color: #E5E7EB;\r\n" + "    -fx-border-width: 1;");
		usersTable.getColumns().addAll(id, name, age);
		id.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
		name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		age.setCellValueFactory(new PropertyValueFactory<User, Integer>("age"));
		usersTable.autosize();
		usersTable.setMinHeight(550);
		usersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		usersTable.setItems(Main.usersObList);

		manageVb.getChildren().addAll(upperHb, usersTable);
		this.setCenter(manageVb);
		this.setPadding(new Insets(0, 10, 10, 10));

//		Action to display the context menu
		actionsB.setOnAction(e -> {
			actionsM.show(actionsB, Side.BOTTOM, 0, 10);
		});

		Stage addStage = new Stage();
		addStage.setTitle("Add User");
		AddUserPage addUserPage = new AddUserPage(600, 400);
		addStage.setResizable(false);
		addStage.setScene(addUserPage);

//		Action to display the add stage
		addB.setOnAction(e -> {
			addStage.show();
		});

		Stage updateStage = new Stage();
		Scene updateScene = new Scene(new Pane(), 600, 400);
		updateStage.setResizable(false);
		updateStage.setScene(updateScene);
		editMi.setOnAction(e -> {
			User selectedUser = usersTable.getSelectionModel().getSelectedItem();
			if (selectedUser != null) {
				UpdatePage updatePage = new UpdatePage(selectedUser);
				updateScene.setRoot(updatePage);
				updateStage.show();
			} else {
				new ErrorAlert("There is No User selected!");
			}
		});

		deleteMi.setOnAction(e -> {
			deleteUser();
		});

		searchTf.setOnKeyTyped(e -> {
			if (!searchTf.getText().isEmpty())
				if (idRb.isSelected())
					filtered("ID");
				else
					filtered("Name");
			else
				usersTable.setItems(Main.usersObList);
		});
		loadMi.setOnAction(e -> {
			try {
				readFromFile();
			} catch (FileNotFoundException e1) {
				new ErrorAlert("Error: No File Selected");
			} catch (NullPointerException e2) {
				new ErrorAlert("Error: No File Selected");
			}
		});

		exportMi.setOnAction(e -> {
			try {
				exportToFile(ascRb.isSelected());
			} catch (FileNotFoundException e1) {
				new ErrorAlert("Error: File Not found!");
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
			try {
//				Read full line from file and split it by using comma as delimiter
				String[] line = in.nextLine().split(",");
				String id = line[0];
				String name = line[1];
				int age = Integer.parseInt(line[2]);
				User user = new User(id, name, age);
				Main.usersList.insertSorted(user);
				Main.usersObList.add(user);
			} catch (NumberFormatException e) {
				errorCount++;
			} catch (IllegalArgumentException e) {
				errorCount++;
			} catch (NullPointerException e) {
				new ErrorAlert("Error: No File Selected");
			} catch (ArrayIndexOutOfBoundsException e) {
				new ErrorAlert("Error: Invalid File Format");
			}

		}
		if (errorCount > 0)
			new ErrorAlert("Error: File data are loaded with [" + errorCount + "] Errors!");
		else
			new SuccessAlert("Great: File data are loaded without any error!");

	}

	public void exportToFile(boolean asc) throws FileNotFoundException {
		fileChooser();
		PrintWriter out = new PrintWriter(file);
		if (asc) {
//			If the user select the ascending choice
			for (User user : Main.usersList) {
				out.println(user.getId() + "," + user.getName() + "," + user.getAge());
			}
		} else {
			for (User user : Main.usersList.reverseIterable()) {
				out.println(user.getId() + "," + user.getName() + "," + user.getAge());
			}
		}
		out.close();

	}

//	method to open file chooser dialog
	public void fileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		file = fileChooser.showOpenDialog(new Stage());
	}

//	Display the users depends on the search TextField
	public void filtered(String filterBy) {
		ObservableList<User> filterdList = FXCollections.observableArrayList();
		for (User user : usersTable.getItems()) {
			if (filterBy.equals("Name")) {
				if (user.getName().toLowerCase().trim().startsWith(searchTf.getText().toLowerCase().trim())) {
					filterdList.add(user);
				}
			} else {
				if (user.getId().toLowerCase().trim().startsWith(searchTf.getText().toLowerCase().trim())) {
					filterdList.add(user);
				}
			}
		}
		usersTable.setItems(filterdList);

	}

//	Method to delete user from the array of books
	public void deleteUser() {
//		Get the book selected in the table view
		User selectedUser = usersTable.getSelectionModel().getSelectedItem();
		if (selectedUser != null) {
			for (User user : Main.usersList) {
				user.getFriendsList().remove(selectedUser);
				for (Post post : user.getPostsCreatedList()) {
					post.removeSharedWith(selectedUser);
				}
			}
			Main.usersList.remove(selectedUser);
			Main.usersObList.remove(selectedUser);
			User.getIds().remove(selectedUser.getId());
		} else {
			new ErrorAlert("There is No User selected!");
		}

	}

	public static TableView<User> getUsersTable() {
		return usersTable;
	}

	public static void setUsersTable(TableView<User> usersTable) {
		UserManagement.usersTable = usersTable;
	}


	public MyButton getAddB() {
		return addB;
	}


	public void setAddB(MyButton addB) {
		this.addB = addB;
	}
	
	

}
