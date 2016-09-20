package com.starterkit.javafx;

import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application entry point.
 */
public class Startup extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	/**
	 * @see {@link javafx.application.Application#start(Stage)}
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("StarterKit-Chess");

		/*
		 * Load screen from FXML file with specific language bundle (derived
		 * from default locale).
		 */
		Parent searchRoot = FXMLLoader.load(getClass().getResource("/com/starterkit/javafx/view/profile-search.fxml"), //
				ResourceBundle.getBundle("com/starterkit/javafx/bundle/search"));

		Scene searchScene = new Scene(searchRoot);

		primaryStage.setScene(searchScene);

		primaryStage.show();
	}
}
