package application;

import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class HomeScene extends Scene {
	private static BorderPane mainPane = new BorderPane();

	public HomeScene() {
		super(mainPane);

		TabPane tabs = new TabPane();
		tabs.setSide(Side.LEFT);

		Tab dashboardT = new Tab("Dashboard");
		dashboardT.setClosable(false);

		Tab userManagementT = new Tab("User Management");
		userManagementT.setClosable(false);

		Tab friendshipManagementT = new Tab("Friendship Management");
		friendshipManagementT.setClosable(false);

		UserManagement userManagP = new UserManagement();
		userManagementT.setContent(userManagP);
		
		FriendshipManagement friendshipP = new FriendshipManagement();
		friendshipManagementT.setContent(friendshipP);

		tabs.getTabs().addAll(dashboardT, userManagementT,friendshipManagementT);
		tabs.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		mainPane.setLeft(tabs);

	}

}
