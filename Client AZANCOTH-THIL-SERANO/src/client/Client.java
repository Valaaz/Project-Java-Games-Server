package client;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Client extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			URL fxmlURL = getClass().getResource("/mvc/vue/FicheMenuPrincipal.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();

			Scene scene = new Scene((VBox) root, 800, 500);
			scene.getStylesheets().add(getClass().getResource("/mvc/vue/css/button.css").toExternalForm());
			primaryStage.getIcons().add(new Image("/mvc/vue/images/icon.svg.png"));
			primaryStage.setScene(scene);
			primaryStage.setTitle("Claiwatin");
			primaryStage.centerOnScreen();
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
