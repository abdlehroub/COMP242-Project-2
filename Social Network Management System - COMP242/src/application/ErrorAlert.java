package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorAlert {

	public ErrorAlert(String message) {
		showErrorAlert(message);
		
	}

	public void showErrorAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		alert.setHeaderText(message);
		alert.showAndWait();
	}

}
