package application;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class UpdatePage extends BorderPane {

	private TextField idTf;
	private TextField nameTf;
	private TextField ageTf;
	private MyButton updateB;
	private MyButton clearB;
	private MyButton cancelB;
	private User selecteduser;

	public UpdatePage(User user) {
		this.selecteduser = user;
//	create labels for the text fields
		Label idL = new Label("ID");
		Label nameL = new Label("Name");
		Label ageL = new Label("Age");

//	Initialize the text fields
		idTf = new TextField();
		idTf.setPrefHeight(30);
		idTf.setPrefWidth(200);
		idTf.setText(selecteduser.getId());
		idTf.setDisable(true);
		nameTf = new TextField();
		nameTf.setPrefHeight(30);
		nameTf.setPrefWidth(200);
		nameTf.setText(selecteduser.getName());
		ageTf = new TextField();
		ageTf.setPrefHeight(30);
		ageTf.setPrefWidth(200);
		ageTf.setText(String.valueOf(selecteduser.getAge()));

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

		this.setTop(manageL);
		this.setCenter(textFieldsGp);
		this.setId("myBorderPane"); // Assign a unique ID

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

		this.setBottom(buttonsHb);

		this.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		clearB.setOnAction(e -> {
			idTf.setText(null);
			nameTf.setText(null);
			ageTf.setText(null);

		});

		updateB.setOnAction(e -> {
			ConfirmAlert confirmDelete = new ConfirmAlert("Are you sure you want to Update this user?");
			Optional<ButtonType> result = confirmDelete.getAlert().showAndWait();
			if (result.isPresent() && result.get() == confirmDelete.getSureButton()) {
				try {
					selecteduser.setName(nameTf.getText());
					selecteduser.setAge(Integer.parseInt(ageTf.getText()));
					UserManagement.getUsersTable().refresh();
				} catch (NumberFormatException e1) {
					new ErrorAlert("Error: The age must e numbers");
				} catch (IllegalArgumentException e2) {
					new ErrorAlert(e2.getMessage());
				}

			}

		});
	}

	public User getSelecteduser() {
		return selecteduser;
	}

	public void setSelecteduser(User selecteduser) {
		this.selecteduser = selecteduser;
	}
}
