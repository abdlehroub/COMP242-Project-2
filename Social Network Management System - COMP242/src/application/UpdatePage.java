package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class UpdatePage extends Scene {

	static BorderPane addPane = new BorderPane();
	private TextField idTf;
	private TextField nameTf;
	private TextField ageTf;
	private MyButton updateB;
	private MyButton clearB;
	private MyButton cancelB;

	public UpdatePage(double h, double w, User user) {
		super(addPane, h, w);

//	create labels for the text fields
		Label idL = new Label("ID");
		Label nameL = new Label("Name");
		Label ageL = new Label("Age");

//	Initialize the text fields
		idTf = new TextField();
		idTf.setPrefHeight(30);
		idTf.setPrefWidth(200);
		idTf.setText(user.getId());
		nameTf = new TextField();
		nameTf.setPrefHeight(30);
		nameTf.setPrefWidth(200);
		nameTf.setText(user.getName());
		ageTf = new TextField();
		ageTf.setPrefHeight(30);
		ageTf.setPrefWidth(200);
		ageTf.setText(String.valueOf(user.getAge()));

		GridPane textFieldsGp = new GridPane();
		textFieldsGp.addColumn(0, idL, idTf);
		textFieldsGp.addColumn(1, nameL, nameTf, ageL, ageTf);
		textFieldsGp.setHgap(140);
		textFieldsGp.setVgap(5);
		textFieldsGp.setPadding(new Insets(10, 0, 0, 30));
		textFieldsGp.getStyleClass().add("custom-grid-pane");
		textFieldsGp.setPrefSize(350, 200); // Preferred size
		textFieldsGp.setMinSize(350, 250); // Minimum size
		textFieldsGp.setMaxSize(570, 270); // Maximum size

		MyLabel manageL = new MyLabel("Edit Book");
		manageL.setPadding(new Insets(20, 0, 0, 30));

		addPane.setTop(manageL);
		addPane.setCenter(textFieldsGp);
		addPane.setId("myBorderPane"); // Assign a unique ID

		HBox buttonsHb = new HBox();
		updateB = new MyButton();
		updateB.setText("Update");
		updateB.setPrefHeight(30);
		updateB.setPrefWidth(70);
		clearB = new MyButton();
		clearB.setText("Clear");
		clearB.setPrefHeight(30);
		clearB.setPrefWidth(70);
		cancelB = new MyButton();
		cancelB.setText("Cancel");
		cancelB.setPrefHeight(30);
		cancelB.setPrefWidth(70);
		buttonsHb.setSpacing(30);
		buttonsHb.setAlignment(Pos.CENTER);
		buttonsHb.setPadding(new Insets(0, 0, 20, 0));

		buttonsHb.getChildren().addAll(updateB, clearB, cancelB);

		addPane.setBottom(buttonsHb);

		this.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		clearB.setOnAction(e -> {
			idTf.setText(null);
			nameTf.setText(null);
			ageTf.setText(null);

		});

	}
}
