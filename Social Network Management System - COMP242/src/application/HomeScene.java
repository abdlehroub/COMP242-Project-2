package application;

import java.io.FileNotFoundException;

import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HomeScene extends Scene {
	private static BorderPane mainPane = new BorderPane();

	public HomeScene() {
		super(mainPane);

//		The logo of the application
		Image image = new Image("file:C:\\Users\\Abdelrahman Lehroub\\Desktop\\Proj.png");
		ImageView appIcon = new ImageView(image);
		appIcon.setFitHeight(200);
		appIcon.setFitWidth(200);

//		The tabs of the app
		TabPane tabs = new TabPane();
		tabs.setSide(Side.LEFT);

		Tab userManagementT = new Tab("Users Management");
		userManagementT.setClosable(false);

		Tab friendshipManagementT = new Tab("Friendships Management");
		friendshipManagementT.setClosable(false);

		Tab postManagementT = new Tab("Posts Management");
		postManagementT.setClosable(false);

		Tab statisticsT = new Tab("Statitstcs");
		statisticsT.setClosable(false);

//		Create rectangle as a background of the logo
		StackPane logoContainer = new StackPane();
		Rectangle rec = new Rectangle();
		rec.setHeight(87);
		rec.setWidth(400);
		rec.setTranslateY(-364);
		rec.setTranslateX(-663.5);
		rec.setFill(Color.WHITE);
		StackPane.setAlignment(appIcon, javafx.geometry.Pos.TOP_LEFT);
		appIcon.setTranslateX(60);
		appIcon.setTranslateY(-50);
		logoContainer.getChildren().addAll(tabs, rec, appIcon);

		UserManagement userManagP = new UserManagement();
		userManagementT.setContent(userManagP);

		FriendshipManagement friendshipP = new FriendshipManagement();
		friendshipManagementT.setContent(friendshipP);

		PostManagementPage postPage = new PostManagementPage();
		postManagementT.setContent(postPage);

		StatisticsPage statP = new StatisticsPage();
		statisticsT.setContent(statP);

		MenuBar menuBar = new MenuBar();

		Menu fileM = new Menu("File");
		Menu userM = new Menu("User");
		Menu exitM = new Menu("Exit");

		MenuItem readMi = new MenuItem("Read file");
		MenuItem saveMi = new MenuItem("Save to file");
		fileM.getItems().addAll(readMi, saveMi);

		MenuItem addUser = new MenuItem("Add User");
		MenuItem userManagement = new MenuItem("User Management");
		userM.getItems().addAll(addUser, userManagement);

		MenuItem exitSaveMi = new MenuItem("Save and exit");
		MenuItem exitDontMi = new MenuItem("Exit");
		exitM.getItems().addAll(exitDontMi, exitSaveMi);

		menuBar.getMenus().addAll(fileM, userM, exitM);

		statP.getPostsByUserB().setOnAction(e -> {
			tabs.getSelectionModel().select(postManagementT);
		});
		statP.getPostsSharedWithB().setOnAction(e -> {
			tabs.getSelectionModel().select(postManagementT);
		});

		mainPane.setId("myBorderPane"); // Assign a unique ID
		mainPane.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		tabs.getTabs().addAll(userManagementT, friendshipManagementT, postManagementT, statisticsT);
		tabs.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		menuBar.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		mainPane.setLeft(logoContainer);
		mainPane.setTop(menuBar);

		addUser.setOnAction(e -> {
			userManagP.getAddB().fire();
		});
		userManagement.setOnAction(e -> {
			tabs.getSelectionModel().select(userManagementT);
		});

		exitDontMi.setOnAction(e -> {
			System.exit(0);
		});
		exitSaveMi.setOnAction(e -> {
			try {
				userManagP.exportToFile(true);
				friendshipP.exportToFile();
				postPage.exportToFile();
			} catch (FileNotFoundException e1) {
				new ErrorAlert("Error: No File Selected");
			} catch (NullPointerException e2) {
				new ErrorAlert("Error: No File Selected");
			}
			System.exit(0);
		});

		readMi.setOnAction(e -> {
			try {
				userManagP.readFromFile();
				friendshipP.readFromFile();
				postPage.readFromFile();
			} catch (FileNotFoundException e1) {
				new ErrorAlert("Error: No File Selected");
			} catch (NullPointerException e2) {
				new ErrorAlert("Error: No File Selected");
			}
		});

		saveMi.setOnAction(e -> {
			try {
				userManagP.exportToFile(true);
				friendshipP.exportToFile();
				postPage.exportToFile();
			} catch (FileNotFoundException e1) {
				new ErrorAlert("Error: No File Selected");
			} catch (NullPointerException e2) {
				new ErrorAlert("Error: No File Selected");
			}
		});

	}

}
