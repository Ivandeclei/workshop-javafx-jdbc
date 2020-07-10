package gui;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.IllegalSelectorException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbException;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.SellerService;

public class SellerListController implements Initializable, DataChangeListener {

	private SellerService service; // vincula controller com service de model

	@FXML
	private TableView<Seller> tableViewSeller;

	@FXML
	private TableColumn<Seller, Integer> tableColumnId;

	@FXML
	private TableColumn<Seller, String> tableColumnName;

	@FXML
	private TableColumn<Seller, Seller> tableColumnEDIT;

	@FXML
	private TableColumn<Seller, Seller> tableColumnREMOVE;

	@FXML
	private Button btNew;

	private ObservableList<Seller> obsList;

	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Seller obj = new Seller();
		createDialogForm(obj, "/gui/SellerForm.fxml", parentStage);
	}

	// injeta a dependencia sem a necessidade de instanciar o objeto direto na
	// classe e eliminiand o forte acoplamento
	public void setSellerService(SellerService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		initializableNodes();

	}

	private void initializableNodes() { // metodo responsavel de vincular a propriedade da classe com o tableview
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id")); // padrao javaFX para
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

		Stage stage = (Stage) Main.getMainScene().getWindow(); // pega tamnho da tela pricipal fazendo um downcasting
																// para stage
		tableViewSeller.prefHeightProperty().bind(stage.heightProperty()); // atribui o tamnho anterior para o
																				// tableview

	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service esta null");
		}
		List<Seller> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewSeller.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Seller obj, String absoluteName, Stage parentStage) {
		/*
		 * try { FXMLLoader loader = new
		 * FXMLLoader(getClass().getResource(absoluteName)); Pane pane = loader.load();
		 * 
		 * SellerFormController controller = loader.getController();
		 * controller.setSeller(obj); controller.setSellerService(new SellerService());
		 * controller.subscribeDataChangeListener(this); // adiciona objeto para receber
		 * a notifica��o do observer controller.updateFormData();
		 * 
		 * Stage dialogStage = new Stage(); dialogStage.setTitle("Enter Seller Data");
		 * dialogStage.setScene(new Scene(pane)); dialogStage.setResizable(false);
		 * dialogStage.initOwner(parentStage);
		 * dialogStage.initModality(Modality.WINDOW_MODAL); dialogStage.showAndWait();
		 * 
		 * } catch (IOException e) { Alerts.showAlert("IO Exception", "Error load View",
		 * e.getMessage(), AlertType.ERROR); }
		 */
	}

	@Override
	public void onDataChanged() { // metodo da interface onde executa um evento que esta sendo executado
		updateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/SellerForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Seller obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
		
		if(result.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			} catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
			
		}
	}

}
