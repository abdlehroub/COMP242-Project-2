package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StatisticsPage extends BorderPane {
	private MyButton postsByUserB;
	private MyButton postsSharedWithB;
	private MyButton mostActiveB;
	private MyButton engagementMetricsB;

	public StatisticsPage() {

		MyLabel manageL = new MyLabel("Statistical Report");
		manageL.setPadding(new Insets(20, 0, 20, 20));
		this.setTop(manageL);

		VBox buttonsVb = new VBox();
		buttonsVb.getStyleClass().add("rounded-vbox");

		postsByUserB = new MyButton();
		postsByUserB.setText("Posts Created by User");
		postsByUserB.setPrefHeight(100);
		postsByUserB.setPrefWidth(400);

		postsSharedWithB = new MyButton();
		postsSharedWithB.setText("Posts Shared with User");
		postsSharedWithB.setPrefHeight(100);
		postsSharedWithB.setPrefWidth(400);

		mostActiveB = new MyButton();
		mostActiveB.setText("Most Active Users");
		mostActiveB.setPrefHeight(100);
		mostActiveB.setPrefWidth(400);

		engagementMetricsB = new MyButton();
		engagementMetricsB.setText("Engagement Metrics");
		engagementMetricsB.setPrefHeight(100);
		engagementMetricsB.setPrefWidth(400);

		buttonsVb.getChildren().addAll(postsByUserB, postsSharedWithB, mostActiveB, engagementMetricsB);
		buttonsVb.setSpacing(20);
		buttonsVb.setAlignment(Pos.CENTER);
		this.setCenter(buttonsVb);
		this.setPadding(new Insets(0, 10, 10, 10));

		MostActivePage mostActive = new MostActivePage();
		mostActiveB.setOnAction(e -> {
			this.setCenter(mostActive);

		});
		mostActive.getBackB().setOnAction(e -> {
			this.setCenter(buttonsVb);
		});

		EngagementMetricsPage engagementMetricsPage = new EngagementMetricsPage();
		engagementMetricsB.setOnAction(e -> {
			this.setCenter(engagementMetricsPage);
//		for (User user : Main.usersList) {
//			System.out.println(user);
//			System.out.println(user.getPostsCreatedList());
//		}

		});
		engagementMetricsPage.getBackB().setOnAction(e -> {
			this.setCenter(buttonsVb);
		});

	}

	public MyButton getPostsByUserB() {
		return postsByUserB;
	}

	public void setPostsByUserB(MyButton postsByUserB) {
		this.postsByUserB = postsByUserB;
	}

	public MyButton getPostsSharedWithB() {
		return postsSharedWithB;
	}

	public void setPostsSharedWithB(MyButton postsSharedWithB) {
		this.postsSharedWithB = postsSharedWithB;
	}

}

class MostActivePage extends VBox {

	ComboBox<String> mostActiveCb;
	private MyButton backB;

	MostActivePage() {
		this.getStyleClass().add("rounded-vbox");

		TextField numT = new TextField();
		numT.setPrefHeight(30);
		numT.setPrefWidth(400);
		numT.setPromptText("Top #?:");

		mostActiveCb = new ComboBox<>();
		mostActiveCb.getItems().addAll("All the time", "Last 3 weeks");
		mostActiveCb.setValue("All the time");

		MyButton filterB = new MyButton();
		filterB.setText("Filter");
		filterB.setPrefHeight(30);
		filterB.setPrefWidth(140);

		HBox upperHb = new HBox();
		upperHb.setAlignment(Pos.CENTER);
		upperHb.setSpacing(10);
		upperHb.getChildren().addAll(numT, mostActiveCb, filterB);
		TableView<User> usersTable = new TableView<User>();
		TableColumn<User, String> id = new TableColumn<User, String>("UserID");
		TableColumn<User, String> name = new TableColumn<User, String>("User Name");
		TableColumn<User, Integer> age = new TableColumn<User, Integer>("Age");
		TableColumn<User, Integer> numOfPosts = new TableColumn<User, Integer>("Number of Posts");
		usersTable.setStyle("  -fx-background-color: white;\r\n" + "    -fx-background-radius: 10;\r\n"
				+ "    -fx-border-radius: 10;\r\n" + "    -fx-border-color: #E5E7EB;\r\n" + "    -fx-border-width: 1;");
		usersTable.getColumns().addAll(id, name, age, numOfPosts);
		numOfPosts.setCellValueFactory(
				e -> new SimpleIntegerProperty(e.getValue().getPostsCreatedList().size()).asObject());
		id.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
		name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		age.setCellValueFactory(new PropertyValueFactory<User, Integer>("age"));
		usersTable.autosize();
		usersTable.setPrefHeight(550);

		usersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		backB = new MyButton();
		backB.setText("Back");
		backB.setPrefHeight(30);
		backB.setPrefWidth(150);

		this.getChildren().addAll(upperHb, usersTable, backB);

		filterB.setOnAction(e -> {
			boolean isAll = false;
			if (mostActiveCb.getValue().equals("All the time")) {
				isAll = true;
			}
			try {
				usersTable.setItems(getMostActiveUsers(Integer.parseInt(numT.getText()), isAll));
			} catch (NumberFormatException e1) {
				new ErrorAlert("Error: The text field must contain only numbers!");
			}

		});

	}

	public ObservableList<User> getMostActiveUsers(int num, boolean all) {
		ObservableList<User> max = FXCollections.observableArrayList(new ArrayList<>(num));
		LinkedList<User> temp = new LinkedList<>();
		for (User user : Main.usersList) {
			if (all) {
				temp.addFirst(user);
			} else {
				for (Post post : user.getPostsCreatedList()) {
					GregorianCalendar now = new GregorianCalendar();
					GregorianCalendar threeWeeksAgo = new GregorianCalendar(now.get(Calendar.YEAR),
							now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH) - 21);
					if (post.getCreationDate().after(threeWeeksAgo)) {
						temp.addFirst(user);
						continue;
					}
				}
			}
		}
		for (int i = 0; i < num; i++) {
			User maxUser = null;
			int maxCount = -1;
			for (User user : temp) {
				if (user.getPostsCreatedList().size() > maxCount) {
					maxCount = user.getPostsCreatedList().size();
					maxUser = user;
				}
			}
			if (maxUser != null) {
				max.add(maxUser);
				temp.remove(maxUser);
			}
		}
		return max;
	}

	public MyButton getBackB() {
		return this.backB;
	}
}

class EngagementMetricsPage extends VBox {
	private TableView<User> usersTable;
	private MyButton backB;

	EngagementMetricsPage() {
		this.getStyleClass().add("rounded-vbox");

		backB = new MyButton();
		backB.setText("Back");
		backB.setPrefHeight(30);
		backB.setPrefWidth(150);

		usersTable = new TableView<User>();
		TableColumn<User, String> id = new TableColumn<User, String>("UserID");
		TableColumn<User, String> name = new TableColumn<User, String>("User Name");
		TableColumn<User, Integer> postsCreated = new TableColumn<User, Integer>("# of Posts Created");
		TableColumn<User, Integer> postsShared = new TableColumn<User, Integer>("# of Posts Shared with him");
		postsCreated.setCellValueFactory(
				e -> new SimpleIntegerProperty(e.getValue().getPostsCreatedList().getSize()).asObject());
		postsShared.setCellValueFactory(
				e -> new SimpleIntegerProperty(e.getValue().getSharedWithThemPostsList().getSize()).asObject());
		id.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
		name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		usersTable.setStyle("  -fx-background-color: white;\r\n" + "    -fx-background-radius: 10;\r\n"
				+ "    -fx-border-radius: 10;\r\n" + "    -fx-border-color: #E5E7EB;\r\n" + "    -fx-border-width: 1;");
		usersTable.getColumns().addAll(id, name, postsCreated, postsShared);
		usersTable.autosize();
		usersTable.setPrefHeight(650);
		usersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		usersTable.setItems(Main.usersObList);
		this.getChildren().addAll(usersTable, backB);
		this.setSpacing(20);
		this.setAlignment(Pos.TOP_LEFT);
	}

	public MyButton getBackB() {
		return this.backB;
	}

	public void refreshTable() {
		usersTable.setItems(Main.usersObList);

	}

}
