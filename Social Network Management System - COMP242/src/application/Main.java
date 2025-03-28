package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main {
//	@Override
//	public void start(Stage primaryStage) {
//		try {
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root,400,400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
	public static void main(String[] args) {
//		launch(args);
		LinkedList<Integer> list = new LinkedList<Integer>();
		list.addFirst(40);
		System.out.println(list);
		list.addFirst(20);
		System.out.println(list);
		list.addFirst(30);
		System.out.println(list);
		list.insert(3,10);
		System.out.println(list);
		System.out.println(list.contains(20));
		System.out.println(list.contains(70));

//		list.removeFirst();
//		System.out.println(list);
//		list.clear();
//		System.out.println(list);

	}
}
