package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserManagement extends BorderPane {
	private TableView<User> usersTable;

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
		TextField searchTf = new TextField();
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
		MyButton addB = new MyButton();
		addB.setText("Add");
		addB.setPrefHeight(25);
		addB.setPrefWidth(70);

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
		upperHb.getChildren().addAll(searchTf, nameRb, idRb, addB, actionsB);
		HBox.setMargin(idRb, new Insets(0, 470, 0, 0));
		HBox.setMargin(searchTf, new Insets(0, 0, 0, 2));
		upperHb.setSpacing(10);
		upperHb.setAlignment(Pos.CENTER);

//		Create TableView to display the users in the system
		usersTable = new TableView<User>();
		TableColumn<User, String> id = new TableColumn<User, String>("UserID");
		TableColumn<User, String> title = new TableColumn<User, String>("User Name");
		TableColumn<User, Integer> age = new TableColumn<User, Integer>("Age");
		usersTable.setStyle("  -fx-background-color: white;\r\n" + "    -fx-background-radius: 10;\r\n"
				+ "    -fx-border-radius: 10;\r\n" + "    -fx-border-color: #E5E7EB;\r\n" + "    -fx-border-width: 1;");
		usersTable.getColumns().addAll(id, title, age);
		id.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
		title.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
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

		editMi.setOnAction(e -> {
			editUser();
		});
		
		deleteMi.setOnAction(e->{
			deleteBook();
		});

	}

	public void editUser() {
		User selectedBook = usersTable.getSelectionModel().getSelectedItem();
		if (selectedBook != null) {
//			Create stage to update Selected book from the table view
			Stage updateStage = new Stage();
			UpdatePage updatePage = new UpdatePage(600, 400, selectedBook);
			updateStage.setResizable(false);
			updateStage.setScene(updatePage);
			updateStage.show();

		} else {
			new ErrorAlert("There is No User selected!");
		}
	}

//	Method to delete book from the array of books
	public void deleteBook() {
//		Get the book selected in the table view
		User selectedUser = usersTable.getSelectionModel().getSelectedItem();
		if (selectedUser != null) {
			Main.usersList.remove(selectedUser);
			Main.usersObList.remove(selectedUser);
		} else {
			new ErrorAlert("There is No User selected!");
		}

	}

}
