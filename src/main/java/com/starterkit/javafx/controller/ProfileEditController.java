package com.starterkit.javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.starterkit.javafx.dataprovider.DataProvider;
import com.starterkit.javafx.dataprovider.data.ProfileVO;
import com.starterkit.javafx.model.ProfileEdit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the person search screen.
 * <p>
 * The JavaFX runtime will inject corresponding objects in the @FXML annotated
 * fields. The @FXML annotated methods will be called by JavaFX runtime at
 * specific points in time.
 * </p>
 *
 */
public class ProfileEditController {

	private static final Logger LOG = Logger.getLogger(ProfileEditController.class);

	/**
	 * Resource bundle loaded with this controller. JavaFX injects a resource
	 * bundle specified in {@link FXMLLoader#load(URL, ResourceBundle)} call.
	 * <p>
	 * NOTE: The variable name must be {@code resources}.
	 * </p>
	 */
	@FXML
	private ResourceBundle resources;

	/**
	 * URL of the loaded FXML file. JavaFX injects an URL specified in
	 * {@link FXMLLoader#load(URL, ResourceBundle)} call.
	 * <p>
	 * NOTE: The variable name must be {@code location}.
	 * </p>
	 */
	@FXML
	private URL location;

	/**
	 * JavaFX injects an object defined in FXML with the same "fx:id" as the
	 * variable name.
	 */

	@FXML
	private TextField loginField;

	@FXML
	private TextField nameField;

	@FXML
	private TextField surnameField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField passwordField;

	@FXML
	private TextField aboutMeField;

	@FXML
	private TextField lifeMottoField;

	@FXML
	private Button saveButton;

	@FXML
	private Button cancelButton;

	private final ProfileEdit model = new ProfileEdit();

	private final DataProvider dataProvider = DataProvider.INSTANCE;

	/**
	 * The JavaFX runtime instantiates this controller.
	 * <p>
	 * The @FXML annotated fields are not yet initialized at this point.
	 * </p>
	 */
	public ProfileEditController() {
		LOG.debug("Constructor: nameField = " + nameField);
	}

	/**
	 * The JavaFX runtime calls this method after loading the FXML file.
	 * <p>
	 * The @FXML annotated fields are initialized at this point.
	 * </p>
	 * <p>
	 * NOTE: The method name must be {@code initialize}.
	 * </p>
	 */
	@FXML
	private void initialize() {
		LOG.debug("initialize(): nameField = " + nameField);

		/*
		 * Bind controls properties to model properties.
		 */
		loginField.textProperty().bind(model.loginProperty());
		nameField.textProperty().bindBidirectional(model.nameProperty());
		surnameField.textProperty().bindBidirectional(model.surnameProperty());
		emailField.textProperty().bindBidirectional(model.emailProperty());
		passwordField.textProperty().bindBidirectional(model.passwordProperty());
		aboutMeField.textProperty().bindBidirectional(model.aboutMeProperty());
		lifeMottoField.textProperty().bindBidirectional(model.lifeMottoProperty());

	}

	/**
	 * The JavaFX runtime calls this method when the <b>Edit</b> button is
	 * clicked.
	 *
	 * @param event
	 *            {@link ActionEvent} holding information about this event
	 */
	@FXML
	private void saveButtonAction(ActionEvent event) throws IOException {
		LOG.debug("'Save' button clicked");
		// TODO here some action
		ProfileVO profile = new ProfileVO();
		dataProvider.updateProfile(profile);

		Stage stage = (Stage) cancelButton.getScene().getWindow();

		Parent searchRoot = FXMLLoader.load(getClass().getResource("/com/starterkit/javafx/view/profile-search.fxml"), //
				ResourceBundle.getBundle("com/starterkit/javafx/bundle/search"));

		Scene searchScene = new Scene(searchRoot);
		stage.setScene(searchScene);
		stage.show();
	}

	/**
	 * The JavaFX runtime calls this method when the <b>Delete</b> button is
	 * clicked.
	 *
	 * @param event
	 *            {@link ActionEvent} holding information about this event
	 */
	@FXML
	private void cancelButtonAction(ActionEvent event) throws IOException {
		LOG.debug("'Cancel' button clicked");

		Stage stage = (Stage) cancelButton.getScene().getWindow();

		Parent searchRoot = FXMLLoader.load(getClass().getResource("/com/starterkit/javafx/view/profile-search.fxml"), //
				ResourceBundle.getBundle("com/starterkit/javafx/bundle/search"));

		Scene searchScene = new Scene(searchRoot);
		stage.setScene(searchScene);
		stage.show();

	}

}
