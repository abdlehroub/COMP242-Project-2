package application;

import java.time.ZoneId;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PostManagementPage extends BorderPane {
	private static TableView<Post> postsTable;
	private static ObservableList<Post> postsList;
	private User currUser;
	private TextField searchTf;
	private MyButton searchB;
	private MyButton createPostB;
	private MyButton deleteB;
	private MyButton viewB;
	private MyButton showSharedPostsB;

	@SuppressWarnings({ "unchecked", "deprecation" })
	public PostManagementPage() {
//		Get the first user to be the user in the UI and put his posts in the table 
		currUser = Main.usersList.getFirst();
		postsList = FXCollections.observableList(currUser.getPostsCreatedList().toArrayList());

//		Create Next & Previous buttons to change the user
		MyButton prevB = new MyButton();
		prevB.setText("<");
		prevB.setPrefHeight(100);
		prevB.setPrefWidth(40);

		MyButton nextB = new MyButton();
		nextB.setText(">");
		nextB.setPrefHeight(100);
		nextB.setPrefWidth(40);

//		Create HBox contains the Navigation Buttons and the TableView
		HBox tableHb = new HBox();
		tableHb.setSpacing(10);
		tableHb.setAlignment(Pos.CENTER);

//		Create TableView to display the list of Posts of the current user 
		postsTable = new TableView<Post>();
		TableColumn<Post, String> id = new TableColumn<Post, String>("PostID");
		TableColumn<Post, String> creationDate = new TableColumn<Post, String>("Creation Date");
		TableColumn<Post, String> age = new TableColumn<Post, String>("Content");
		TableColumn<Post, String> sharedWith = new TableColumn<Post, String>("Shared With");
		postsTable.setStyle("  -fx-background-color: white;\r\n" + "    -fx-background-radius: 10;\r\n"
				+ "    -fx-border-radius: 10;\r\n" + "    -fx-border-color: #E5E7EB;\r\n" + "    -fx-border-width: 1;");
		id.setCellValueFactory(new PropertyValueFactory<Post, String>("id"));
		creationDate.setCellValueFactory(new PropertyValueFactory<Post, String>("creationDateFormat"));
		age.setCellValueFactory(new PropertyValueFactory<Post, String>("content"));
		sharedWith.setCellValueFactory(new PropertyValueFactory<Post, String>("sharedWithListPrinted"));
		postsTable.autosize();
		postsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		postsTable.setItems(postsList);
		postsTable.setPrefHeight(550);
		postsTable.setPrefWidth(1050);
		postsTable.getColumns().addAll(id, creationDate, age, sharedWith);
		tableHb.getChildren().addAll(prevB, postsTable, nextB);

//		The outer container of the content of this page
		VBox manageVb = new VBox();
		manageVb.getStyleClass().add("rounded-vbox");

//		Label as title of the page
		MyLabel titleL = new MyLabel("Posts Management");
		titleL.setPadding(new Insets(20, 0, 20, 20));
		this.setTop(titleL);

//	    HBoxes for the upper buttons and text field and lower buttons
		HBox lowerHb = new HBox();
		HBox upperHb = new HBox();

//		Show the current user name and id
		MyLabel userL = new MyLabel("User: " + currUser);
		userL.setStyle("-fx-font-size: 15px; -fx-font-style: italic; -fx-text-fill: #635bff; -fx-font-weight: bold;");

//		Search TextField and button
		searchTf = new TextField();
		searchTf.setPrefHeight(30);
		searchTf.setPrefWidth(600);
		searchTf.setPromptText("Enter the User ID:");

		searchB = new MyButton();
		searchB.setText("Search");
		searchB.setPrefHeight(26);
		searchB.setPrefWidth(60);

//		Add the upper content to HBox
		upperHb.getChildren().addAll(searchTf, searchB, userL);
		upperHb.setAlignment(Pos.CENTER);
		upperHb.setSpacing(20);

//		Button to Create Post for the Selected User manually
		createPostB = new MyButton();
		createPostB.setText("Create Post");
		createPostB.setPrefHeight(30);
		createPostB.setPrefWidth(120);

//		Button to View Post selected from the table 
		viewB = new MyButton();
		viewB.setText("View Post");
		viewB.setPrefHeight(30);
		viewB.setPrefWidth(140);

//		Button to Delete Post from the posts list
		deleteB = new MyButton();
		deleteB.setText("Delete Post");
		deleteB.setPrefHeight(30);
		deleteB.setPrefWidth(140);

//		Button to Display the posts from other users shared with the current user
		showSharedPostsB = new MyButton();
		showSharedPostsB.setText("Friends Posts");
		showSharedPostsB.setPrefHeight(30);
		showSharedPostsB.setPrefWidth(140);

//		Add the items of the lower HBox to it
		lowerHb.getChildren().addAll(createPostB, viewB, showSharedPostsB, deleteB);
		HBox.setMargin(searchB, new Insets(0, 180, 0, 0));
		lowerHb.setSpacing(10);
		lowerHb.setAlignment(Pos.CENTER);

//		Add all content in the main container
		manageVb.getChildren().addAll(upperHb, tableHb, lowerHb);
		this.setCenter(manageVb);
		this.setPadding(new Insets(0, 10, 10, 10));

//		Action to go to the next user in the main users list
		nextB.setOnAction(e -> {
			this.currUser = Main.usersList.getNext(currUser);
			refreshTable(currUser);
			userL.setText("User: " + currUser);

		});

//		Action to go to the previous user in the main users list
		prevB.setOnAction(e -> {
			this.currUser = Main.usersList.getPrev(currUser);
			refreshTable(currUser);
			userL.setText("User: " + currUser);
		});

//		Action to delete to the selected post from the table
		deleteB.setOnAction(e -> {
			Post selectedPost = postsTable.getSelectionModel().getSelectedItem();
			if (selectedPost != null) {
				currUser.removePost(selectedPost);
				postsList.remove(selectedPost);
			} else {
				new ErrorAlert("Error: There is no selected post!!");

			}
		});

//		Show the Create Post window to create post to the current user
		Stage createStage = new Stage();
		createStage.setTitle("Create Post");
		CreatePostPage createPostPage = new CreatePostPage(600, 450, this.currUser);
		createStage.setResizable(false);
		createStage.setScene(createPostPage);
		createStage.setOnCloseRequest(e -> {
			createPostPage.getClearB().fire();
		});
		createPostB.setOnAction(e -> {
			createPostPage.setCurrUser(currUser);
			createStage.show();
		});
		createPostPage.getCancelB().setOnAction(e -> {
			createStage.close();
		});

//		Show the View Posts window to View post Selected from the table view
		Stage viewStage = new Stage();
		viewStage.setTitle("View Posts");
		viewStage.setResizable(false);
		Scene viewScene = new Scene(new Pane(), 600, 450);
		viewB.setOnAction(e -> {
			Post selected = postsTable.getSelectionModel().getSelectedItem();
			if (selected != null) {
				ViewPost viewPostPage = new ViewPost(selected);
				viewScene.setRoot(viewPostPage);
				viewStage.setScene(viewScene);
				viewStage.show();
			} else {
				new ErrorAlert("Error: There is No Posts selected from the table!");
			}
		});

//		Show the Friends Posts window to display the posts shared with the current user
		Stage friendsPostsStage = new Stage();
		friendsPostsStage.setTitle("Friends Posts1");
		friendsPostsStage.setResizable(false);
		Scene friendsPostsScene = new Scene(new Pane(), 600, 450);
		showSharedPostsB.setOnAction(e -> {

			if (!currUser.getSharedWithThemPostsList().isEmpty()) {
				FriendsPosts friendsPostsPage = new FriendsPosts(currUser);
				friendsPostsScene.setRoot(friendsPostsPage);
				friendsPostsStage.setScene(friendsPostsScene);
				friendsPostsStage.show();
			} else {
				new ErrorAlert("Error: There is No Posts shared with this user!");
			}
		});

	}

//	Method to refresh the data of the posts table
	public static void refreshTable(User currUser) {
		postsList.setAll(currUser.getPostsCreatedList().toArrayList());
		postsTable.setItems(postsList);
	}

}

class ViewPost extends BorderPane {
	private DatePicker datePicker;
	private Post currPost;

	public ViewPost(Post currPost) {
		this.currPost = currPost;

		VBox contentVb = new VBox();
		contentVb.getStyleClass().add("rounded-vbox");
		this.setPadding(new Insets(0, 10, 10, 10));

//		create labels for the text fields
		HBox upperHb = new HBox();
		upperHb.setSpacing(10);
		upperHb.setAlignment(Pos.CENTER);

		Label shareWithL = new Label("Share With");
		Label creationDateL = new Label("Creation Date");

		ComboBox<String> shareWithCb = new ComboBox<String>();
		shareWithCb.getItems().addAll("All Friends", "Specific Friends");
		if (this.currPost.getSharedWithList() == currPost.getCreator().getFriendsList()) {
			shareWithCb.setValue("All Friends");
		} else {
			shareWithCb.setValue("Specific Friends");
		}
		shareWithCb.setEditable(false);
		shareWithCb.setDisable(true);

//		Initialize the text fields
		datePicker = new DatePicker();
		datePicker.setValue(
				(this.currPost.getCreationDate().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
		datePicker.setEditable(false);
		datePicker.setDisable(true);
		upperHb.getChildren().addAll(shareWithL, shareWithCb, creationDateL, datePicker);

		TextArea postContentTa = new TextArea();
		postContentTa.setText(currPost.getContent());
		postContentTa.setEditable(false);
		postContentTa.setMinHeight(220);
		postContentTa.setPrefWidth(200);

//		Create the title of the page label
		MyLabel addTitleL = new MyLabel("View Post");
		addTitleL.setPadding(new Insets(10, 0, 10, 30));

		contentVb.getChildren().addAll(upperHb, postContentTa);

		this.setTop(addTitleL);
		this.setCenter(contentVb);
		this.setId("myBorderPane"); // Assign a unique ID

		this.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

	}

	public void setCurrPost(Post currPost) {
		this.currPost = currPost;
	}

}

class FriendsPosts extends BorderPane {
	private DatePicker datePicker;
	private Post currPost;
	private TextArea postContentTa;
	private MyLabel userL;

	public FriendsPosts(User currUser) {
//		Get the first post in shared with the current user to display it
		this.currPost = currUser.getSharedWithThemPostsList().getFirst();

//		The main container of the content of this page
		VBox contentVb = new VBox();
		contentVb.getStyleClass().add("rounded-vbox");
		this.setPadding(new Insets(0, 10, 10, 10));
		contentVb.setAlignment(Pos.CENTER);

//		HBox contains the content
		HBox upperHb = new HBox();
		upperHb.setSpacing(10);
		upperHb.setAlignment(Pos.CENTER);

//		Label to show who's the post creator 
		userL = new MyLabel("User: " + currPost.getCreator());
		userL.setStyle("-fx-font-size: 10px; -fx-font-style: italic; -fx-text-fill: #635bff; -fx-font-weight: bold;");

//		The creation date of the post
		Label creationDateL = new Label("Creation Date");
		datePicker = new DatePicker();
		datePicker.setValue(
				(this.currPost.getCreationDate().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
		datePicker.setEditable(false);
		datePicker.setDisable(true);
		upperHb.getChildren().addAll(creationDateL, datePicker, userL);

//		HBox for the text area and the navigation buttons
		HBox middleHb = new HBox();
		middleHb.setSpacing(20);
		middleHb.setAlignment(Pos.CENTER);

//		Display the content of thepost in the text area
		postContentTa = new TextArea();
		postContentTa.setText(currPost.getContent());
		postContentTa.setEditable(false);
		postContentTa.setMinHeight(220);
		postContentTa.setPrefWidth(400);

//		Create Next & Previous buttons to change the user
		MyButton prevB = new MyButton();
		prevB.setText("<");
		prevB.setPrefHeight(70);
		prevB.setPrefWidth(25);

		MyButton nextB = new MyButton();
		nextB.setText(">");
		nextB.setPrefHeight(70);
		nextB.setPrefWidth(25);

		middleHb.getChildren().addAll(prevB, postContentTa, nextB);

//		Create the title of the page label
		MyLabel addTitleL = new MyLabel("Freinds Posts");
		addTitleL.setPadding(new Insets(10, 0, 10, 30));

//		Delete button to delete post from the user friends posts list
		MyButton deleteB = new MyButton();
		deleteB.setText("Delete");
		deleteB.setPrefHeight(30);
		deleteB.setPrefWidth(300);

//		Add all content in the main page container
		contentVb.getChildren().addAll(upperHb, middleHb, deleteB);
		this.setTop(addTitleL);
		this.setCenter(contentVb);
		this.setId("myBorderPane"); // Assign a unique ID

		this.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

//		Action to go to the next post in the friends post 
		nextB.setOnAction(e -> {
			this.currPost = currUser.getSharedWithThemPostsList().getNext(currPost);
			refreshPost();

		});

//		Action to go to the previous post in the friends post 
		prevB.setOnAction(e -> {
			this.currPost = currUser.getSharedWithThemPostsList().getPrev(currPost);
			refreshPost();

		});

//		Action to go to delete post from the friends postsF list
		deleteB.setOnAction(e -> {
			ConfirmAlert confirmDelete = new ConfirmAlert("Are you sure you want to delete this item?");
			Optional<ButtonType> result = confirmDelete.getAlert().showAndWait();
			if (result.isPresent() && result.get() == confirmDelete.getSureButton()) {
				currUser.removeOthersPost(currPost);
				if (!currUser.getSharedWithThemPostsList().isEmpty()) {
					this.currPost = currUser.getSharedWithThemPostsList().getNext(currPost);
					refreshPost();
				} else { // If the post deleted is the last post in the list
					Label emptyL = new MyLabel("There is no posts shared with\n\t\t  this user");
					emptyL.setStyle(
							"-fx-font-size: 20px; -fx-font-style: italic; -fx-text-fill: #635bff; -fx-font-weight: bold;");
					contentVb.getChildren().clear();
					contentVb.getChildren().add(emptyL);
				}
			}
		});
	}

	public void setCurrPost(Post currPost) {
		this.currPost = currPost;
	}

//	Method to refresh the data of the page depends on the current post
	public void refreshPost() {
		userL.setText("User: " + this.currPost.getCreator());
		postContentTa.setText(currPost.getContent());
		datePicker.setValue(
				(this.currPost.getCreationDate().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));

	}

}
