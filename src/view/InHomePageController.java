package view;

import java.util.ArrayList;

import controller.Couple;
import controller.Disease;
import controller.MainApp;
import databases.HPOConnexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class InHomePageController {
	
		@FXML
	  	private TextField CS;
		private String clinicalSign;
	
	    // Reference to the main application.
	    private MainApp mainApp;

	    /**
	     * The constructor.
	     * The constructor is called before the initialize() method.
	     */
	    public InHomePageController() {	    	
	    }

	    /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {
	        }

	    /**
	     * Is called by the main application to give a reference back to itself.
	     * 
	     * @param mainApp
	     */
	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;
	    }
	    
	    public void handleButtonSearch() {
	    	String clinicalSign = CS.getText();
	    	System.out.println(clinicalSign);
	    	mainApp.showDisplayPage();	
	    }
	    		    
	    public void handleButtonDrugSideEffect() {
	    	String clinicalSign = CS.getText();
	    	System.out.println(clinicalSign);
	    	mainApp.showDisplaySideEffectPage();
	    	
	    }
	    
	    public void handleButtonMedecine() {
	    	clinicalSign = CS.getText();
	    	System.out.println(clinicalSign);
	    	mainApp.showDisplayMedecinePage();
	    	
	    }
	    
	    public String getCS() {
	    	return clinicalSign;
	    }
	    
}
