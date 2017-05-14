package view;



import org.json.simple.parser.ParseException;

import controller.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class InHomePageController {
	
		@FXML
	  	private TextField CS;
		private static String clinicalSign;
					
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
	    
	    public void handleButtonSearch() throws ParseException {
	    	clinicalSign = CS.getText();
	    	//System.out.println(clinicalSign);
	    	
	    	mainApp.showDisplayPage();
	    	
	    }
	    		    
	    public void handleButtonDrugSideEffect() {
	    	clinicalSign = CS.getText();
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
