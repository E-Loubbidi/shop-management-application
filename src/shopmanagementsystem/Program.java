package shopmanagementsystem;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Program extends Application{

	private static Stage appRoot = null;
	
	public static List<BorderPane> recentHistoryPanes = null;
	
	private Dashbord db = new Dashbord();
	
	public static void showViews(BorderPane view) {
		AnchorPane root = new AnchorPane();
		root.getChildren().add(view);
		Scene scene = appRoot.getScene();
		scene.setRoot(root);
		appRoot.setScene(scene);
		appRoot.show();
		recentHistoryPanes.add(view);
	}
	
	public static void back() {
		
		if (recentHistoryPanes.size() >= 1) {
		recentHistoryPanes.remove(recentHistoryPanes.size() - 1);
		AnchorPane root = new AnchorPane();
		root.getChildren().add(recentHistoryPanes.get(recentHistoryPanes.size() - 1));		
		appRoot.hide();
		Scene scene = appRoot.getScene();
		scene.setRoot(root);
		appRoot.setScene(scene);
		appRoot.show();
		}
	}

	@Override
	public void start(Stage window) throws Exception {
		
		appRoot = window;
		
		appRoot.setTitle("Shop Management System");
		appRoot.setWidth(1000);
		appRoot.setHeight(700);
		
		appRoot.getIcons().add(new Image("iconShop.png"));
		
		AnchorPane root = new AnchorPane();
		Scene defaultScene = new Scene(root);
		appRoot.setScene(defaultScene);
		
		recentHistoryPanes = new ArrayList<>();
		
		defaultScene.getStylesheets().add("style.css");
		
		showViews(db.getPane());
		
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
