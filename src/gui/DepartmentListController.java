package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;


public class DepartmentListController implements Initializable{

	private DepartmentService service; //vincula controller com service de model
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	private Button btNew;
	
	private ObservableList<Department> obsList;
	
	public void onBtNewAction() {
		System.out.println("btn");
	}
	
	//injeta a dependencia sem a necessidade de instanciar o objeto direto na classe e eliminiand o forte acoplamento
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		initializableNodes();
		
	}

	private void initializableNodes() { // metodo responsavel de vincular a propriedade da classe com o tableview
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id")); // padrao javaFX para 
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name") );
		
		Stage stage = (Stage)Main.getMainScene().getWindow(); //pega tamnho da tela pricipal fazendo um downcasting para stage
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty()); //atribui o tamnho anterior para o tableview 
		
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Service esta null");
		}
		List<Department> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
	}

}
