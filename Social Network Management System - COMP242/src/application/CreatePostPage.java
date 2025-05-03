package application;

import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreatePostPage extends Scene {
	private static BorderPane rootPane = new BorderPane();
	private MyButton createPostB;
	private MyButton clearB;
	private MyButton cancelB;
	private DatePicker datePicker;
	private LinkedList<User> selected;
	private GregorianCalendar calendar;
	private User currUser;

	public CreatePostPage(double h, double w, User currUser) {
		super(rootPane, h, w);
		this.currUser = currUser;

//		The container of the content of the page
		VBox contentVb = new VBox();
		contentVb.getStyleClass().add("rounded-vbox");
		rootPane.setPadding(new Insets(0, 10, 10, 10));

//		HBox contains the date picker and the combo box
		HBox upperHb = new HBox();
		upperHb.setSpacing(10);
		upperHb.setAlignment(Pos.CENTER);

//		ComboBox to select the posts shared with who
		Label shareWithL = new Label("Share With");
		ComboBox<String> shareWithCb = new ComboBox<String>();
		shareWithCb.getItems().addAll("All Friends", "Specific Friends");

//		DatePicker to select the creation date of the post
		Label creationDateL = new Label("Creation Date");
		datePicker = new DatePicker();
		LocalDate minDate = LocalDate.of(1900, 1, 1);
		datePicker.setDayCellFactory(new javafx.util.Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(DatePicker picker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate date, boolean empty) {
						super.updateItem(date, empty);

						// Disable dates before 1900
						if (date.isBefore(minDate)) {
							setDisable(true);
						}
					}
				};
			}
		});

//		Add the upper content to the upper HBox
		upperHb.getChildren().addAll(shareWithL, shareWithCb, creationDateL, datePicker);

//		TextArea to write the content of the post
		TextArea postContentTa = new TextArea();
		postContentTa.setMinHeight(220);
		postContentTa.setPrefWidth(200);

//		Create the title of the page label
		MyLabel titleL = new MyLabel("Create Post");
		titleL.setPadding(new Insets(10, 0, 10, 30));

//		Create HBox contains the buttons of the Create Post page
		HBox buttonsHb = new HBox();
		buttonsHb.setSpacing(30);
		buttonsHb.setAlignment(Pos.CENTER);
		buttonsHb.setPadding(new Insets(0, 0, 0, 0));

//		Create Post Button
		createPostB = new MyButton();
		createPostB.setText("Add");
		createPostB.setPrefHeight(30);
		createPostB.setPrefWidth(70);

//		Clear the content of the text fields button
		clearB = new MyButton();
		clearB.setText("Clear");
		clearB.setPrefHeight(30);
		clearB.setPrefWidth(70);

//		Cancel Button to close the page
		cancelB = new MyButton();
		cancelB.setText("Cancel");
		cancelB.setPrefHeight(30);
		cancelB.setPrefWidth(70);
		buttonsHb.setSpacing(30);
		buttonsHb.setAlignment(Pos.CENTER);
		buttonsHb.setPadding(new Insets(0, 0, 0, 0));

//		Add the buttons to the HBox and add the HBOX to the main pane
		buttonsHb.getChildren().addAll(createPostB, clearB, cancelB);
		contentVb.getChildren().addAll(upperHb, postContentTa, buttonsHb);
		rootPane.setTop(titleL);
		rootPane.setCenter(contentVb);
		rootPane.setId("myBorderPane"); // Assign a unique ID

		this.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

//		Action to clear the content of the text fields
		clearB.setOnAction(e -> {
			datePicker.setValue(null);
			postContentTa.setText(null);
			shareWithCb.setValue(null);
		});

//		Create Post with taking into consideration the selection of the ComboBox
		SelectFriendsPage selectFriends = new SelectFriendsPage(400, 400, this.currUser);
		Stage selectStage = new Stage();
		selectStage.setResizable(false);
		selectStage.setScene(selectFriends);
		createPostB.setOnAction(e -> {
			if (shareWithCb.getSelectionModel().getSelectedItem() != null && datePicker.getValue() != null) {
				if (shareWithCb.getSelectionModel().getSelectedItem().equals("All Friends")) {
					LocalDate localDate = datePicker.getValue();
					GregorianCalendar calendar = null;
					if (localDate != null) {
						// Convert LocalDate to GregorianCalendar
						calendar = convertToGregorianCalendar(localDate);
					}
					try {
						new Post(this.currUser, postContentTa.getText(), calendar, this.currUser.getFriendsList());
					} catch (IllegalArgumentException e1) {
						new ErrorAlert(e1.getMessage());
					}
					PostManagementPage.refreshTable(this.currUser);
				} else if (shareWithCb.getSelectionModel().getSelectedItem().equals("Specific Friends")) {
					LocalDate localDate = datePicker.getValue();
					if (localDate != null) {
						// Convert LocalDate to GregorianCalendar
						calendar = convertToGregorianCalendar(localDate);
					}
					selectFriends.setCurrUser(this.currUser);
					selectFriends.refreshListView();
					selectStage.show();
					selected = new LinkedList<User>();
					selectFriends.getDoneB().setOnAction(e1 -> {
						selected = selectFriends.getSelected();
						try {
							new Post(this.currUser, postContentTa.getText(), calendar, selected);
						} catch (IllegalArgumentException e2) {
							new ErrorAlert(e2.getMessage());
						}
						PostManagementPage.refreshTable(this.currUser);
						selectStage.close();
					});
					selectFriends.getCancelB().setOnAction(e1 -> {
						selectStage.close();
					});
				}
			} else {
				if (datePicker.getValue() == null)
					new ErrorAlert("Error: Select the Creation Date!");
				if (shareWithCb.getSelectionModel().getSelectedItem() == null)
					new ErrorAlert("Error: Select the users those you want to share the post with them!");
			}
		});
	}

	public GregorianCalendar convertToGregorianCalendar(LocalDate localDate) {
		// Convert LocalDate to Date
		Date date = java.sql.Date.valueOf(localDate);
		// Initialize a GregorianCalendar using the Date
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar;
	}

	public MyButton getCancelB() {
		return cancelB;
	}

	public MyButton getClearB() {
		return clearB;
	}

	public void setCurrUser(User currUser) {
		this.currUser = currUser;
	}

}

class SelectFriendsPage extends Scene {
	private static BorderPane selectPane = new BorderPane();
	private MyButton doneB;
	private MyButton cancelB;
	private ListView<User> freindsLv;
	private User currUser;

	public SelectFriendsPage(double h, double w, User currUser) {
		super(selectPane, h, w);
		this.currUser = currUser;

//		Create the main container of the content of the page
		VBox contentVb = new VBox();
		contentVb.getStyleClass().add("rounded-vbox");
		selectPane.setPadding(new Insets(0, 10, 10, 10));

//		The title of the page
		MyLabel addTitleL = new MyLabel("Share with");
		addTitleL.setPadding(new Insets(10, 0, 10, 30));

//		Create HBox contains the buttons of the add page
		HBox buttonsHb = new HBox();
		buttonsHb.setSpacing(30);
		buttonsHb.setAlignment(Pos.CENTER);
		buttonsHb.setPadding(new Insets(0, 0, 0, 0));

//		Done Button to create post shared with the selected friends
		setDoneB(new MyButton());
		getDoneB().setText("Done");
		getDoneB().setPrefHeight(30);
		getDoneB().setPrefWidth(70);

//		Create cancel Button to close the page
		cancelB = new MyButton();
		cancelB.setText("Cancel");
		cancelB.setPrefHeight(30);
		cancelB.setPrefWidth(70);

//		Add the buttons to the HBox
		buttonsHb.getChildren().addAll(getDoneB(), cancelB);

//		Create the list view to enable for the user to select the friends those he want to share the post with him
		if(currUser!= null)
			freindsLv = new ListView<User>(FXCollections.observableList(this.currUser.getFriendsList().toArrayList()));
		else
			freindsLv = new ListView<User>();
		freindsLv.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
		freindsLv.setMaxHeight(300);
		freindsLv.setMaxWidth(400);

//		Add all content to the main pane
		contentVb.getChildren().addAll(freindsLv, buttonsHb);
		selectPane.setTop(addTitleL);
		selectPane.setCenter(contentVb);
		selectPane.setId("myBorderPane"); // Assign a unique ID
		this.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

	}

	public LinkedList<User> getSelected() {
		LinkedList<User> selected = new LinkedList<User>();
		for (User user : freindsLv.getSelectionModel().getSelectedItems()) {
			selected.addFirst(user);
		}
		return selected;
	}

	public MyButton getDoneB() {
		return doneB;
	}

	public void setDoneB(MyButton doneB) {
		this.doneB = doneB;
	}

	public void setCurrUser(User currUser) {
		this.currUser = currUser;
	}

	public MyButton getCancelB() {
		return this.cancelB;
	}

	public void refreshListView() {
		freindsLv.setItems(FXCollections.observableList(this.currUser.getFriendsList().toArrayList()));
	}

}
