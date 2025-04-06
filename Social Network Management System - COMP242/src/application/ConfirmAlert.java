package application;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;

public class ConfirmAlert {

	private Alert alert = new Alert(Alert.AlertType.INFORMATION);
	private ButtonType sureButton = new ButtonType("Yes", ButtonData.OK_DONE);
	private ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

	public ConfirmAlert(String message) {
		showConfirmAlert(message);
	}

	public void showConfirmAlert(String message) {
		
		alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Sure?");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.getButtonTypes().setAll(sureButton, cancelButton);
		alert.getDialogPane().getStylesheets().add(getClass().getResource("application.css").toExternalForm());

	}

	public ButtonType getSureButton() {
		return this.sureButton;
	}

	public Alert getAlert() {
		return alert;
	}
}
