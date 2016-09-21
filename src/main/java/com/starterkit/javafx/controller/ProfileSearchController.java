package com.starterkit.javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.starterkit.javafx.dataprovider.DataProvider;
import com.starterkit.javafx.dataprovider.data.ProfileVO;
import com.starterkit.javafx.model.ProfileEdit;
import com.starterkit.javafx.model.ProfileSearch;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Controller for the person search screen.
 * <p>
 * The JavaFX runtime will inject corresponding objects in the @FXML annotated
 * fields. The @FXML annotated methods will be called by JavaFX runtime at
 * specific points in time.
 * </p>
 *
 */
public class ProfileSearchController {

	private static final Logger LOG = Logger.getLogger(ProfileSearchController.class);

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
	private Button searchButton;

	@FXML
	private Button editButton;

	@FXML
	private Button deleteButton;

	@FXML
	private TableView<ProfileVO> resultTable;

	@FXML
	private TableColumn<ProfileVO, String> loginColumn;

	@FXML
	private TableColumn<ProfileVO, String> nameColumn;

	@FXML
	private TableColumn<ProfileVO, String> surnameColumn;

	@FXML
	private TableColumn<ProfileVO, String> emailColumn;

	private final DataProvider dataProvider = DataProvider.INSTANCE;

	private final ProfileSearch model = new ProfileSearch();

	/**
	 * The JavaFX runtime instantiates this controller.
	 * <p>
	 * The @FXML annotated fields are not yet initialized at this point.
	 * </p>
	 */
	public ProfileSearchController() {
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

		initializeResultTable();
		deleteButton.setDisable(true);
		editButton.setDisable(true);

		/*
		 * Bind controls properties to model properties.
		 */
		nameField.textProperty().bindBidirectional(model.nameProperty());
		surnameField.textProperty().bindBidirectional(model.surnameProperty());
		loginField.textProperty().bindBidirectional(model.loginProperty());
		resultTable.itemsProperty().bind(model.resultProperty());

	}

	private void initializeResultTable() {
		/*
		 * Define what properties of PersonVO will be displayed in different
		 * columns.
		 */
		loginColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLogin()));
		nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
		surnameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getSurname()));
		emailColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getEmail()));

		/*
		 * Show specific text for an empty table. This can also be done in FXML.
		 */
		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));

		/*
		 * When table's row gets selected say given person's name.
		 */
		resultTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProfileVO>() {

			@Override
			public void changed(ObservableValue<? extends ProfileVO> observable, ProfileVO oldValue,
					ProfileVO newValue) {
				LOG.debug(newValue + " selected");
				model.setSelectedProfile(newValue);
				if (newValue != null) {
					deleteButton.setDisable(false);
					editButton.setDisable(false);
				}
			}
		});
	}

	/**
	 * The JavaFX runtime calls this method when the <b>Search</b> button is
	 * clicked.
	 *
	 * @param event
	 *            {@link ActionEvent} holding information about this event
	 */
	@FXML
	private void searchButtonAction(ActionEvent event) {
		LOG.debug("'Search' button clicked");

		searchButtonAction();
	}

	/**
	 * This implementation is correct.
	 * <p>
	 * The {@link DataProvider#findPersons(String, SexVO)} call is executed in a
	 * background thread.
	 * </p>
	 */
	public void searchButtonAction() {
		/*
		 * Use task to execute the potentially long running call in background
		 * (separate thread), so that the JavaFX Application Thread is not
		 * blocked.
		 */
		Task<Collection<ProfileVO>> backgroundTask = new Task<Collection<ProfileVO>>() {

			/**
			 * This method will be executed in a background thread.
			 */
			@Override
			protected Collection<ProfileVO> call() throws Exception {
				LOG.debug("call() called");

				/*
				 * Get the data.
				 */
				Collection<ProfileVO> result = dataProvider.findProfiles( //
						model.getLogin(), //
						model.getName(), //
						model.getSurname());

				/*
				 * Value returned from this method is stored as a result of task
				 * execution.
				 */
				return result;
			}

			/**
			 * This method will be executed in the JavaFX Application Thread
			 * when the task finishes.
			 */
			@Override
			protected void succeeded() {
				LOG.debug("succeeded() called");

				/*
				 * Get result of the task execution.
				 */
				Collection<ProfileVO> result = getValue();

				/*
				 * Copy the result to model.
				 */
				model.setResult(new ArrayList<ProfileVO>(result));

				/*
				 * Reset sorting in the result table.
				 */
				resultTable.getSortOrder().clear();

				deleteButton.setDisable(true);
				editButton.setDisable(true);
			}
		};

		/*
		 * Start the background task. In real life projects some framework
		 * manages background tasks. You should never create a thread on your
		 * own.
		 */
		new Thread(backgroundTask).start();
	}

	/**
	 * The JavaFX runtime calls this method when the <b>Edit</b> button is
	 * clicked.
	 *
	 * @param event
	 *            {@link ActionEvent} holding
	 * @throws IOException
	 *             information about this event
	 */
	@FXML
	private void editButtonAction(ActionEvent event) throws IOException {
		LOG.debug("'Edit' button clicked");

		FXMLLoader loader = new FXMLLoader();

		loader.setResources(ResourceBundle.getBundle("com/starterkit/javafx/bundle/edit"));
		loader.setLocation(getClass().getResource("/com/starterkit/javafx/view/profile-edit.fxml"));

		Stage stage = new Stage();

		stage.setTitle("StarterKit-Chess");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(((Node) event.getSource()).getScene().getWindow());

		Parent editRoot = loader.load();
		ProfileEditController controller = loader.getController();
		ProfileEdit editModel = controller.getModel();

		editModel.setId(model.getSelectedProfile().getId());
		editModel.setLogin(model.getSelectedProfile().getLogin());
		editModel.setName(model.getSelectedProfile().getName());
		editModel.setSurname(model.getSelectedProfile().getSurname());
		editModel.setEmail(model.getSelectedProfile().getEmail());
		editModel.setPassword(model.getSelectedProfile().getPassword());
		editModel.setAboutMe(model.getSelectedProfile().getAboutMe());
		editModel.setLifeMotto(model.getSelectedProfile().getLifeMotto());

		controller.setSearchController(this);

		Scene editScene = new Scene(editRoot);
		stage.setScene(editScene);
		stage.show();
	}

	/**
	 * The JavaFX runtime calls this method when the <b>Delete</b> button is
	 * clicked.
	 *
	 * @param event
	 *            {@link ActionEvent} holding information about this event
	 * @throws IOException
	 */
	@FXML
	private void deleteButtonAction(ActionEvent event) {
		LOG.debug("'Delete' button clicked");

		deleteButtonAction();

	}

	private void deleteButtonAction() {

		Task<Void> backgroundTask = new Task<Void>() {

			/**
			 * This method will be executed in a background thread.
			 */
			@Override
			protected Void call() throws Exception {
				LOG.debug("call() called");

				dataProvider.deleteProfile(model.getSelectedProfile().getId());
				return null;
			}

			@Override
			protected void succeeded() {
				LOG.debug("succeeded() called");

				deleteButton.setDisable(true);
				editButton.setDisable(true);
				searchButtonAction();
			}
		};

		new Thread(backgroundTask).start();
	}

}
