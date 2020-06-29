package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;


public class DepartmentListController implements Initializable{

	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	private Button btNew;
	
	public void onBtNewAction() {
		System.out.println("btn");
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

}
