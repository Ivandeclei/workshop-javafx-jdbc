package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable {

	//propriedades do menu
	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;
	
	//trata eventos do menu
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
	}
	
	@FXML
	public void onMenuItemDeparmentAction() {
		System.out.println("onMenuDepartment");
	}
	
	@FXML
	public void onMenuItemAbout() {
		System.out.println("about");
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
		
	}
	

}
