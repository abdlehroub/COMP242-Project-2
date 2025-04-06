package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddUserPage extends Scene {
	private static BorderPane addPane = new BorderPane();
	private TextField idTf;
	private TextField nameTf;
	private TextField ageTf;
	private ListView<User> freindsLv;
	private MyButton addB;
	private MyButton clearB;
	private MyButton cancelB;

	public AddUserPage(double h, double w) {
		super(addPane, h, w);

//		create labels for the text fields
		Label idL = new Label("ID");
		Label nameL = new Label("Name");
		Label ageL = new Label("Age");
		Label addFriends = new Label("Add Friends");

//		Initialize the text fields
		idTf = new TextField();
		idTf.setPrefHeight(30);
		idTf.setPrefWidth(200);
		idTf.setPromptText("Enter the User ID");
		nameTf = new TextField();
		nameTf.setPrefHeight(30);
		nameTf.setPrefWidth(200);
		nameTf.setPromptText("Enter the User name");
		ageTf = new TextField();
		ageTf.setPrefHeight(30);
		ageTf.setPrefWidth(200);
		ageTf.setPromptText("Enter the Age");

//		Make the ListView to suggest friends
		freindsLv = new ListView<User>(Main.usersObList);
		freindsLv.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE); // Enable
																										// multi-select
		freindsLv.setMaxHeight(100);
		freindsLv.setMaxWidth(400);
		VBox freindsVb = new VBox(addFriends, freindsLv);
		freindsVb.setSpacing(10);

//		Add the text fields to the grid Pane and Change the size and spacing
		GridPane textFieldsGp = new GridPane();
		textFieldsGp.addColumn(0, idL, idTf, freindsVb);
		VBox ageVb = new VBox(ageL, ageTf);
		ageVb.setSpacing(10);
		textFieldsGp.addColumn(1, nameL, nameTf, ageVb);
		textFieldsGp.setHgap(50);
		textFieldsGp.setVgap(10);
		textFieldsGp.setPadding(new Insets(10, 0, 0, 30));
		textFieldsGp.getStyleClass().add("custom-grid-pane");
		textFieldsGp.setPrefSize(350, 200);
		textFieldsGp.setMinSize(350, 250);
		textFieldsGp.setMaxSize(570, 270);

//		Create the title of the page label
		MyLabel addTitleL = new MyLabel("Add User");
		addTitleL.setPadding(new Insets(20, 0, 0, 30));

		addPane.setTop(addTitleL);
		addPane.setCenter(textFieldsGp);
		addPane.setId("myBorderPane"); // Assign a unique ID

//		Create HBox contains the buttons of the add page
		HBox buttonsHb = new HBox();

//		Create add Button to add Book
		addB = new MyButton();
		addB.setText("Add");
		addB.setPrefHeight(30);
		addB.setPrefWidth(70);

//		Create clear Button to clear the content of the text fields
		clearB = new MyButton();
		clearB.setText("Clear");
		clearB.setPrefHeight(30);
		clearB.setPrefWidth(70);

//		Create cancel Button to close the page
		cancelB = new MyButton();
		cancelB.setText("Cancel");
		cancelB.setPrefHeight(30);
		cancelB.setPrefWidth(70);
		buttonsHb.setSpacing(30);
		buttonsHb.setAlignment(Pos.CENTER);
		buttonsHb.setPadding(new Insets(0, 0, 20, 0));

//		Add the buttons to the HBox
		buttonsHb.getChildren().addAll(addB, clearB, cancelB);

		addPane.setBottom(buttonsHb);

		this.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

//		Action to clear the content of the text fields
		clearB.setOnAction(e -> {
			idTf.setText(null);
			nameTf.setText(null);
			ageTf.setText(null);

		});

	}

	public MyButton getCancelB() {
		return cancelB;
	}

	public MyButton getClearB() {
		return clearB;
	}

}
